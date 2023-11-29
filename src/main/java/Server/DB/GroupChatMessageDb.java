package Server.DB;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import Utils.GroupChatInfo;
import Utils.GroupChatMessage;

public class GroupChatMessageDb {
    private static Database db = Server.Server.db;
    private static PreparedStatement insert_sm;
    static {
        try {
            insert_sm = db.conn.prepareStatement("EXEC add_msg_to_group ?, ?, ?, ?, ?");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void add(String sender, String text, Date date,
                           String media_id, String group_id) throws SQLException {
        insert_sm.setString(1, group_id);
        insert_sm.setString(2, sender);
        insert_sm.setTimestamp(3, new Timestamp(date.getTime()));
        insert_sm.setString(4, text);
        if (media_id != null) insert_sm.setString(5, media_id);
        else insert_sm.setNull(5, java.sql.Types.NULL);
        if (insert_sm.executeUpdate() != 1) throw new RuntimeException("Expected insert to modify aleast 1 row");
    }
}

