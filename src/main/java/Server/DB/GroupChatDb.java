package Server.DB;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import Utils.GroupChatInfo;

public class GroupChatDb {
    private static Database db = Server.Server.db;
    private static PreparedStatement insert_sm;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO GroupChat(id, name, date) VALUES(?, ?, ?);");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void add(GroupChatInfo group_info) throws SQLException {
        insert_sm.setString(1, group_info.id);
        insert_sm.setString(2, group_info.name);
        insert_sm.setTimestamp(3, new Timestamp(group_info.created_date.getTime()));
        if (insert_sm.executeUpdate() != 1) throw new RuntimeException("Expected insert to modify aleast 1 row");
    }
}
