package Client;

import Utils.*;
import view.Login;

public class Client {
    public static ApiClient api_c;
    public static  String token;
    static {
        try {
            Connection.init_ssl("/.ssl/root/ca.jks", "changeit");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        api_c = new ApiClient("localhost", 13123);
    }
    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
