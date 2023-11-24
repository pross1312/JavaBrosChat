package Utils;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public enum Gender {
        Male(0), Female(1);
        public int val;
        private Gender(int v) { val = v; }
    }
    public String username, fullname, address, email;
    public Date birthdate;
    public Gender gender;
    public UserInfo(String username, String fullname, String addr, String email, Date birth, Gender gender) {
        this.username = username;
        this.fullname = fullname;
        this.address = addr;
        this.email = email;
        this.birthdate = birth;
        this.gender = gender;
    }
}
