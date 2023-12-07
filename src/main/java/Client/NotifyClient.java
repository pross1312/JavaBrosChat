package Client;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import Utils.Connection;
import Utils.ChatMessage;
import Utils.Notify.*;
import Utils.NotifyConnect;
import Utils.ResultError;
import Utils.ResultOk;
import Utils.Connection;
public class NotifyClient {
    private Connection conn;
    String addr;
    int port;
    String username;
    String token;
    Thread thread;
    public NotifyClient(String token, String addr, int port) throws IOException {
        this.addr = addr;
        this.port = port;
        this.conn = null;
        this.thread = null;
        this.token = token;
        conn = new Connection(addr, port);
        System.out.println("QIWOEJOIQWEJ");
        handshake(token);
    }
    public void handshake(String token) throws IOException {
        if (conn == null || !conn.is_connected()) {
            conn = new Connection(addr, port);
            System.out.println("Connected");
        }
        if (!conn.send(new NotifyConnect(token))) {
            throw new IOException("Handshake to notification server failed");
        }
        var result = conn.read();
        if (result instanceof ResultOk okk) {
            System.out.println((String)okk.data());
            System.out.println("Handshake successful, start listening to notification");
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
                    }  else if (result instanceof NewGroupMsg noti) {
                        try {
                            ClientA.api_c.async_invoke_api(x -> {
                                if (x instanceof ResultOk ok) {
                                    var msgs = (ArrayList<ChatMessage>)ok.data();
                                    msgs.forEach(msg -> System.out.println(msg.msg));
                                } else if (x instanceof ResultError err) {
                                    System.out.println(err.msg());
                                }
                            }, "GroupChatService", "get_unread_msg", token, noti.group_id);
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    } else if (result instanceof NewFriendMsg noti) {
                        try {
                            ClientA.api_c.async_invoke_api(x -> {
                                if (x instanceof ResultOk ok) {
                                    var msgs = (ArrayList<ChatMessage>)ok.data();
                                    msgs.forEach(msg -> System.out.println(msg.msg));
                                } else if (x instanceof ResultError err) {
                                    System.out.println(err.msg());
                                }
                            }, "FriendChatService", "get_unread_msg", token, noti.sender);
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    } else if (result instanceof NewFriendLogin new_login) {
                        System.out.printf("[%s] just logged in\n", new_login.friend);
                    } else if (result instanceof Notify) {
                        System.out.printf("Unhandled notification type '%s'\n", result.getClass().getName());
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
