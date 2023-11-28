package Utils;
import java.util.Date;

public class RegistrationRecord {
    public String username;
    public Date ts;
    public RegistrationRecord(String username, Date date) {
        this.username = username;
        this.ts = date;
    }
}
