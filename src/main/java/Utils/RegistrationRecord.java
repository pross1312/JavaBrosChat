package Utils;
import java.io.Serializable;
import java.util.Date;

public class RegistrationRecord implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public String username;
    public Date ts;
    public RegistrationRecord(String username, Date date) {
        this.username = username;
        this.ts = date;
    }
}
