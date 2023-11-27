package Server.Service;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;

import Server.Service.Service;
import Server.DB.*;
import Utils.*;
import Server.Server;

public class UserManagementService extends Service {
    UserInfo get_info(String token) throws SQLException {
        if (Server.accounts.containsKey(token)) {
            String username = Server.accounts.get(token).a;
            var info = UserInfoDb.query(username);
            if (info == null) throw new Error("No user with username " + username);
            return info;
        }
        throw new Error("Account is not logged in, token not found");
    }
    void add_friend(String token, String target) {
        throw new Error("Unimplemented");
    }
    ArrayList<FriendRequest> get_friend_requests(String token, FriendRequest req) {
        throw new Error("Unimplemented");
    }
    void unfriend(String token, String friend_username) {
        throw new Error("Unimplemented");
    }
    ArrayList<UserInfo> list_active_friends(String token) {
        throw new Error("Unimplemented");
    }
    ArrayList<UserInfo> find_users(String token, String pattern) {
        throw new Error("Unimplemented");
    }
    void report_spam(String token, SpamReport report) {
        throw new Error("Unimplemented");
    }
    void block_user(String token, String target_username) {
        throw new Error("Unimplemented");
    }
}
