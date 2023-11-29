package Server.DB;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import Utils.GroupChatMemberInfo;

public class GroupChatMemberDb {
    private static Database db = Server.Server.db;
    private static CallableStatement insert_sm;
    private static PreparedStatement list_sm;
    static {
        try {
            insert_sm = db.conn.prepareCall("{call add_member_to_group(?, ?, ?, ?)}");
            list_sm = db.conn.prepareStatement("SELECT * FROM GroupChatMember WHERE group_id = ?");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void add(GroupChatMemberInfo member) throws SQLException {
        insert_sm.setString(1, member.group_id);
        insert_sm.setString(2, member.username);
        insert_sm.setTimestamp(3, new Timestamp(member.joined_date.getTime()));
        insert_sm.setBoolean(4, member.is_admin);
        insert_sm.execute();
    }
    public static boolean check_in_group(String username, String group_id) throws SQLException {
        list_sm.setString(1, group_id);
        var result = list_sm.executeQuery();
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        while (result.next()) {
            if (result.getString("username").compareTo(username) == 0) return true;
        }
        return false;
    }
}
