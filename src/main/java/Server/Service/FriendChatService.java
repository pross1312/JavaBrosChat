package Server.Service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import Server.Server;
import Server.DB.*;
import Utils.*;

public class FriendChatService extends Service {
    void send_msg(String token, String text, String friend) throws SQLException {
        var acc = Server.accounts.get(token);
        if (acc == null) throw new Error("Can't execute send_msg api, token not found");
        var username = acc.a;
        if (!UserFriendDb.is_friend(username, friend))
            throw new Error(String.format("'%s' is not your friend", friend));
        Server.api_server.notify(friend, new NewFriendMsg(username, 1));
        FriendChatDb.add(username, text, new Date(), null, friend);
    }
    ArrayList<ChatMessage> get_unread_msg(String token, String friend) throws SQLException {
        var acc = Server.accounts.get(token);
        if (acc == null) throw new Error("Can't execute get_unread_msg api, token not found");
        var username = acc.a;
        if (!UserFriendDb.is_friend(username, friend))
            throw new Error(String.format("'%s' is not your friend", friend));
        var result = FriendChatDb.get_unread_msg(username, friend);
        FriendChatDb.update_last_read(username, friend);
        result.trimToSize();
        return result;
    }
}
