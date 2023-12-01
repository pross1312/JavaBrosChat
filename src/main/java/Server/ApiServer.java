package Server;
import java.io.IOException;
import java.io.NotSerializableException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import Utils.Connection;
import Server.Service.*;
import Api.ApiCall;
import Utils.Result;
import Utils.ResultError;

public class ApiServer implements ConnectionHandler {
    // final static int limit;
    private ConnectionServer server;
    private HashMap<String, Service> services;
    private Thread listening_thread;

    ApiServer(String addr, int port) throws IOException {
        server = new ConnectionServer(addr, port);

        services = new HashMap<String, Service>();
        // register all service here
        services.put("UserManagementService", new UserManagementService());
        services.put("AccountService", new AccountService());
        services.put("AdminService", new AdminService());
        services.put("GroupChatService", new GroupChatService());
        services.put("FriendChatService", new FriendChatService());
        // ...........................................
    }
    void start_listening(boolean on_new_thread) {
        if (on_new_thread) {
            var tmp = this;
            listening_thread = new Thread(new Runnable() {
                @Override public void run() {
                    server.start_listening(tmp);
                }
            });
            listening_thread.start();
        } else {
            server.start_listening(this);
        }
    }

    @Override public void handle(Connection conn) {
         // NOTE: keep reading if client request api continuously because ssl handshake takes time
         // close when can't read data
        var obj = conn.read();
        if (obj == null) {
             // receive nothing ???
        } else if (obj instanceof ApiCall) {
            var api = (ApiCall)obj;
            System.out.printf("[INFO] Client invoke '%s' api of '%s' with %s\n", api.name, api.service, api.args.toString());
            Result response = null;
            if (services.containsKey(api.service)) {
                try {
                    var result = services.get(api.service).invoke(api.name, api.args.toArray());
                    response = Result.ok(result);
                } catch (NoSuchMethodException e) { // can't find api
                    response = Result.error(
                            String.format("Could not invoke function '%s' of '%s' with %s",
                                api.name, api.service,
                                api.args.stream()
                                        .map(x -> x.getClass().getName())
                                        .collect(Collectors.toList())));
                } catch (Error e) { // api exception
                    response = Result.error(e.getMessage());
                } catch (Throwable e) {
                    e.printStackTrace();
                    response = Result.error("Something went wronggggg.");
                }
            } else {
                response = Result.error(String.format("Service not found %s", api.service));
            }
            if (!conn.send(response)) {
                System.out.println("[ERROR] Can't send result back to client");
            }
        } else { // not api call ???
            System.out.printf("[ERROR] Could not handle '%s', data: %s\n", obj.getClass().getName(), obj.toString());
        }
        conn.close();
    }
    public void close() {
        server.close();
        if (listening_thread != null) {
            try {
                listening_thread.join();
            } catch(InterruptedException ignored) {
            }
        }
    }
}
