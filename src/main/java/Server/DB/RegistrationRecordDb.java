package Server.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class RegistrationRecordDb {
    private static Database db = Server.Server.db;
    private static PreparedStatement insert_sm;
    public String username;
    public Date ts;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO RegistrationRecord(username, ts) VALUES(?, ?);");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    private RegistrationRecordDb(String username, java.util.Date ts) {
        this.username = username;
        this.ts = ts;
    }
    public static boolean add(String username) throws SQLException {
        insert_sm.setString(1, username);
        insert_sm.setTimestamp(2, new Timestamp(new Date().getTime()));;
        return insert_sm.executeUpdate() == 1;
    }
}

