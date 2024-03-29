package Server.DB;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import Utils.GroupChatInfo;
import Utils.ChatMessage;

public class GroupChatMessageDb {
    private static Database db = Server.Main.db;
    private static CallableStatement insert_sm, update_read_sm;
    private static PreparedStatement get_unread_sm, get_count_unread_sm, get_all_sm;
    static {
        try {
            insert_sm = db.conn.prepareCall("{CALL add_msg_to_group(?, ?, ?, ?)}");
            update_read_sm = db.conn.prepareCall("{CALL update_group_chat_last_read(?, ?)}");
            get_unread_sm = db.conn.prepareStatement("select * from get_unread_group_msg(?, ?)");
            get_count_unread_sm = db.conn.prepareStatement("select count(id) as count from get_unread_group_msg(?, ?)");
            get_all_sm = db.conn.prepareStatement("select * from get_all_group_msg(?, ?)");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
     // this also increment last read of sender
     // done in sql
    public static void add(String sender, byte[] cipher_msg, Date date, String group_id) throws SQLException {
        insert_sm.setString(1, group_id);
        insert_sm.setString(2, sender);
        insert_sm.setTimestamp(3, new Timestamp(date.getTime()));
        insert_sm.setBytes(4, cipher_msg);
        if (insert_sm.executeUpdate() != 1) throw new RuntimeException("Expected insert to modify aleast 1 row");
    }
    public static ChatMessage parse_row(ResultSet result) throws SQLException {
        var id = result.getInt("id");
        var sender = result.getString("sender");
        var target = result.getString("group_id");
        var sent_date = new java.util.Date(result.getTimestamp("sent_date").getTime());
        var cipher_msg = result.getBytes("cipher_msg");
        return new ChatMessage(id, target, sender, sent_date, cipher_msg);
    }
    public static ArrayList<ChatMessage> get_all_msg(String username, String group_id) throws SQLException {
        get_all_sm.setString(1, username);
        get_all_sm.setString(2, group_id);
        var result = get_all_sm.executeQuery();
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        var msgs = new ArrayList<ChatMessage>();
        while (result.next()) {
            var msg = parse_row(result);
            if (msg.sender.equals("__REMOVED__")) {
                msg.cipher_msg = null;
            }
            msgs.add(msg);
        }
        return msgs;
    }
    public static ArrayList<ChatMessage> get_unread_msg(String username, String group_id) throws SQLException {
        get_unread_sm.setString(1, username);
        get_unread_sm.setString(2, group_id);
        var result = get_unread_sm.executeQuery();
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        var msgs = new ArrayList<ChatMessage>();
        while (result.next()) {
            var msg = parse_row(result);
            if (msg.sender.equals("__REMOVED__")) {
                msg.cipher_msg = null;
            }
            msgs.add(msg);
        }
        return msgs;
    }
    public static void update_last_read(String username, String group_id) throws SQLException {
        update_read_sm.setString(1, username);
        update_read_sm.setString(2, group_id);
        if (update_read_sm.executeUpdate() != 1) throw new RuntimeException("Expected this update to affect 1 row at least");
    }
    public static int get_count_unread(String username, String group_id) throws SQLException {
        get_count_unread_sm.setString(1, username);
        get_count_unread_sm.setString(2, group_id);
        var result = get_count_unread_sm.executeQuery();
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        if (!result.next()) throw new RuntimeException("Select count can't return no rows");
        return result.getInt("count") + 1; // NOTE: don't know why the query return count - 1
    }
    public static void remove_all(String username) throws SQLException {
        Statement st = db.conn.createStatement();
        var auto = db.conn.getAutoCommit();
        db.conn.setAutoCommit(false);
        st.executeUpdate(String.format("update GroupChatMessage set sender = '__REMOVED__' where sender = '%s'",
                                    username));
        if (auto) db.conn.commit();
        db.conn.setAutoCommit(auto);
        st.close();
    }
}
