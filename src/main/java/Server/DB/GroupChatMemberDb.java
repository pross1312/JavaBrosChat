package Server.DB;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import Utils.GroupChatInfo;
import Utils.GroupChatMemberInfo;

public class GroupChatMemberDb {
    private static Database db = Server.Main.db;
    private static CallableStatement insert_sm;
    private static PreparedStatement list_sm, clear_hs_sm;
    static {
        try {
            insert_sm = db.conn.prepareCall("{call add_member_to_group(?, ?, ?, ?)}");
            list_sm = db.conn.prepareStatement("SELECT * FROM GroupChatMember WHERE group_id = ?");
            clear_hs_sm = db.conn.prepareStatement("UPDATE GroupChatMember SET start_history_msg = last_read_msg WHERE group_id = ? AND username = ?");
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
    public static ArrayList<String> list_members(String group_id) throws SQLException {
        list_sm.setString(1, group_id);
        var result = list_sm.executeQuery();
        var members = new ArrayList<String>();
        while (result.next()) members.add(result.getString("username"));
        return members;
    }
    public static boolean is_admin(String username, String group_id) throws SQLException {
        Statement st = db.conn.createStatement();
        var sql = String.format("select * from GroupChatMember where group_id = '%s' and username = '%s' and is_admin = 'true'",
                                        group_id, username);
        System.out.println(sql);
        var result = st.executeQuery(sql);
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        var rs = result.next();
        st.close();
        return rs;
    }
    public static void remove(String username, String group_id) throws SQLException {
        Statement st = db.conn.createStatement();
        st.executeUpdate(String.format("delete from GroupChatMember where group_id = '%s' and username = '%s'",
                                    group_id, username));
        st.close();
    }
    public static void remove_all(String username) throws SQLException {
        Statement st = db.conn.createStatement();
        st.executeUpdate(String.format("delete from GroupChatMember where username = '%s'",
                                    username));
        st.close();
    }
    public static boolean check_in_group(String username, String group_id) throws SQLException {
        list_sm.setString(1, group_id);
        var result = list_sm.executeQuery();
        assert result != null : "Result set of query operation can't be null";
        while (result.next()) {
            if (result.getString("username").compareTo(username) == 0) return true;
        }
        return false;
    }
    public static void set_admin(String username, String group_id, boolean is_admin) throws SQLException {
        Statement st = db.conn.createStatement();
        st.executeUpdate(String.format("UPDATE GroupChatMember SET is_admin = '%s' WHERE username = '%s' AND group_id = '%s'",
                                    is_admin, username, group_id));
        st.close();
    }
    public static ArrayList<GroupChatMemberInfo> list_all_members(String group_id) throws SQLException {
        ArrayList<GroupChatMemberInfo> arr = new ArrayList<>();
        list_sm.setString(1, group_id);
        var result = list_sm.executeQuery();
        if(result == null)
            throw new RuntimeException("Result set of query operation can't be null");
        while(result.next()){
            String id = result.getString("group_id");
            String name = result.getString("username");
            Date date = result.getDate("joined_date");
            boolean admin = result.getBoolean("is_admin");
            arr.add(new GroupChatMemberInfo(id, name, date, admin));
        }
        result.close();
        return arr;
    }
    public static boolean clear_history(String group_id, String username) throws SQLException {
        clear_hs_sm.setString(1, group_id);
        clear_hs_sm.setString(2, username);
        return clear_hs_sm.executeUpdate() == 1;
    }
}
