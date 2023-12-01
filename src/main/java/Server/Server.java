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
    public static volatile ConcurrentHashMap<String, Pair<String, AccountType>> accounts;
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
             accounts = new ConcurrentHashMap<String, Pair<String, AccountType>>();
             System.out.println("[INFO] Server initialized successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("[ERROR] Can't initialize server\n");
            System.exit(1);
        }
    }
    public static boolean is_user_login(String username) {
        return Server.accounts.searchValues(1000, x -> {
            if (x.a.compareTo(username) == 0) return true;
            return null;
        });
    }
    public static void main(String[] args) {
        api_server.start_listening(false);
        api_server.close();
    }
}
