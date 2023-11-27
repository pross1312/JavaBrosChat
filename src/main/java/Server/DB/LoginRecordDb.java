package Server.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class LoginRecordDb {
    private static Database db = Server.Server.db;
    private static PreparedStatement insert_sm, last_login_query;
    public String username;
    public Date ts;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO LoginRecord(username, ts) VALUES(?, ?);");
            last_login_query = db.conn.prepareStatement("SELECT * FROM LoginRecord WHERE username = ? ORDER BY ts DESC");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    private LoginRecordDb(String username, java.util.Date ts) {
        this.username = username;
        this.ts = ts;
    }
    public static boolean add(String username) throws SQLException {
        insert_sm.setString(1, username);
        insert_sm.setTimestamp(2, new Timestamp(new Date().getTime()));;
        return insert_sm.executeUpdate() == 1;
    }
    public static LoginRecordDb get_last_login(String username) throws SQLException {
        last_login_query.setString(1, username);
        ResultSet logins = last_login_query.executeQuery();
        if (logins == null) throw new SQLException("Can't query");
        if (logins.next()) {
            var record = new LoginRecordDb(logins.getString("username"), new java.util.Date(logins.getTimestamp("ts").getTime()));
            logins.close();
            return record;
        }
        return null;
    }
}
