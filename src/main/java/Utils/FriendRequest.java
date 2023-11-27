package Utils;

import java.io.Serializable;
import java.util.Date;

public class FriendRequest implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public String initiator;
    public Date date_sent;
    public String target;
    public FriendRequest(String ini, String target, Date date) {
        this.initiator = ini;
        this.target = target;
        this.date_sent = date;
    }
}

