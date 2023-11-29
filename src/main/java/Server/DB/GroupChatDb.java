package Server.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import Utils.GroupChatInfo;

public class GroupChatDb {
    private static Database db = Server.Server.db;
    private static PreparedStatement insert_sm, list_sm;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO GroupChat(id, name, date) VALUES(?, ?, ?);");
            list_sm = db.conn.prepareStatement("SELECT * FROM list_group_of_user(?)");
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
    public static GroupChatInfo parse_group_info(ResultSet result) throws SQLException {
        return new GroupChatInfo(result.getString("id"),
                result.getString("name"),
                new Date(result.getTimestamp("date").getTime()));
    }
    public static ArrayList<GroupChatInfo> list_groups(String username) throws SQLException {
        list_sm.setString(1, username);
        var result = list_sm.executeQuery();
        if(result == null)
            throw new RuntimeException("Result set of query operation can't be null");
        var groups = new ArrayList<GroupChatInfo>();
        while (result.next()) {
            groups.add(parse_group_info(result));
        }
        return groups;
    }
}
