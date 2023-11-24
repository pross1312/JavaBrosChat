package Server.DB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Utils.Account;
import Utils.Account.AccountType;

public class AccountDb {
    private static Database db = Server.Server.db;
    private static PreparedStatement query_sm, update_sm, insert_sm;
    static {
        try {
            query_sm = db.conn.prepareStatement("SELECT * FROM Account WHERE username = ?;");
            update_sm = db.conn.prepareStatement("UPDATE * Account SET password = ?, type = ? WHERE username = ?;");
            insert_sm = db.conn.prepareStatement("INSERT INTO Account VALUES(?, ?, ?, ?);");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static boolean add(Account acc) throws SQLException {
        insert_sm.setString(1, acc.username);
        insert_sm.setString(2, acc.password);
        insert_sm.setInt(3, acc.type.value);
        insert_sm.setBoolean(4, acc.is_locked);
        return insert_sm.executeUpdate() == 1;
    }
    public static boolean update_pass(Account acc) throws SQLException {
        update_sm.setString(1, acc.password);
        update_sm.setInt(2, acc.type.value);
        if (update_sm.executeUpdate() == 0) return false;
        return true;
    }
    public static Account query(String username) throws SQLException {
        query_sm.setString(1, username);
        query_sm.execute();
        var result = query_sm.getResultSet();
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        if (!result.next()) return null;
        var pass = result.getString("password");
        var type_int = result.getInt("type");
        var is_locked = result.getBoolean("is_locked");
        AccountType type = null;
        if (type_int == 1) type = AccountType.Admin;
        else if (type_int == 0) type = AccountType.User;
        else {
            System.out.printf("[ERROR] '%s' has invalid type %d\n", username, type_int);
            throw new Error("Internal server error.");
        }
        return new Account(username, pass, type, is_locked);
    }
}
