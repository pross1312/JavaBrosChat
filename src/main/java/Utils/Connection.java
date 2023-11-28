package Utils;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyStore;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class Connection implements Closeable {
    private static SocketFactory factory = SocketFactory.getDefault();
    public static void init_ssl(String trust_file, String trust_pass) throws Exception {
        KeyStore trustStore = KeyStore.getInstance("PKCS12");
        var password = trust_pass.toCharArray();
        InputStream inputStream = Connection.class.getResourceAsStream(trust_file);
        if (inputStream == null) throw new Exception(String.format("[ERROR] %s not found", trust_file));
        trustStore.load(inputStream, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("PKIX", "SunJSSE");
        trustManagerFactory.init(trustStore);
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, trustManagerFactory.getTrustManagers(), null);
        factory = ctx.getSocketFactory();
    }
    private Socket sock;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;

    // be careful, this may block if only 1 side construct object input/output stream
    public Connection(InetSocketAddress e) throws IOException {
        sock = factory.createSocket(e.getAddress(), e.getPort());
        writer = new ObjectOutputStream(sock.getOutputStream());
        reader = new ObjectInputStream(sock.getInputStream());
    }
    // be careful, this may block if only 1 side construct object input/output stream
    public Connection(String address, int port) throws IOException {
        sock = factory.createSocket(address, port);
        writer = new ObjectOutputStream(sock.getOutputStream());
        reader = new ObjectInputStream(sock.getInputStream());
    }
    // be careful, this may block if only 1 side construct object input/output stream
    public Connection(Socket s) throws IOException {
        sock = s;
        writer = new ObjectOutputStream(sock.getOutputStream());
        reader = new ObjectInputStream(sock.getInputStream());
    }
    public String info() {
        return String.format("[%s, %d]", sock.getLocalAddress().toString(), sock.getLocalPort());
    }
	public Object read() {
        Object obj = null;
        try {
            obj = reader.readObject();
        } catch (ClassNotFoundException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            System.out.printf("[ERROR] Can't read data, error: %s\n", e);
            return null;
        }
        return obj;
    }
	public boolean send(Object message) {
        try {
            writer.writeObject(message);
            writer.flush();
        } catch (IOException e) {
            System.out.printf("[ERROR] Can't write data, error: %s\n", e);
            return false;
        }
        return true;
	}
    public void close() {
        try {
            sock.close();
        } catch (IOException e) {
            System.out.println("[ERROR] Can't close connection");
        }
    }
    public boolean is_connected() {
        return sock.isConnected();
    }
    public boolean set_timeout(int timeout) {
        try {
            sock.setSoTimeout(timeout);
            return true;
        } catch(SocketException ignored) {}
        return false;
    }
}
