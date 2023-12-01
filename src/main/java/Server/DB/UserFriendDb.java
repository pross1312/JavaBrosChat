package Server.DB;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Utils.UserInfo;

public class UserFriendDb {
    private static Database db = Server.Server.db;
    private static PreparedStatement insert_sm, remove_sm, get_all_friends_sm;
    public String username;
    public String friend;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO UserFriend(username, friend, start_history_msg, last_read_msg) VALUES(?, ?, 0, 0);");
            remove_sm = db.conn.prepareStatement("DELETE FROM UserFriend WHERE username = ? AND friend = ?");
            get_all_friends_sm = db.conn.prepareStatement("SELECT * FROM list_friends_info(?)");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    private UserFriendDb(String username, String friend) {
        this.username = username;
        this.friend = friend;
    }
    public static void add(String username, String friend) throws SQLException {
        boolean is_auto_commit = db.conn.getAutoCommit();
        db.set_auto_commit(false);
        insert_sm.setString(1, username);
        insert_sm.setString(2, friend);
        insert_sm.executeUpdate();
        insert_sm.setString(1, friend);
        insert_sm.setString(2, username);
        insert_sm.executeUpdate();
        if (is_auto_commit) db.commit();
        db.set_auto_commit(is_auto_commit);
    }
    public static void remove(String username, String friend) throws SQLException {
        boolean is_auto_commit = db.conn.getAutoCommit();
        db.set_auto_commit(false);
        remove_sm.setString(1, username);
        remove_sm.setString(2, friend);
        remove_sm.executeUpdate();
        remove_sm.setString(1, friend);
        remove_sm.setString(2, username);
        remove_sm.executeUpdate();
        if (is_auto_commit) db.commit();
        db.set_auto_commit(is_auto_commit);
    }
    public static boolean is_friend(String username, String friend) throws SQLException {
        var st = db.conn.createStatement();
        var result = st.executeQuery(String.format("SELECT * FROM UserFriend WHERE username = '%s' AND friend = '%s'", username, friend));
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        boolean res = false;
        if (result.next()) res = true;
        st.close();
        return res;
    }
    public static ArrayList<UserInfo> list_friends_info(String username) throws SQLException {
        get_all_friends_sm.setString(1, username);
        var result = get_all_friends_sm.executeQuery();
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        var friends_info = new ArrayList<UserInfo>();
        while (result.next()) {
            friends_info.add(UserInfoDb.parse_row(result));
        }
        result.close();
        return friends_info;
    }
}

