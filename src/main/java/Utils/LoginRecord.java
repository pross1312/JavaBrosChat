package Utils;

import java.io.Serializable;
import java.util.Date;

public class LoginRecord implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public String username;
    public Date ts;
    public LoginRecord(String username, Date ts) {
        this.username = username;
        this.ts = ts;
    }
}
