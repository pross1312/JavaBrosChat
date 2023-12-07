package Server.Service;

import Utils.*;
import Server.DB.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class AdminService extends Service {
    ArrayList<UserInfo> list_users(String token) throws SQLException { // filter will be done on client side
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute list_users api, token not found");
        if(acc.b == AccountType.User)
            throw new Error("Only admin is allowed to get the user's list");
        ArrayList<UserInfo> user = UserInfoDb.list_users();
        user.trimToSize();
        return user;
    }

    ArrayList<UserInfo> list_active_users(String token, Date from, Date to) throws SQLException{ // sort by name or created time on client side
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute list_users api, token not found");
        if(acc.b == AccountType.User)
            throw new Error("Only admin is allowed to get the user's active list");
        ArrayList<UserInfo> user = LoginRecordDb.get_list_active_users(from, to);
        user.trimToSize();
        return user;
    }
    void add_user(String token, String username, String pass, UserInfo user_info) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if(acc.a == null)
            throw new Error("Can't execute add_user api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to insert a user");
        AccountService as = new AccountService();
        as.register(username, pass, user_info);
    }
    void update_user(String token, String username, UserInfo user_info) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if(acc.a == null)
            throw new Error("Can't execute update_user api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to update a user");
        if (UserInfoDb.query(username) == null)
            throw new Error("Username does not exist to be updated");
        if (username.compareTo(user_info.username) != 0)
            throw new Error("Can't update user information with different username and user_info.username");
        UserInfoDb.update(user_info, username);
    }
    void del_user(String token, String username) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if(acc.a == null)
            throw new Error("Can't execute del_user api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to delete a user");
        if (UserInfoDb.query(username) == null)
            throw new Error("Username does not exist to be deleted");
        UserInfoDb.delete(username);
        AccountDb.delete(username);
    }
    void change_user_pass(String token, String username, String new_pass) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if(acc.a == null)
            throw new Error("Can't execute change_user_pass api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to change a user's password");
        if (UserInfoDb.query(username) == null)
            throw new Error("Username does not exist to change password");
        AccountDb.change_pass(username, new_pass);
    }
    ArrayList<Date> get_login_log(String token, String username) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute get_login_log api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to get a user's login record");
        var result = LoginRecordDb.get_user_login_log(username);
        result.trimToSize();
        return result;
    }
    ArrayList<LoginRecord> get_login_log(String token) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute get_login_log api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to get the login record of all users");
        var result = LoginRecordDb.get_all_login_log();
        result.trimToSize();
        return result;
    }
    ArrayList<UserInfo> list_user_friends(String token, String username) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute list_user_friends api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to get all the user's friends");
        var result = UserFriendDb.list_friends_info(username);
        result.trimToSize();
        return result;
    }
    // group-operations
    ArrayList<GroupChatInfo> list_groups(String token) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute list_groups api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to get all the group chat room information");
        var result = GroupChatDb.list_all_group_chats();
        result.trimToSize();
        return result;
    }
    ArrayList<GroupChatMemberInfo> list_group_members(String token, String group_id) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute list_group_members api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to get all the group chat room member's information");
        var result = GroupChatMemberDb.list_all_members(group_id);
        result.trimToSize();
        return result;
    }
    // spam-operations
    ArrayList<SpamReport> list_spam_reports(String token) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute list_spam_reports api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to list all the spam reports");
        var result = SpamReportDb.list_all_spam_reports();
        result.trimToSize();
        return result;
    }
    ArrayList<SpamReport> list_spam_reports(String token, Date from, Date to) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute list_spam_reports api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to list all the spam reports");
        return SpamReportDb.list_spam_from_to(from, to);
    }
    ArrayList<SpamReport> list_spam_filter_name(String token, String username) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute list_spam_filter_name api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to list all the spam reports of a specific user");
        var result = SpamReportDb.list_spam_username(username);
        result.trimToSize();
        return result;
    }
    void lock_user(String token, String username) throws SQLException{
        var acc = Server.Main.accounts.get(token);
        if(acc == null)
            throw new Error("Can't execute list_spam_filter_name api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to list all the spam reports of a specific user");
        AccountDb.lock_user(username);
    }
    // new-registration
    ArrayList<RegistrationRecord> list_registers(String token, Date from, Date to) throws SQLException {
        var acc = Server.Main.accounts.get(token);
        if (acc == null)
            throw new Error("Can't execute list_spam_filter_name api, token not found");
        if (acc.b == AccountType.User)
            throw new Error("Only admin is allowed to list all the spam reports of a specific user");
        var result = RegistrationRecordDb.list_registration_record(from, to);
        result.trimToSize();
        return result;
    }
}
