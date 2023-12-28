package Server.DB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BlockUserDb {
    public String username;
    public String target;
    private static Database db = Server.Main.db;
    private static PreparedStatement insert_sm, delete_sm, query_sm;
    public BlockUserDb(String username, String target){
        this.username = username;
        this.target = target;
    }
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO BlockUser(username, target) VALUES(?, ?);");
            delete_sm = db.conn.prepareStatement("DELETE FROM BlockUser WHERE username = ? AND target = ?");
            query_sm = db.conn.prepareStatement("SELECT * FROM BlockUser WHERE username = ? AND target = ?");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static boolean add(String username, String target) throws SQLException {
        insert_sm.setString(1, username);
        insert_sm.setString(2, target);
        return insert_sm.executeUpdate() == 1;
    }
    public static boolean delete(String username, String target) throws SQLException {
        delete_sm.setString(1, username);
        delete_sm.setString(2, target);
        return delete_sm.executeUpdate() == 1;
    }
    public static boolean checkBlocked(String username, String target) throws SQLException {
        query_sm.setString(1, username);
        query_sm.setString(2, target);
        var result = query_sm.executeQuery();
        return result == null;
    }
}
