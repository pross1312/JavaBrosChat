package Utils;

import java.io.Serializable;

public class NotifyConnect implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public String token;
    public NotifyConnect(String token) {
        this.token = token;
    }
}
