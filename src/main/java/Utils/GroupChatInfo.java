package Utils;

import java.util.ArrayList;

import java.util.Date;

public class GroupChatInfo {
    public String id;
    public String name;
    public Date created_date;
    public GroupChatInfo(String id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.created_date = date;
    }
}

