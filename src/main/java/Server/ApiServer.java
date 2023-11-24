package Server;
import java.io.IOException;
import java.io.NotSerializableException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

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
        var obj = conn.read();
        if (obj == null) { // receive nothing ???
            // TODO: do something ?
        } else if (obj instanceof ApiCall) {
            var api = (ApiCall)obj;
            System.out.printf("[INFO] Client invoke '%s' api of '%s' with %s\n", api.name, api.service, api.args.toString());
            Result response = null;
    // public String service, name;
    // public ArrayList<Object>  args;
            if (services.containsKey(api.service)) {
                try {
                    var result = services.get(api.service).invoke(api.name, api.args.toArray());
                    response = Result.ok(result);
                } catch (NoSuchMethodException e) { // can't find api
                    response = Result.error(
                            String.format("Could not invoke function '%s' of '%s' with %s",
                                    api.service, api.name, api.args.toString()));
                } catch (Error e) { // api exception
                    response = Result.error(e.getMessage());
                } catch (Throwable e) {
                    e.printStackTrace();
                    response = Result.error("Internal server error");
                }
            } else {
                response = Result.error(String.format("Service not found %s", api.service));
            }
            conn.send(response);
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
