package Server.DB;

import Utils.UserInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import Utils.LoginRecord;



public class LoginRecordDb {
    private static Database db = Server.Main.db;
    private static PreparedStatement insert_sm, last_login_query, query_active_sm, query_login_log_sm, query_login_log_all_sm;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO LoginRecord(username, ts) VALUES(?, ?);");
            last_login_query = db.conn.prepareStatement("SELECT * FROM LoginRecord WHERE username = ? ORDER BY ts DESC");
            query_active_sm = db.conn.prepareStatement("SELECT DISTINCT ui.* FROM LoginRecord lr, UserInfo ui WHERE ts >= ? AND ts <= ? AND lr.username = ui.username");
            query_login_log_sm = db.conn.prepareStatement("SELECT ts FROM LoginRecord WHERE username = ? ORDER BY ts DESC");
            query_login_log_all_sm = db.conn.prepareStatement("SELECT * FROM LoginRecord ORDER BY ts DESC");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static boolean add(String username) throws SQLException {
        insert_sm.setString(1, username);
        insert_sm.setTimestamp(2, new Timestamp(new Date().getTime()));
        return insert_sm.executeUpdate() == 1;
    }
    public static LoginRecord get_last_login(String username) throws SQLException {
        last_login_query.setString(1, username);
        ResultSet logins = last_login_query.executeQuery();
        if (logins == null) throw new SQLException("Can't query");
        if (logins.next()) {
            var record = new LoginRecord(logins.getString("username"), new java.util.Date(logins.getTimestamp("ts").getTime()));
            logins.close();
            return record;
        }
        return null;
    }
    public static ArrayList<UserInfo> get_list_active_users(Date fromDate, Date toDate) throws SQLException {
        query_active_sm.setTimestamp(1, new Timestamp(fromDate.getTime()));
        query_active_sm.setTimestamp(2, new Timestamp(toDate.getTime()));
        ArrayList<UserInfo> arr = new ArrayList<UserInfo>();
        var result = query_active_sm.executeQuery();
        if(result == null)
            throw new RuntimeException("Result set of query operation can't be null");
        while(result.next()){
            var info = UserInfoDb.parse_row(result);
            arr.add(info);
        }
        result.close();
        return arr;
    }
    public static ArrayList<Date> get_user_login_log(String username) throws SQLException{
        query_login_log_sm.setString(1, username);
        ArrayList<Date> arr = new ArrayList<>();
        var result = query_login_log_sm.executeQuery();
        if(result == null)
            throw new RuntimeException("Result set of query operation can't be null");
        while(result.next()){
            Date temp = result.getDate("ts");
            arr.add(temp);
        }
        result.close();
        return arr;
    }
    public static ArrayList<LoginRecord> get_all_login_log() throws SQLException{
        ArrayList<LoginRecord> arr = new ArrayList<>();
        var result = query_login_log_all_sm.executeQuery();
        if(result == null)
            throw new RuntimeException("Result set of query operation can't be null");
        while(result.next()){
            String username = result.getString("username");
            Date ts = result.getDate("ts");
            arr.add(new LoginRecord(username, ts));
        }
        result.close();
        return arr;
    }
}
