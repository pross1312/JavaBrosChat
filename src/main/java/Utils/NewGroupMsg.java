package Utils;

public class NewGroupMsg extends Notify {
    public String group_id;
    public int count;
    public NewGroupMsg(String group, int count) {
        this.group_id = group;
        this.count = count;
    }
}
