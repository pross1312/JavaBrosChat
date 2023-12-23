package Server.DB;

import Utils.RegistrationRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class RegistrationRecordDb {
    private static Database db = Server.Main.db;
    private static PreparedStatement insert_sm, query_sm;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO RegistrationRecord(username, ts) VALUES(?, ?);");
            query_sm = db.conn.prepareStatement("SELECT * FROM RegistrationRecord WHERE ts >= ? AND ts <= ?");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static boolean add(String username) throws SQLException {
        insert_sm.setString(1, username);
        insert_sm.setTimestamp(2, new Timestamp(new Date().getTime()));;
        return insert_sm.executeUpdate() == 1;
    }
    public static boolean delete(String username) throws SQLException {
        Statement st = db.conn.createStatement();
        var result = st.executeUpdate(String.format("DELETE FROM RegistrationRecord WHERE username = '%s'", username)) == 1;
        st.close();
        return result;
    }
    public static ArrayList<RegistrationRecord> list_registration_record(Date from, Date to) throws SQLException {
        ArrayList<RegistrationRecord> arr = new ArrayList<>();
        query_sm.setTimestamp(1, new Timestamp(from.getTime()));
        query_sm.setTimestamp(2, new Timestamp(to.getTime()));
        var result = query_sm.executeQuery();
        if(result == null)
            throw new RuntimeException("Result set of query operation can't be null");
        while(result.next()){
            String username = result.getString("username");
            Date date = result.getDate("ts");
            arr.add(new RegistrationRecord(username, date));
        }
        result.close();
        return arr;
    }
}

