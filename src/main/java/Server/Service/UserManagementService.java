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
        var acc = Server.accounts.get(token);
        if (acc != null) {
            String username = acc.a;
            var info = UserInfoDb.query(username);
            if (info == null) throw new Error(username + " has no info.");
            return info;
        }
        throw new Error("Can't execute get_info api, token not found");
    }
    void add_friend(String token, String friend) throws SQLException {
        var acc = Server.accounts.get(token);
        // TODO: notify friend new friend request if 'friend' is logged in ?
        if (acc != null) {
            String username = acc.a;
            var req_from_friend = FriendRequestDb.query(friend, username);
            if (req_from_friend == null) {
                if (!FriendRequestDb.add(username, friend)) throw new Error("Can't execute add_friend api");
            } else {
                System.out.println("Checking");
                Server.db.set_auto_commit(false);
                FriendRequestDb.remove(friend, username);
                UserFriendDb.add(username, friend);
                Server.db.commit();
                Server.db.set_auto_commit(true);
            }
        } else throw new Error("Can't execute add_friend api, token not found");
    }

    ArrayList<FriendRequest> get_friend_requests(String token) throws SQLException {
        var acc = Server.accounts.get(token);
        if (acc != null) {
            String username = acc.a;
            return FriendRequestDb.list_request(username);
        }
        throw new Error("Can't execute get_friend_request api, token not found");
    }

    void unfriend(String token, String friend) throws SQLException {
        var acc = Server.accounts.get(token);
        if (acc != null) {
            String username = acc.a;
            UserFriendDb.remove(username, friend);
        } else throw new Error("Can't execute unfriend api, token not found");
    }

    ArrayList<Pair<UserInfo, Boolean>> list_friends(String token) throws SQLException {
        var account = Server.accounts.get(token);
        if (account != null) {
            String username = account.a;
            var infos = UserFriendDb.list_friends_info(username);
            var result = new ArrayList<Pair<UserInfo, Boolean>>(infos.size());
            for (int i = 0; i < infos.size(); i++) {
                var data = new Pair<UserInfo, Boolean>(infos.get(i), false);
                for (var acc : Server.accounts.entrySet()) {
                    if (acc.getValue().a.compareTo(infos.get(i).username) == 0) {
                        data.b = true;
                        break;
                    }
                }
                result.add(data);
            }
            return result;
        }
        throw new Error("Can't execute list_friends api, token not found");
    }

    ArrayList<UserInfo> find_users(String token, String pattern) {
        throw new Error("Unimplemented");
    }

    void report_spam(String token, String target, String reason) throws SQLException {
        var acc = Server.accounts.get(token);
        if (acc == null) throw new Error("Can't execute report_spam api, token not found");
        var username = acc.a;
        var report = new SpamReport(username, target, reason, new java.util.Date());
        if (!SpamReportDb.add(report)) {
            throw new RuntimeException("Can't execute report_spam api");
        }
    }

    void block_user(String token, String target_username) {
        throw new Error("Unimplemented");
    }
}
