package Utils;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

// Headers {
//      "target": string of target, split by comma
//      "type": one of the following []
// }
public class Message implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    String sender;
    Date date;
    HashMap<String, String> headers;
    byte[] body;
    public Message(String sender) {
        this.sender = sender;
        this.date = new Date();
        this.headers = new HashMap<String, String>();
        this.body = null;
    }
    public void set(String key, String value) { this.headers.put(key, value); }
    public void del(String key) { this.headers.remove(key); }
    public void set_body(byte[] body) { this.body = body; }
    public String str() {
        var builder = new StringBuilder();
        builder.append(String.format("%s - %s\n", sender, date.toString()));
        for (var key : headers.keySet()) {
            builder.append(String.format("\t[%s]: %s\n", key, headers.get(key)));
        }
        return builder.toString();
    }
}
