package Client;

import Utils.*;
import Utils.Notify.*;
import java.io.IOException;
import view.Login;

public class Client {
    public static final String SV_ADDR = "localhost";
    public static final int SV_PORT = 13123;
    public static ApiClient api_c = new ApiClient(SV_ADDR, SV_PORT);
    public MessageClient msg_c;
    public String token;
    public String username;
    public NotifyClient noti_c;
    static {
        try {
            Connection.init_ssl("/.ssl/root/ca.jks", "changeit");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    private static Client instance;

    public static Client new_instance(String token, String username, AccountType type) throws IOException {
        instance = new Client(token, username, type);
        return instance;
    }
    public static Client get_instance() {
        return instance;
    }

    private Client(String token, String username, AccountType type) throws IOException {
        this.token = token;
        this.username = username;
        if (type == AccountType.User) {
            this.msg_c = new MessageClient(username, token, SV_ADDR, SV_PORT);
            this.noti_c = new NotifyClient(token, SV_ADDR, SV_PORT);
            this.noti_c.register(Unfriend.class, "MAIN:CLIENT", x -> {
                var noti = (Unfriend)x;
                String friend = noti.user_1.equals(username) ? noti.user_2 :
                        noti.user_2.equals(username) ? noti.user_1 : null;
                this.msg_c.chat_sessions.remove(friend);
            });
            this.noti_c.register(DelGroupMember.class, "MAIN:CLIENT", x -> {
                var noti = (DelGroupMember)x;
                if (noti.member.equals(username)) {
                    this.msg_c.chat_sessions.remove(noti.group_id);
                }
            });
        }
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
