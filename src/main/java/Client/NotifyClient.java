package Client;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import Utils.Connection;
import Utils.ChatMessage;
import Utils.Notify.*;
import Utils.Pair;
import Utils.NotifyConnect;
import Utils.ResultError;
import Utils.ResultOk;
import Utils.Connection;
public class NotifyClient {
    private Connection conn;
    String addr;
    int port;
    Thread thread;
    ConcurrentHashMap<Class<?>, LinkedList<Pair<String, Consumer<Notify>>>> handlers;
    public NotifyClient(String token, String addr, int port) throws IOException {
        this.addr = addr;
        this.port = port;
        this.conn = null;
        this.thread = null;
        conn = new Connection(addr, port);
        handlers = new ConcurrentHashMap<>();
        handshake(token);
    }
    public void register(Class<?> type, String register_name, Consumer<Notify> handler) {
        var consumers = handlers.get(type);
        if (consumers == null) {
            consumers = new LinkedList<>();
            consumers.addFirst(new Pair<>(register_name, handler));
            handlers.put(type, consumers);
        } else {
            for (var obj : consumers) {
                if (obj.a.equals(register_name)) {
                    obj.b = handler;
                    return;
                }
            }
            consumers.addFirst(new Pair<>(register_name, handler));
        }
    }
    public void unregister(Class<?> type, String register_name) {
        var consumers = handlers.get(type);
        if (consumers != null) {
            consumers.removeIf(x -> x.a.equals(register_name));
        }
    }
    public void handshake(String token) throws IOException {
        if (conn == null || !conn.is_connected()) {
            conn = new Connection(addr, port);
        }
        if (!conn.send(new NotifyConnect(token))) {
            throw new IOException("Handshake to notification server failed");
        }
        var result = conn.read();
        if (result instanceof ResultOk okk) {
            start_listening();
        } else if (result instanceof ResultError err) {
            System.out.println(err.msg());
            throw new IOException("Handshake failed");
        }
    }
    void start_listening() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (conn.is_connected()) {
                    var result = conn.read();
                    if (result == null) {
                        conn.close();
                        break;
                    }  else if (result instanceof Notify noti) {
                        var consumers = handlers.get(noti.getClass());
                        if (consumers == null) {
                            System.out.printf("Unhandled notification type '%s'\n", noti.getClass().getName());
                        } else {
                            consumers.forEach((x) -> {
                                x.b.accept(noti);
                            });
                        }
                    } else {
                        throw new RuntimeException(String.format("Unable to process type %s from server", result.getClass().getName()));
                    }
                }
                conn.close();
                System.out.println("Notification client disconnected");
                System.out.println("Can't receive notification anymore");
                System.out.println("Try handshake again if you want");
            }
        });
        thread.start();
    }
    public void close() {
        conn.close();
        try {
            thread.join();
        } catch (Exception ignored) {}
    }
}
