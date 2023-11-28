package Utils;

import java.util.Date;

public class SpamReport {
    public String reporter; // username
    public String target;
    public String reason;
    public Date date;
    public SpamReport(String usr, String target, String reason, Date date) {
        this.reporter = usr;
        this.target = target;
        this.reason = reason;
        this.date = date;
    }
}
