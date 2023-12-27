package Server.DB;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import Utils.Pair;

public class IdentityDb {
    private static Database db = Server.Main.db;
    private static CallableStatement insert_sm;
    static {
        try {
            insert_sm = db.conn.prepareCall("{call update_identity(?, ?)}");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static boolean add(String username, byte[] public_key) throws SQLException {
        insert_sm.setString(1, username);
        insert_sm.setBytes(2, public_key);
        return insert_sm.executeUpdate() == 1;
    }
    public static byte[] query(String username) throws SQLException {
        var st = db.conn.createStatement();
        ResultSet result = st.executeQuery(String.format("SELECT * FROM UserIdentity WHERE username = '%s'",
                                 username));
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        if (!result.next()) return null;
        var pk = result.getBytes("public_key");
        st.close();
        return pk;
    }
    public static boolean remove(String username) throws SQLException {
        var st = db.conn.createStatement();
        var result = st.executeUpdate(String.format("DELETE FROM UserIdentity WHERE username = '%s'",
                                 username));
        st.close();
        return result == 1;
    }
    public static LinkedList<Pair<String, byte[]>> get_all() throws SQLException {
        var result = new LinkedList<Pair<String, byte[]>>();
        var st = db.conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM UserIdentity");
        if (rs == null) throw new RuntimeException("Result set of query operation can't be null");
        while (rs.next()) {
            result.push(new Pair<>(rs.getString("username"), rs.getBytes("public_key")));
        }
        st.close();
        return result;
    }
}

