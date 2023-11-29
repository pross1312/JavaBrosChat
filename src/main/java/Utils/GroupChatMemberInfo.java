package Utils;

import java.util.Date;

public class GroupChatMemberInfo {
    public String group_id;
    public String username;
    public Date joined_date;
    public boolean is_admin;
    public GroupChatMemberInfo(String id, String username, Date date, boolean is_admin) {
        this.group_id = id;
        this.username = username;
        this.joined_date = date;
        this.is_admin = is_admin;
    }
}

