package Utils;

public class NewMsgNotify extends Notify {
    public String group_id;
    public int count;
    public NewMsgNotify(String group, int count) {
        this.group_id = group;
        this.count = count;
    }
}
