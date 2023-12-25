package Utils.Notify;

public class DelGroupMember extends Notify {
    public String group_id;
    public String member;
    public DelGroupMember(String group_id, String member) {
        this.group_id = group_id;
        this.member = member;
    }
}
