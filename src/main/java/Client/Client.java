package Client;

import Utils.*;
import view.Login;

public class Client {
    public static final String SV_ADDR = "localhost";
    public static final int SV_PORT = 13123;
    public static ApiClient api_c;
    public static MessageClient msg_c;
    public static  String token;
    public static String username;
    public static NotifyClient noti_c;
    static {
        try {
            Connection.init_ssl("/.ssl/root/ca.jks", "changeit");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        api_c = new ApiClient(SV_ADDR, SV_PORT);
    }
    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
