package Server.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import Server.DB.*;
import Utils.Notify.*;
import Server.Main;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import Utils.*;

public class GroupChatService extends Service {
    String create(String token, String group_name, ArrayList<String> users_list) throws SQLException {
        var acc = Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute create api, token not found");
        var username = acc.a;
        users_list.removeIf(x -> x.compareTo(username) == 0); // NOTE: remove if users_list contains username to manage this easier
        if (users_list.size() <= 1) throw new Error("Can only create group with 3 or more people");
        var date = new java.util.Date();
        var group_id = Base64.getEncoder().encodeToString((group_name + date.toString()).getBytes());
        if (group_id.length() > 256) throw new RuntimeException("Group id can have atmost 256 chars");
        // TODO: review how to create group_id
        var group = new GroupChatInfo(group_id, group_name, date);
        // TODO: caching ? for both this service and usermanagement service
        var user_friends = UserFriendDb.list_friends_info(username)
                                       .stream()
                                       .map(x -> x.username)
                                       .collect(Collectors.toList());
        if (!user_friends.containsAll(users_list))
            throw new Error("Can't create group chat with people that are not friends");
        GroupChatDb.add(group);
        Main.db.set_auto_commit(false);
        for (var member : users_list) {
            GroupChatMemberDb.add(new GroupChatMemberInfo(group_id, member, date, false));
        }
        GroupChatMemberDb.add(new GroupChatMemberInfo(group_id, username, date, true));
        Main.db.commit();
        Main.db.set_auto_commit(true);
        users_list.add(username);
        return group_id;
    }
    void send_msg(String token, byte[] cipher_msg, String group_id, String receiver) throws SQLException {
        var acc = Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute send_msg api, token not found");
        var username = acc.a;
        if (GroupChatMemberDb.check_in_group(username, group_id)) {
            Main.server.notify(receiver,
                    new NewGroupMsg(group_id, GroupChatMessageDb.get_count_unread(receiver, group_id)));
            GroupChatMessageDb.add(receiver, username, cipher_msg, new Date(), null, group_id);

        } else {
            throw new Error("Can't send message to group that you are not in");
        }
    }
    ArrayList<ChatMessage> get_unread_msg(String token, String group_id) throws SQLException {
        var acc = Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute get_unread_msg api, token not found");
        var username = acc.a;
        if (!GroupChatMemberDb.check_in_group(username, group_id)) throw new Error("Can't get message of group that you are not in");
        var result = GroupChatMessageDb.get_unread_msg(username, group_id);
        GroupChatMessageDb.update_last_read(username, group_id);
        result.trimToSize();
        return result;
    }
    ArrayList<GroupChatInfo> list_groups(String token) throws SQLException {
        var acc = Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute list_user_groups api, token not found");
        var username = acc.a;
        var result = GroupChatDb.list_groups(username);
        result.trimToSize();
        return result;
    }
    void remove_member(String token, String group_id, String target_username) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute remove_member api, token not found");
        var username = acc.a;
        var members = GroupChatMemberDb.list_members(group_id);
        if (members.size() == 1) throw new Error("Can't remove last member in group");
        if (!GroupChatMemberDb.check_in_group(target_username, group_id)) {
            throw new Error(String.format("'%s' is not in group", target_username));
        }
        if (!GroupChatMemberDb.is_admin(username, group_id)) {
            throw new Error(String.format("'%s' is not admin of the group", username));
        }
        GroupChatMemberDb.remove(target_username, group_id);
        GroupChatMemberDb.list_members(group_id).forEach((member) -> {
            Main.server.notify(member, new DelGroupMember(group_id, target_username));
        });
    }
    void add_member(String token, String group_id, String target_username) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute add_member api, token not found");
        var username = acc.a;
        if (GroupChatMemberDb.check_in_group(target_username, group_id))
            throw new Error(String.format("'%s' is already in group", target_username));
        if (!UserFriendDb.is_friend(username, target_username))
            throw new Error("Can't add people that are not your friends to group");
        GroupChatMemberDb.list_members(group_id).forEach((member) -> {
            Main.server.notify(member, new NewGroupMember(group_id, target_username));
        });
        GroupChatMemberDb.add(new GroupChatMemberInfo(group_id, target_username, new Date(), false));
    }
    void set_admin(String token, String group_id, String target_username, Boolean is_admin) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute set_admin api, token not found");
        var username = acc.a;
        if (!GroupChatMemberDb.is_admin(username, group_id))
            throw new Error(String.format("Can't set admin '%s' is not admin of the group", username));
        if (!GroupChatMemberDb.check_in_group(target_username, group_id))
            throw new Error(String.format("Can't set admin, '%s' is not in group", target_username));
        GroupChatMemberDb.set_admin(target_username, group_id, is_admin);
    }
    void rename(String token, String group_id, String new_name) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute rename api, token not found");
        var username = acc.a;
        if (!GroupChatMemberDb.check_in_group(username, group_id))
            throw new Error(String.format("Can't rename '%s' is not in group", username));
        GroupChatDb.rename(group_id, new_name);
    }
    ArrayList<String> list_members(String token, String group_id) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute rename api, token not found");
        var username = acc.a;
        if (!GroupChatMemberDb.check_in_group(username, group_id)) throw new Error("Can't get list of member of group that you are not in");
        var result = GroupChatMemberDb.list_members(group_id);
        result.trimToSize();
        return result;
    }
}
