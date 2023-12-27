package Utils;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public int id;
    public String target;
    public String sender; // username
    public Date sent_date;
    public byte[] cipher_msg;
    public ChatMessage(int id, String target, String sender, Date date, byte[] cipher_msg) {
        this.id = id;
        this.target = target;
        this.sender = sender;
        this.sent_date = date;
        this.cipher_msg = cipher_msg;
    }
}

