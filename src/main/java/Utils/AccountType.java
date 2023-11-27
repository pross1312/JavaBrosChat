package Utils;
public enum AccountType {
    Admin(1), User(0);
    public int val;
    public static AccountType from(int val) {
        if (val == 1) return AccountType.Admin;
        else if (val == 0) return AccountType.User;
        throw new RuntimeException(String.format("Unregconizable account type %d", val));
    }
    private AccountType(int val) {
        this.val = val;
    }
}
