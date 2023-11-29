package Utils;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

public class GroupChatMessage implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public int id;
    public String group_id;
    public String sender; // username
    public Date sent_date;
    public String msg;
    public String media_id; // this will be null if there is no media
    public GroupChatMessage(int id, String group, String sender, Date date, String msg, String media_id) {
        this.id = id;
        this.group_id = group_id;
        this.sender = sender;
        this.sent_date = date;
        this.msg = msg;
        this.media_id = media_id;
    }
}

