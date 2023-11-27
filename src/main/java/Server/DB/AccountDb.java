package Server.DB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Utils.AccountType;

public class AccountDb {
    public String username;
    public String hashed_pass;
    public AccountType type;
    public boolean is_locked;
    private static Database db = Server.Server.db;
    private static PreparedStatement query_sm, insert_sm;
    public AccountDb(String username, String hashed_pass, AccountType type, boolean is_locked) {
        this.username = username;
        this.hashed_pass = hashed_pass;
        this.type = type;
        this.is_locked = false;
        this.is_locked = is_locked;
    }
    static {
        try {
            query_sm = db.conn.prepareStatement("SELECT * FROM Account WHERE username = ?;");
            insert_sm = db.conn.prepareStatement("INSERT INTO Account VALUES(?, ?, ?, ?);");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static boolean add(AccountDb acc) throws SQLException {
        insert_sm.setString(1, acc.username);
        insert_sm.setString(2, acc.hashed_pass);
        insert_sm.setInt(3, acc.type.val);
        insert_sm.setBoolean(4, acc.is_locked);
        return insert_sm.executeUpdate() == 1;
    }
    public static AccountDb query(String username) throws SQLException {
        query_sm.setString(1, username);
        query_sm.execute();
        var result = query_sm.getResultSet();
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        if (!result.next()) return null;
        var hashed_pass = result.getString("password");
        var type = AccountType.from(result.getInt("type"));
        var is_locked = result.getBoolean("is_locked");
        result.close();
        return new AccountDb(username, hashed_pass, type, is_locked);
    }
}
