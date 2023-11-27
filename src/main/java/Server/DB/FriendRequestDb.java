package Server.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import Utils.FriendRequest;

public class FriendRequestDb {
    private static Database db = Server.Server.db;
    private static PreparedStatement insert_sm, query_sm, remove_sm, list_sm;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO FriendRequest(initiator, target, date) VALUES(?, ?, ?);");
            query_sm = db.conn.prepareStatement("SELECT * FROM FriendRequest WHERE initiator = ? AND target = ?");
            remove_sm = db.conn.prepareStatement("DELETE FROM FriendRequest WHERE initiator = ? AND target = ?");
            list_sm = db.conn.prepareStatement("SELECT * FROM FriendRequest WHERE target = ?");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static boolean add(String initiator, String target) throws SQLException {
        insert_sm.setString(1, initiator);
        insert_sm.setString(2, target);
        insert_sm.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
        return insert_sm.executeUpdate() == 1;
    }
    public static boolean remove(String initiator, String target) throws SQLException {
        remove_sm.setString(1, initiator);
        remove_sm.setString(2, target);
        return remove_sm.executeUpdate() == 1;
    }
    public static FriendRequest parse_request(ResultSet result) throws SQLException {
        var ini = result.getString("initiator");
        var target = result.getString("target");
        var date = new java.util.Date(result.getTimestamp("date").getTime());
        return new FriendRequest(ini, target, date);
    }
    public static ArrayList<FriendRequest> list_request(String username) throws SQLException {
        var reqs = new ArrayList<FriendRequest>();
        list_sm.setString(1, username);
        var result = list_sm.executeQuery();
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        while (result.next()) {
            reqs.add(parse_request(result));
        }
        return reqs;
    }
    public static FriendRequest query(String initiator, String target) throws SQLException {
        query_sm.setString(1, initiator);
        query_sm.setString(2, target);
        ResultSet result = query_sm.executeQuery();
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        if (!result.next()) return null;
        return parse_request(result);
    }
}
