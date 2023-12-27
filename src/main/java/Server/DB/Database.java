package Server.DB;
import com.microsoft.sqlserver.jdbc.*;
import java.util.Properties;
import java.sql.*;

public class Database {
    private static Properties props;
    private final static String SQL_URL = "jdbc:sqlserver://127.0.0.1";
    private SQLServerDriver driver;
    Connection conn;
    static {
        props = new Properties();
        props.setProperty("encrypt", "true");
        props.setProperty("trustServerCertificate", "true");
        props.setProperty("user", "huy");
        props.setProperty("password", "123");
        props.setProperty("database", "Java");
        props.setProperty("loginTimeout", "5");
    }
    public Database() throws SQLException {
        if (!SQLServerDriver.isRegistered()) {
            SQLServerDriver.register();
        }
        driver = new SQLServerDriver();
        conn = driver.connect(Database.SQL_URL, Database.props);
    }
    public boolean is_connected() {
        try {
            return conn.isValid(200);
        } catch (SQLException ignored) {}
        return false;
    }
    public void set_auto_commit(boolean val) throws SQLException {
        conn.setAutoCommit(val);
    }
    public void commit() throws SQLException {
        conn.commit();
    }
    public boolean reconnect() {
        if (is_connected()) return true;
        try {
            conn = driver.connect(Database.SQL_URL, Database.props);
        } catch (SQLServerException e) {
            System.out.println(e);
        }
        return  false;
    }
}
