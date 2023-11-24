package Server;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import Server.ApiServer;
import Server.DB.Database;
import Utils.*;

public class Server {
    public static ApiServer api_server;
    public static volatile ConcurrentHashMap<String, Account> accounts;
    public static NotificationServer notification_server;
    public static Database db;
    static {
        try {
            ConnectionServer.init_ssl("/.ssl/server/server.p12", "changeit");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
             db = new Database();
             api_server = new ApiServer("localhost", 13123);
             accounts = new ConcurrentHashMap<String, Account>();
             notification_server = new NotificationServer("localhost", 13122);
             System.out.println("[INFO] Server initialized successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("[ERROR] Can't initialize server\n");
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        notification_server.start_listening();
        api_server.start_listening(false);
        api_server.close();
    }
}
