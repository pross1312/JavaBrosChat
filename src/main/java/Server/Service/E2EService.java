package Server.Service;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import Server.DB.*;
import Server.DB.SecretDb.SecretType;
import Utils.GroupIdentity;
import Utils.Pair;

public class E2EService extends Service {
    public void add_identity(String token, byte[] publickey) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute add api, token not found");
        var username = acc.a;
        IdentityDb.add(username, publickey);
    }
    public byte[] get_identity(String token, String name) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute add api, token not found");
        return IdentityDb.query(name);
    }
    public void add_group_secret(String token, String target, String g_id, byte[] params) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute add api, token not found");
        var username = acc.a;
        if (!GroupChatMemberDb.check_in_group(username, g_id)) {
            throw new Error(String.format("User is not in group, can't add group key"));
        }
        if (!GroupChatMemberDb.check_in_group(target, g_id)) {
            throw new Error(String.format("Target is not in group, can't add group key"));
        }
        if (!SecretDb.add(target, g_id, params, SecretType.GROUP)) {
            throw new Error(String.format("Can't add group chat secret"));
        }
    }
    public byte[] get_group_secret(String token, String group_id) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute add api, token not found");
        var username = acc.a;
        if (!GroupChatMemberDb.check_in_group(username, group_id)) {
            throw new Error(String.format("User is not in group, can't get group key"));
        }
        return SecretDb.query(username, group_id, SecretType.GROUP);
    }
    public void add_user_secret(String token, String friend, byte[] usr_to_friend, byte[] friend_to_usr) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute add api, token not found");
        var username = acc.a;
        if (!UserFriendDb.is_friend(username, friend)) {
            throw new Error(String.format("Can't add secret for someone that is not your friend"));
        }
        if (!SecretDb.add(username, friend, usr_to_friend, SecretType.USER)) {
            throw new Error(String.format("Can't add user chat secret"));
        }
        if (!SecretDb.add(friend, username, friend_to_usr, SecretType.USER)) {
            throw new Error(String.format("Can't add user chat secret"));
        }
    }
    public byte[] get_user_secret(String token, String target) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute add api, token not found");
        var username = acc.a;
        if (!UserFriendDb.is_friend(username, target)) {
            throw new Error(String.format("Can't get secret of someone that is not your friend"));
        }
        return SecretDb.query(username, target, SecretType.USER);
    }
}
