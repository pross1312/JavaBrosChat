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
        if (acc == null)
            throw new Error("Can't execute list_users api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to get the user's list");
        ArrayList<UserInfo> user = UserInfoDb.list_users();
        user.trimToSize();
        return user;
    }

    ArrayList<UserInfo> list_active_users(String token, Date from, Date to) throws SQLException{ // sort by name or created time on client side
        var acc = Server.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute list_active_users api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to get the user's active list");
        ArrayList<UserInfo> user = LoginRecordDb.get_list_active_users(from, to);
        user.trimToSize();
        return user;
    }
    void add_user(String token, String username, String pass, UserInfo user_info) throws SQLException{
        var acc = Server.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute add_user api, token not found");
        if(acc.b == AccountType.User)
            throw new Error("Only admin is allowed to insert a user");
        AccountService as = new AccountService();
        as.register(username, pass, user_info);
    }
    void update_user(String token, String username, UserInfo user_info) throws SQLException{
        var acc = Server.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute update_user api, token not found");
        if(acc.b == AccountType.User)
            throw new Error("Only admin is allowed to update a user");
        if(UserInfoDb.query(username) == null)
            throw new Error("Username does not exist to be updated");
        if (username.compareTo(user_info.username) != 0)
            throw new Error("Can't update user information with different username and user_info.username");
        UserInfoDb.update(user_info, username);
    }
    void del_user(String token, String username) throws SQLException{
        var acc = Server.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute del_user api, token not found");
        if(acc.b == AccountType.User)
            throw new Error("Only admin is allowed to delete a user");
        if(UserInfoDb.query(username) == null)
            throw new Error("Username does not exist to be deleted");
        UserInfoDb.delete(username);
        AccountDb.delete(username);
    }
    void change_user_pass(String token, String username, String new_pass) throws SQLException{
        var acc = Server.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute change_user_pass api, token not found");
        if(acc.b == AccountType.User)
            throw new Error("Only admin is allowed to change a user's password");
        if(UserInfoDb.query(username) == null)
            throw new Error("Username does not exist to change password");
        AccountDb.change_pass(username, new_pass);
    }
    ArrayList<Date> get_login_log(String token, String username) throws SQLException{ // sort by time
        var acc = Server.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute get_login_log api, token not found");
        if(acc.b == AccountType.User)
            throw new Error("Only admin is allowed to get a user's login record");
        return LoginRecordDb.get_user_login_log(username);
    }
    ArrayList<LoginRecord> get_login_log(String token) throws SQLException{ // all users, sort by time
        var acc = Server.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute get_login_log api, token not found");
        if(acc.b == AccountType.User)
            throw new Error("Only admin is allowed to get the login record of all users");
        return LoginRecordDb.get_all_login_log();
    }
    ArrayList<UserInfo> list_user_friends(String token, String username){

    }
//    // group-operations
//    ArrayList<GroupChatInfo> list_groups(String token, String pattern); // sort and filter on client side
//    ArrayList<GroupChatMemberInfo> list_group_members(String token, String group_id); // filter out admins on client side (admin is also member)
//    // spam-operations
//    ArrayList<SpamReport> list_spam_reports(String token) // all reports, sort by username or reported time on client side
//    ArrayList<SpamReport> list_spam_reports(String token, Date from, Date to) // list report made between [from, to]
//    ArrayList<SpamReport> list_spam_filter_name(String token, String pattern) // list report filter by username
//    void lock_user(String token, String username) // confirm spam-report and lock user using this api
//    // new-registration
//    ArrayList<RegistrationRecord> list_registers(String token, Date from, Date to) // list user-registrations made between [from, to] order by name or time on client side
}
