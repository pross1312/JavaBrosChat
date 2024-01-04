package Server.Service;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import Server.Main;
import Server.Service.Service;
import Server.DB.*;
import Server.DB.SecretDb.SecretType;
import Utils.*;
import Utils.Notify.NewFriend;
import Utils.Notify.NewFriendRequest;

public class UserManagementService extends Service {
    UserInfo get_info(String token) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc != null) {
            String username = acc.a;
            var info = UserInfoDb.query(username);
            if (info == null) throw new Error(username + " has no info.");
            return info;
        }
        throw new Error("Can't execute get_info api, token not found");
    }
    boolean add_friend(String token, String friend) throws SQLException { // return true if new_friend (request -> false)
        var acc = Server.Main.accounts.get(token);
        if (acc != null) {
            String username = acc.a;
            if (BlockUserDb.checkBlocked(friend, username)) throw new Error("Can't add friend, you are blocked!!!");
            if (UserInfoDb.query(friend) == null) throw new Error(String.format("No username '%s' found", friend));
            if (UserFriendDb.is_friend(username, friend)) throw new Error(String.format("%s and %s are already friend.", username, friend));
            var req_from_friend = FriendRequestDb.query(friend, username);
            if (req_from_friend == null) {
                if (!FriendRequestDb.add(username, friend)) throw new Error("Can't execute add_friend api");
                Server.Main.server.notify(friend, new NewFriendRequest(username));
                return false;
            } else {
                Server.Main.db.set_auto_commit(false);
                FriendRequestDb.remove(friend, username);
                UserFriendDb.add(username, friend);
                Server.Main.db.commit();
                Server.Main.db.set_auto_commit(true);
                Server.Main.server.notify(friend, new NewFriend(username)); // notify friend that they just made a new friend
                Server.Main.server.notify(username, new NewFriend(friend)); // notify friend that they just made a new friend
                return true;
            }
        } else throw new Error("Can't execute add_friend api, token not found");
    }

    ArrayList<FriendRequest> get_friend_requests(String token) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc != null) {
            String username = acc.a;
            var result = FriendRequestDb.list_request(username);
            result.trimToSize();
            return result;
        }
        throw new Error("Can't execute get_friend_request api, token not found");
    }

    void unfriend(String token, String friend) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc != null) {
            String username = acc.a;
            UserFriendDb.remove(username, friend);
            Server.Main.db.set_auto_commit(false);
            SecretDb.remove(username, friend, SecretType.USER);
            SecretDb.remove(friend, username, SecretType.USER);
            Server.Main.db.commit();
            Server.Main.db.set_auto_commit(true);

        } else throw new Error("Can't execute unfriend api, token not found");
    }

    ArrayList<Pair<UserInfo, Boolean>> list_friends(String token) throws SQLException {
        var account = Server.Main.accounts.get(token);
        if (account != null) {
            String username = account.a;
            var infos = UserFriendDb.list_friends_info(username);
            var result = infos.stream()
                        .map(x -> new Pair<UserInfo, Boolean>(x, Server.Main.is_user_login(x.username)))
                        .collect(Collectors.toCollection(ArrayList::new));
            result.trimToSize();
            return result;
        }
        throw new Error("Can't execute list_friends api, token not found");
    }

    ArrayList<Pair<UserInfo, P2PStatus>> find_users(String token, String pattern) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if(acc == null)
            throw new Error("Can't execute find_users api, token not found");
        var users = UserInfoDb.searchUsers(pattern);
        var result = new ArrayList<Pair<UserInfo, P2PStatus>>(users.size());
        for (var usr: users) {
            if (UserFriendDb.is_friend(acc.a, usr.username))
                result.add(new Pair<>(usr, P2PStatus.FRIEND));
            else if (FriendRequestDb.query(acc.a, usr.username) != null)
                result.add(new Pair<>(usr, P2PStatus.REQUESTING));
            else result.add(new Pair<>(usr, P2PStatus.STRANGER));
        }
        result.trimToSize();
        return result;
    }

    void report_spam(String token, String target, String reason) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute report_spam api, token not found");
        var username = acc.a;
        var report = new SpamReport(username, target, reason, new java.util.Date());
        if (!SpamReportDb.add(report)) {
            throw new RuntimeException("Can't execute report_spam api");
        }
    }

    void block_user(String token, String target_username) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if(acc == null)
            throw new Error("Can't execute block_user api, token not found");
        var username = acc.a;
        if (BlockUserDb.checkBlocked(username, target_username)) {
            throw new Error("Target is already blocked");
        }
        if (!BlockUserDb.add(username, target_username)) {
            throw new RuntimeException("Can't execute block_user api");
        }
    }
    void unblock(String token, String target_username) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if(acc == null)
            throw new Error("Can't execute block_user api, token not found");
        var username = acc.a;
        if (!BlockUserDb.checkBlocked(username, target_username)) {
            throw new Error("Target is not blocked");
        }
        if (!BlockUserDb.delete(username, target_username)) {
            throw new RuntimeException("Can't execute unblock api");
        }
    }
}
