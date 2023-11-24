package Utils;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {
    public enum AccountType {
        Admin(1), User(0);
        public int value;
        private AccountType(int v) {
            value = v;
        }
    }
    private static final long serialVersionUID = 1L; // version for compatibility
    public String username;
    public String password;
    public AccountType type;
    public boolean is_locked;
    public Account(String username, String password, AccountType type, boolean is_locked) {
        this(username, password, type);
        this.is_locked = is_locked;
    }
    public Account(String username, String password, AccountType type) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.is_locked = false;
    }
}
