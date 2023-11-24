package Server.Service;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import Server.Service.Service;
import Utils.*;
import Server.Server;

public class UserManagementService extends Service {
    void add_friend(String token, String target) {
        Utils.todo();
    }
    ArrayList<FriendRequest> get_friend_requests(String token, FriendRequest req) {
        Utils.todo();
        return null;
    }
    void unfriend(String token, String friend_username) {
        Utils.todo();
    }
    ArrayList<UserInfo> list_active_friends(String token) {
        Utils.todo();
        return null;
    }
    ArrayList<UserInfo> find_users(String token, String pattern) {
        Utils.todo();
        return null;
    }
    void report_spam(String token, SpamReport report) {
        Utils.todo();
    }
    void block_user(String token, String target_username) {
        Utils.todo();
    }
}
