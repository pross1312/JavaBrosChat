package Server;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import Utils.Result;
import Utils.Connection;

public class NotificationServer {
    private ConcurrentHashMap<String, Connection> clients;
    private ConnectionServer server;
    private Thread thread;
    NotificationServer(String addr, int port) throws IOException {
        server = new ConnectionServer(addr, port);
        clients = new ConcurrentHashMap<String, Connection>();
    }
    public boolean notify(String username, Object notification) {
        var conn = clients.get(username);
        if (conn == null) {
            return false;
        } else if (conn.is_connected()) {
            return conn.send(notification);
        }
        clients.remove(username);
        return false;
    }
    public void start_listening() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!server.is_closed()) {
                    try (var conn = server.accept()) {
                        var obj = conn.read();
                        System.out.println("[INFO] Try adding client to notification list");
                        if (obj instanceof String token) {
                            if (Server.accounts.containsKey(token)) {
                                var username = Server.accounts.get(token).username;
                                System.out.printf("[INFO] '%s' is now listening to notification.\n", username);
                                clients.put(username, conn);
                                conn.send(Result.ok(null));
                            } else {
                                conn.send(Result.error(String.format("Can't add %s to notification list", token)));
                            }
                        } else {
                            conn.send(Result.error(String.format("Invalid class %s", obj.getClass().getName())));
                        }
                    } catch (IOException e) {
                        System.out.printf("[ERROR] Can't accept connection, error: %s\n", e.getMessage());
                    }
                }
            }
        });
        thread.start();
    }
    public void close() {
        server.close();
        try { if (thread != null) thread.join(); }
        catch (InterruptedException ignored) {}
    }
}
