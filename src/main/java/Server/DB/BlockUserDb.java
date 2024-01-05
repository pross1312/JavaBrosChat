package Server.DB;

import java.sql.SQLException;

public class BlockUserDb {
    public String username;
    public String target;
    private static Database db = Server.Main.db;
    public BlockUserDb(String username, String target){
        this.username = username;
        this.target = target;
    }
    public static boolean add(String username, String target) throws SQLException {
        var st = db.conn.createStatement();
        var result = st.executeUpdate(String.format("INSERT INTO BlockUser(username, target) VALUES('%s', '%s');",
                    username, target)) == 1;
        st.close();
        return result;
    }
    public static boolean delete(String username, String target) throws SQLException {
        var st = db.conn.createStatement();
        var result = st.executeUpdate(String.format(
                    "DELETE FROM BlockUser WHERE username = '%s' AND target = '%s'",
                    username, target)) == 1;
        st.close();
        return result;
    }
    public static boolean remove_all(String username) throws SQLException {
        var st = db.conn.createStatement();
        return st.executeUpdate(String.format(
                    "DELETE FROM BlockUser WHERE username = '%s' OR target = '%s'",
                    username, username)) >= 1;
    }
    public static boolean checkBlocked(String username, String target) throws SQLException {
        var st = db.conn.createStatement();
        var result = st.executeQuery(String.format(
                    "SELECT * FROM BlockUser WHERE username = '%s' AND target = '%s'",
                    username, target));
        if (result == null) throw new RuntimeException("Unexpected");
        return result.next();
    }
}
