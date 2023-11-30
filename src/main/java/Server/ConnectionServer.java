package Server;
import Utils.Connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.security.KeyStore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
public class ConnectionServer {
    private static ServerSocketFactory factory = ServerSocketFactory.getDefault();
    private ServerSocket server;
    public static void init_ssl(String key_file, String key_pass) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        var key_store_pass = key_pass.toCharArray();
        InputStream inputStream = ConnectionServer.class.getResourceAsStream(key_file);
        if (inputStream == null) throw new Exception(String.format("[ERROR] %s not found", key_file));
        keyStore.load(inputStream, key_store_pass);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keyStore, key_store_pass);
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(kmf.getKeyManagers(), null, null);
        factory = ctx.getServerSocketFactory();
    }
    public ConnectionServer(String addr, int port) throws IOException {
        server = factory.createServerSocket();
        server.bind(new InetSocketAddress(addr, port));
    }
    public void start_listening(ConnectionHandler handler) {
        while (!server.isClosed()) {
            try {
                Connection conn = accept();
                new Thread(new Runnable() {
                    @Override public void run() {
                    handler.handle(conn);
                    conn.close();
                    }
                }).run();
            } catch (IOException e) {
                System.out.printf("[ERROR] Can't accept connection, error: %s\n", e.getMessage());
            }
        }
    }
    public Connection accept() throws IOException {
        var conn = new Connection(server.accept());
        System.out.printf("[INFO] Server accept connection: %s\n", conn.info());
        return conn;
    }
    public void close() {
        try {server.close();}
        catch (IOException ignored) {}
    }
    public boolean is_closed() {
        return server.isClosed();
    }
}
