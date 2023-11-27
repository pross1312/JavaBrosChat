package Server.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Utils.UserInfo;
import Utils.UserInfo.Gender;

public class UserInfoDb {
    private static Database db = Server.Server.db;
    private static PreparedStatement insert_sm, query_sm;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO UserInfo(username, fullname, email, address, birthdate, gender) VALUES(?, ?, ?, ?, ?, ?)");
            query_sm = db.conn.prepareStatement("SELECT * FROM UserInfo WHERE username = ?");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static UserInfo parse_row(ResultSet result) throws SQLException { // parse a row
        var username = result.getString("username");
        var fullname = result.getString("fullname");
        var address = result.getString("address");
        var email = result.getString("email");
        var birthdate = result.getDate("birthdate");
        var gender = Gender.from(result.getInt("gender"));
        return new UserInfo(username, fullname, address, email, birthdate, gender);
    }
    public static UserInfo query(String username) throws SQLException {
        query_sm.setString(1, username);
        var result = query_sm.executeQuery();
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        if (!result.next()) return null;
        var info = parse_row(result);
        result.close();
        return info;
    }
    public static boolean add(UserInfo info) throws SQLException {
        insert_sm.setString(1, info.username);
        insert_sm.setString(2, info.fullname);
        insert_sm.setString(3, info.email);
        insert_sm.setString(4, info.address);
        insert_sm.setDate(5, new java.sql.Date(info.birthdate.getTime()));
        insert_sm.setInt(6, info.gender.val);
        return insert_sm.executeUpdate() == 1;
    }
}
