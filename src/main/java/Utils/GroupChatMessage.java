package Utils;

import java.util.Date;
import java.util.Optional;

public class GroupChatMessage {
    public int id;
    public String group_id;
    public String sender; // username
    public Date sent_date;
    public String msg;
    public Optional<String> media_id;
    public GroupChatMessage(int id, String group, String sender, Date date, String msg, Optional<String> media_id) {
        this.id = id;
        this.group_id = group_id;
        this.sender = sender;
        this.sent_date = date;
        this.msg = msg;
        this.media_id = media_id;
    }
}

