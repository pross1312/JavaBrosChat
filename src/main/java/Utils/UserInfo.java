package Utils;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public enum Gender {
        Male(0), Female(1);
        public int val;
        public static Gender from(int val) {
            if (val == 1) return Gender.Female;
            else if (val == 0) return Gender.Male;
            else throw new RuntimeException(String.format("Can't regconize gender value %d", val));
        }
        private Gender(int v) { val = v; }
    }
    public String username, fullname, address, email;
    public Date birthdate;
    public Gender gender;
    public UserInfo(String username, String email) {
        this.username = username;
        this.fullname = "";
        this.address = "";
        this.email = email;
        this.birthdate = new Date();
        this.gender = Gender.Male;
    }
    public UserInfo(String username, String fullname, String addr, String email, Date birth, Gender gender) {
        this.username = username;
        this.fullname = fullname;
        this.address = addr;
        this.email = email;
        this.birthdate = birth;
        this.gender = gender;
    }
}
