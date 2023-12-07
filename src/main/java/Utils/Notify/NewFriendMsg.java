package Utils.Notify;

public class NewFriendMsg extends Notify {
    public String sender;
    public int count;
    public NewFriendMsg(String sender, int count) {
        this.sender = sender;
        this.count = count;
    }
}

