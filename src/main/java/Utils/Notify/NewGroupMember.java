package Utils.Notify;

public class NewGroupMember extends Notify {
    public String group_id;
    public String member;
    public NewGroupMember(String group_id, String member) {
        this.group_id = group_id;
        this.member = member;
    }
}
