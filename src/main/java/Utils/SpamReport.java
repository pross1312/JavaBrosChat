package Utils;

import java.io.Serializable;
import java.util.Date;

public class SpamReport implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
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
