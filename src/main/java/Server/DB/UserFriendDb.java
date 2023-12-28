package Server.DB;

import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Utils.UserInfo;

public class UserFriendDb {
    private static Database db = Server.Main.db;
    private static PreparedStatement insert_sm, get_all_friends_sm, clear_hs_sm;
    public String username;
    public String friend;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO UserFriend(username, friend, start_history_msg, last_read_msg) VALUES(?, ?, 0, 0);");
            get_all_friends_sm = db.conn.prepareStatement("SELECT * FROM list_friends_info(?)");
            clear_hs_sm = db.conn.prepareStatement("UPDATE UserFriend SET start_history_msg = last_read_msg WHERE username = ? AND friend = ?");
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
    public static void remove_all(String username) throws SQLException {
        Statement st = db.conn.createStatement();
        boolean is_auto_commit = db.conn.getAutoCommit();
        db.set_auto_commit(false);
        st.execute(String.format("DELETE FROM FriendChat WHERE sender = '%s' OR friend = '%s'",
                    username, username));
        st.execute(String.format("DELETE FROM UserFriend WHERE username = '%s' OR friend = '%s'",
                    username, username));
        if (is_auto_commit) db.commit();
        db.set_auto_commit(is_auto_commit);
        st.close();
    }
    public static void remove(String username, String friend) throws SQLException {
        Statement st = db.conn.createStatement();
        boolean is_auto_commit = db.conn.getAutoCommit();
        db.set_auto_commit(false);
        st.execute(String.format("DELETE FROM UserFriend WHERE (username = '%s' AND friend = '%s') OR (username = '%s' AND friend = '%s')",
                   username, friend, friend, username));
        st.execute(String.format("DELETE FROM FriendChat WHERE (sender = '%s' AND friend = '%s') OR (sender = '%s' AND friend = '%s')",
                   username, friend, friend, username));
        if (is_auto_commit) db.commit();
        db.set_auto_commit(is_auto_commit);
        st.close();
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
    public static boolean clear_history(String username, String friend) throws SQLException {
        clear_hs_sm.setString(1, username);
        clear_hs_sm.setString(2, friend);
        return clear_hs_sm.executeUpdate() == 1;
    }
}

