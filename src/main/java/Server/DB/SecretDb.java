package Server.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Utils.GroupIdentity;
import Utils.Pair;

public class SecretDb {
    public enum SecretType {
        GROUP,
        USER,
    };
    private static Database db = Server.Main.db;
    private static PreparedStatement insert_sm_group, insert_sm_user;
    static {
        try {
            insert_sm_group = db.conn.prepareStatement("INSERT INTO GroupChatSecret VALUES(?, ?, ?)");
            insert_sm_user = db.conn.prepareStatement("INSERT INTO UserChatSecret VALUES(?, ?, ?)");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static boolean add(String username, String target, byte[] params, SecretType type) throws SQLException {
        var sql = type == SecretType.GROUP ? insert_sm_group : insert_sm_user;
        sql.setString(1, username);
        sql.setString(2, target);
        sql.setBytes(3, params);
        return sql.executeUpdate() == 1;
    }
    public static byte[] query(String username, String target, SecretType type) throws SQLException {
        var st = db.conn.createStatement();
        var result = st.executeQuery(String.format("SELECT * FROM %s WHERE username = '%s' AND target = '%s'",
                    type == SecretType.GROUP ? "GroupChatSecret" : "UserChatSecret", username, target));
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        if (!result.next()) return null;
        var params = result.getBytes("aes_params");
        result.close();
        st.close();
        return params;
    }
    public static boolean remove(String username, String target, SecretType type) throws SQLException {
        var st = db.conn.createStatement();
        return st.executeUpdate(String.format("DELETE FROM %s WHERE username = '%s' AND target = '%s'",
                        type == SecretType.GROUP ? "GroupChatSecret" : "UserChatSecret", username, target)) == 1;
    }
    public static boolean remove_all(String username, SecretType type) throws SQLException {
        var st = db.conn.createStatement();
        return st.executeUpdate(String.format("DELETE FROM %s WHERE username = '%s' OR target = '%s'",
                        type == SecretType.GROUP ? "GroupChatSecret" : "UserChatSecret", username, username)) == 1;
    }
}
