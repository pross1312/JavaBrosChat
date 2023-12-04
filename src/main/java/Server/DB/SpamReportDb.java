package Server.DB;

import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Utils.SpamReport;

public class SpamReportDb {
    private static Database db = Server.Server.db;
    private static PreparedStatement insert_sm, query_all_sm, query_sm_from_to, query_username;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO SpamReport(reporter, target, reason, date) VALUES(?, ?, ?, ?);");
            query_all_sm = db.conn.prepareStatement("SELECT * FROM SpamReport");
            query_sm_from_to = db.conn.prepareStatement("SELECT * FROM SpamReport WHERE date >= ? AND date <= ?");
            query_username = db.conn.prepareStatement("SELECT * FROM SpamReport WHERE target = ?");
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
    public static ArrayList<SpamReport> list_all_spam_reports() throws SQLException{
        ArrayList<SpamReport> arr = new ArrayList<>();
        var result = query_all_sm.executeQuery();
        if(result == null)
            throw new RuntimeException("Result set of query operation can't be null");
        while(result.next()){
            String reporter = result.getString("reporter");
            String target = result.getString("target");
            String reason = result.getString("reason");
            Date date = result.getDate("date");
            arr.add(new SpamReport(reporter, target, reason, date));
        }
        result.close();
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
            String reporter = result.getString("reporter");
            String target = result.getString("target");
            String reason = result.getString("reason");
            Date date = result.getDate("date");
            arr.add(new SpamReport(reporter, target, reason, date));
        }
        result.close();
        return arr;
    }
    public static ArrayList<SpamReport> list_spam_username(String username) throws SQLException {
        ArrayList<SpamReport> arr = new ArrayList<>();
        query_username.setString(1, username);
        var result = query_username.executeQuery();
        if(result == null)
            throw new RuntimeException("Result set of query operation can't be null");
        while(result.next()){
            String reporter = result.getString("reporter");
            String target = result.getString("target");
            String reason = result.getString("reason");
            Date date = result.getDate("date");
            arr.add(new SpamReport(reporter, target, reason, date));
        }
        result.close();
        return arr;
    }
}
