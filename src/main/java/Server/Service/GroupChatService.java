package Server.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import Server.DB.*;
import Server.Server;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import Utils.*;

public class GroupChatService extends Service {
    String create_group_chat(String token, String group_name, ArrayList<String> users_list) throws SQLException {
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
        System.out.println(users_list);
        System.out.println(user_friends);
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
        return group_id;
    }
    void send_msg_to_group(String token, String text, String group_id) throws SQLException {
        var acc = Server.accounts.get(token);
        if (acc == null) throw new Error("Can't execute send_msg_to_group api, token not found");
        var username = acc.a;
        if (GroupChatMemberDb.check_in_group(username, group_id)) {
            GroupChatMessageDb.add(username, text, new Date(), null, group_id);
        } else {
            throw new Error("Can't send message to group that you are not in");
        }
    }
}
