package Server.Service;

import Utils.*;
import Server.Server;
import Server.DB.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class AdminService extends Service {
    ArrayList<UserInfo> list_users(String token) throws SQLException { // filter will be done on client side
        var acc = Server.accounts.get(token);
        if(acc.b == AccountType.User)
            throw new Error("Users are not allowed to get the user's list");
        ArrayList<UserInfo> user = UserInfoDb.list_users();
        return user;
    }

    ArrayList<UserInfo> list_active_users(String token, Date from, Date to){ // sort by name or created time on client side
        var acc = Server.accounts.get(token);
        if(acc.b == AccountType.User)
            throw new Error("Users are not allowed to get the user's list");
        ArrayList<UserInfo> user = UserInfoDb.list_users();
        return user;
    }
//    void add_user(String token, String username, String pass, Optional<UserInfo> user_info);
//    void update_user(String token, String username, UserInfo user_info);
//    void del_user(String token, String username);
//    void change_user_pass(String token, String username, String new_pass);
//    ArrayList<LoginRecordDb> get_login_log(String token, String username); // sort by time
//    ArrayList<LoginRecordDb> get_login_log(String token); // all users, sort by time
//    ArrayList<UserInfo> list_user_friends(String token, String username);
//    // group-operations
//    //
//    ArrayList<UserInfo> list_group_members(String token, String group_id); // filter out admins on client side (admin is also member)
//    // spam-operations
//    ArrayList<SpamReport> list_spam_reports(String token); // all reports, sort by username or reported time on client side
//    ArrayList<SpamReport> list_spam_reports(String token, Date from, Date to); // list report made between [from, to]
//    ArrayList<SpamReport> list_spam_filter_name(String token, String pattern); // list report filter by username
//    void lock_user(String token, String username); // confirm spam-report and lock user using this api
//    // new-registration
//    //
}
