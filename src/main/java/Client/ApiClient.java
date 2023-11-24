package Client;

import java.io.IOException;
import java.io.NotSerializableException;
import java.util.function.Consumer;

import Utils.Connection;
import Api.ApiCall;
import Utils.Result;

public class ApiClient {
    String addr;
    int port;
    ApiClient(String addr, int port) {
        this.addr = addr;
        this.port = port;
    }
    Result invoke_api(String service, String name, Object... args) throws RuntimeException, IOException {
        Connection conn;
        conn = new Connection(addr, port);
        var api = new ApiCall(service, name, args);
        conn.send(api);
        Object result = conn.read();
        conn.close();
        if (result != null && !(result instanceof Result)) {
            throw new RuntimeException(
                String.format("Expected Result but received invalid result of class '%s'",
                    result != null ? result.getClass().getName() : "null")
            );
        }
        return (Result)result;
    }
    Thread async_invoke_api(Consumer<Result> handler, String service, String name, Object... args) throws RuntimeException, IOException {
        Connection conn;
        conn = new Connection(addr, port);
        var thread = new Thread(new Runnable() {
            @Override public void run() throws RuntimeException {
                var api = new ApiCall(service, name, args);
                conn.send(api);
                Object result = conn.read();
                conn.close();
                if (!(result instanceof Result)) {
                    throw new RuntimeException(
                            String.format("Expected Result but received invalid result of class '%s'",
                                result != null ? result.getClass().getName() : "null")
                            );
                } else handler.accept((Result)result);
            }
        });
        thread.start();
        return thread; 
    }
}
