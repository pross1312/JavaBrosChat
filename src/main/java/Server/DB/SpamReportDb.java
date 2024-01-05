package Server.DB;

import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Utils.SpamReport;

public class SpamReportDb {
    private static Database db = Server.Main.db;
    private static PreparedStatement insert_sm, query_sm_from_to;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO SpamReport(reporter, target, reason, date) VALUES(?, ?, ?, ?);");
            query_sm_from_to = db.conn.prepareStatement("SELECT * FROM SpamReport WHERE date >= ? AND date <= ?");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static boolean add(SpamReport report) throws SQLException {
        insert_sm.setString(1, report.reporter);
        insert_sm.setString(2, report.target);
        insert_sm.setString(3, report.reason);
        insert_sm.setTimestamp(4, new Timestamp(report.date.getTime()));
        return insert_sm.executeUpdate() == 1;
    }
    public static SpamReport parse_row(ResultSet result) throws SQLException {
        String reporter = result.getString("reporter");
        String target = result.getString("target");
        String reason = result.getString("reason");
        Date date = result.getDate("date");
        return new SpamReport(reporter, target, reason, date);
    }
    public static ArrayList<SpamReport> list_all_spam_reports() throws SQLException{
        ArrayList<SpamReport> arr = new ArrayList<>();
        try (var st = db.conn.createStatement()) {
            var result = st.executeQuery("SELECT * FROM SpamReport");
            if (result == null) {
                throw new RuntimeException("Result set of query operation can't be null");
            }
            while (result.next()) {
                arr.add(parse_row(result));
            }
        }
        return arr;
    }
    public static ArrayList<SpamReport> list_spam_from_to(Date from, Date to) throws SQLException {
        ArrayList<SpamReport> arr = new ArrayList<>();
        query_sm_from_to.setTimestamp(1, new Timestamp(from.getTime()));
        query_sm_from_to.setTimestamp(2, new Timestamp(to.getTime()));
        var result = query_sm_from_to.executeQuery();
        if(result == null)
            throw new RuntimeException("Result set of query operation can't be null");
        while(result.next()){
            arr.add(parse_row(result));
        }
        result.close();
        return arr;
    }
    public static ArrayList<SpamReport> list_spam_username(String username) throws SQLException {
        ArrayList<SpamReport> arr = new ArrayList<>();
        try (var st = db.conn.createStatement()) {
            var result = st.executeQuery(String.format(
                    "SELECT * FROM SpamReport WHERE target = '%s'", username));
            if (result == null) {
                throw new RuntimeException("Result set of query operation can't be null");
            }
            while (result.next()) {
                arr.add(parse_row(result));
            }
            result.close();
        }
        return arr;
    }
    public static boolean remove_all(String username) throws SQLException {
        try (var st = db.conn.createStatement()) {
            return st.executeUpdate(String.format(
                    "DELETE FROM SpamReport WHERE reporter = '%s' OR target = '%s'",
                    username, username)) >= 1;
        }
    }
}
