package Server.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import Server.DB.*;
import Utils.NewMsgNotify;
import Server.Server;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import Utils.*;

public class GroupChatService extends Service {
    ConcurrentHashMap<String, List<String>> group_users;
    public GroupChatService() {
        group_users = new ConcurrentHashMap<String, List<String>>();
    }
    String create_group(String token, String group_name, ArrayList<String> users_list) throws SQLException {
        var acc = Server.accounts.get(token);
        if (acc == null) throw new Error("Can't execute create_group_chat api, token not found");
        var username = acc.a;
        users_list.removeIf(x -> x.compareTo(username) == 0); // NOTE: remove if users_list contains username to manage this easier
        if (users_list.size() < 1) throw new Error("Can't create group with you only");
        var date = new java.util.Date();
        var group_id = Base64.getEncoder().encodeToString((group_name + date.toString()).getBytes());
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
        Server.db.set_auto_commit(false);
        for (var member : users_list) {
            GroupChatMemberDb.add(new GroupChatMemberInfo(group_id, member, date, false));
        }
        GroupChatMemberDb.add(new GroupChatMemberInfo(group_id, username, date, true));
        Server.db.commit();
        Server.db.set_auto_commit(true);
        users_list.add(username);
        group_users.put(group_id, users_list);
        return group_id;
    }
    void send_msg_to_group(String token, String text, String group_id) throws SQLException {
        var acc = Server.accounts.get(token);
        if (acc == null) throw new Error("Can't execute send_msg_to_group api, token not found");
        var username = acc.a;
        if (GroupChatMemberDb.check_in_group(username, group_id)) {
            var members = group_users.get(group_id);
            if (members == null) {
                members = GroupChatMemberDb.list_members(group_id);
            }
            for (var x : members) {
                if (x.compareTo(username) != 0) {
                    Server.notification_server.notify(x,
                            new NewMsgNotify(group_id, GroupChatMessageDb.get_count_unread(x, group_id)));
                }
            }
            GroupChatMessageDb.add(username, text, new Date(), null, group_id);

        } else {
            throw new Error("Can't send message to group that you are not in");
        }
    }
    ArrayList<GroupChatMessage> get_unread_msg(String token, String group_id) throws SQLException {
        var acc = Server.accounts.get(token);
        if (acc == null) throw new Error("Can't execute list_user_groups api, token not found");
        var username = acc.a;
        if (!GroupChatMemberDb.check_in_group(username, group_id)) throw new Error("Can't get message of group that you are not in");
        var result = GroupChatMessageDb.get_unread_msg(username, group_id);
        GroupChatMessageDb.update_last_read(username, group_id);
        result.trimToSize();
        return result;
    }
    ArrayList<GroupChatInfo> list_groups(String token) throws SQLException {
        var acc = Server.accounts.get(token);
        if (acc == null) throw new Error("Can't execute list_user_groups api, token not found");
        var username = acc.a;
        var result = GroupChatDb.list_groups(username);
        for (var x : result) {
            group_users.put(x.id, GroupChatMemberDb.list_members(x.id));
        }
        result.trimToSize();
        return result;
    }
    void remove_member(String token, String group_id, String target_username) throws SQLException {
        var acc = Server.accounts.get(token);
        if (acc == null) throw new Error("Can't execute remove_member api, token not found");
        var username = acc.a;
        if (!GroupChatMemberDb.check_in_group(target_username, group_id)) {
            throw new Error(String.format("'%s' is not in group", target_username));
        }
        if (!GroupChatMemberDb.is_admin(username, group_id)) {
            throw new Error(String.format("'%s' is not admin of the group", username));
        }
        var group = group_users.get(group_id);
        assert group != null : "Group cache must contain active group";
        assert group.remove(target_username) : "Group cache must contain correct members";
        GroupChatMemberDb.remove(target_username, group_id);
    }
    void add_member(String token, String group_id, String target_username) throws SQLException {
    }
    void set_admin(String token, String group_id, String target_username) throws SQLException {
    }
}
