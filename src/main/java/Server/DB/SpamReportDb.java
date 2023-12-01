package Server.DB;

import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Utils.SpamReport;

public class SpamReportDb {
    private static Database db = Server.Main.db;
    private static PreparedStatement insert_sm;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO SpamReport(reporter, target, reason, date) VALUES(?, ?, ?, ?);");
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
}
