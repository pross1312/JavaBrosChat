package Server;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import Server.Server;
import Server.DB.Database;
import Utils.*;

public class Main {
    public static Server server;
    public static Mailer mailer;
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
             server = new Server("localhost", 13123);
             accounts = new ConcurrentHashMap<String, Pair<String, AccountType>>();
             mailer = new Mailer("JavaBros", "javabros29", "sski acfi ikhv zeqm");
             System.out.println("[INFO] Server initialized successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("[ERROR] Can't initialize server\n");
            System.exit(1);
        }
    }
    public static boolean is_user_login(String username) {
        return Main.accounts.searchValues(1000, x -> {
            if (x.a.equals(username)) return true;
            return null;
        }) != null;
    }
    public static void main(String[] args) {
        server.start_listening(false);
        server.close();
    }
}
