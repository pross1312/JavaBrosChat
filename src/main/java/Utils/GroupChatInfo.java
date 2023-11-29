package Utils;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Date;

public class GroupChatInfo implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public String id;
    public String name;
    public Date created_date;
    public GroupChatInfo(String id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.created_date = date;
    }
}

