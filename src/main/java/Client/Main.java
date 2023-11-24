package Client;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Date;

import Api.ApiCall;
import Utils.*;
import Utils.Account.AccountType;

public class Main {
    public static Connection noti_sock;
    public static void main(String[] args) {
        try {
            Connection.init_ssl("/.ssl/root/ca.jks", "changeit");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        var client = new ApiClient("localhost", 13123);
        try {
            var result = client.invoke_api("AccountService", "login", "admin", "admin");
            if (result instanceof ResultError err) {
                System.out.println(err.msg());
            } else if (result instanceof ResultOk ok) {
                var data = (Pair<String, AccountType>)ok.data();
                System.out.println(data.a);
                System.out.println(data.b);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Exit");
    }
}
