package view.API;

import Utils.ChatMessage;
import Utils.GroupChatMemberInfo;
import Utils.LoginRecord;
import Utils.Pair;
import Utils.RegistrationRecord;
import Utils.Result;
import Utils.ResultError;
import Utils.ResultOk;
import Utils.UserInfo;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.Version2AdminDashBoard;

// This file provide some function to get Data from Backend. 
public class CallAPI {

    // Get First Login Time
    public static Date getFirstLoginTime(String token, String username) {
        Date first_Login = null;
        ArrayList<Date> login_Records = CallAPI.getLogInHistory(token, username);
        first_Login = findMinDate(login_Records);
        return first_Login;
    }

    public static ArrayList<GroupChatMemberInfo> list_group_members(String token, String group_id) {
        ArrayList<GroupChatMemberInfo> list_member = null;
        Result rs = Client.Client.api_c.invoke_api("AdminService", "list_group_members", token, group_id);
        if (rs instanceof ResultError err) {
            JOptionPane.showMessageDialog(null, err.msg());
        } else if (rs instanceof ResultOk ok) {
            list_member = (ArrayList<GroupChatMemberInfo>) ok.data();
            return list_member;
        }
        return list_member;
    }

    public static ArrayList<Date> getLogInHistory(String token, String username) {
        Result rs = Client.Client.api_c.invoke_api("AdminService", "get_login_log", token, username);
        if (rs instanceof ResultError err) {
            System.out.println(err.msg());
            return null;
        } else if (rs instanceof ResultOk ok) {
            ArrayList<Date> login_Records = (ArrayList< Date>) ok.data();
            return login_Records;
        }
        return null;
    }

    public static Date findMinDate(ArrayList<Date> dateList) {
        if (dateList == null || dateList.isEmpty()) {
            return null;
        }
        Date minDate = dateList.get(0);

        for (int i = 1; i < dateList.size(); i++) {
            Date currentDate = dateList.get(i);
            if (currentDate.before(minDate)) {
                minDate = currentDate;
            }
        }
        return minDate;
    }

    public static void lockUser(String token, String username) {
        String msg = null;
        Result rs = Client.Client.api_c.invoke_api("AdminService", "lock_user", token, username);
        if (rs instanceof ResultError err) {
            msg = err.msg();
        } else if (rs instanceof ResultOk ok) {
            msg = "Lock User Successfully";
        }
    }

    public static void unlockUser(String token, String username) {
        String msg = null;
        Result rs = Client.Client.api_c.invoke_api("AdminService", "unlock_user", token, username);
        if (rs instanceof ResultError err) {
            msg = err.msg();
        } else if (rs instanceof ResultOk ok) {
            msg = "Un Lock User Successfully";
        }
        JOptionPane.showMessageDialog(null, msg, "INFO", JOptionPane.INFORMATION_MESSAGE);
    }

    public static ArrayList<Integer> getUserEachMonthinYear(int year, String token, String typeUser) {
        ArrayList<Integer> list_amount_users = new ArrayList<>();
        String nameService;
        nameService = "list_active_users";
        if (typeUser.equals("active")) {
            nameService = "list_active_users";
        } else if (typeUser.equals("new")) {
            nameService = "list_registers";
        }

        for (int i = 1; i <= 12; ++i) {
            int end_of_Month = LocalDate.of(year, i, 1).lengthOfMonth();
            LocalDate from = LocalDate.of(year, i, 1);
            LocalDate to = LocalDate.of(year, i, end_of_Month);

            Date fromDateAsDate = Date.from(from.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Date toDateAsDate = Date.from(to.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Result rs = Client.Client.api_c.invoke_api("AdminService", nameService, token, fromDateAsDate, toDateAsDate);
            if (rs instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (rs instanceof ResultOk ok) {
                ArrayList<RegistrationRecord> record_list = (ArrayList<RegistrationRecord>) ok.data();
                if (record_list == null) {
                    JOptionPane.showMessageDialog(null, "Error while connecting to server");
                    return null;
                }
                int amount_of_User = record_list.size();
                list_amount_users.add(amount_of_User);
            }
        }

        return list_amount_users;
    }
    
    public static Result get_unread_friend(String token, String friend) {
        var result = Client.Client.api_c.invoke_api("FriendChatService", "get_unread_msg", token, friend);
        if (result instanceof ResultError err) {
            return err;
        } else if (result instanceof ResultOk ok) {
            return Result.ok((ArrayList<ChatMessage>) ok.data());
        } else throw new RuntimeException("Unexpected");
    }

    public static Result get_unread_group(String token, String g_id) {
        var result = Client.Client.api_c.invoke_api("GroupChatService", "get_unread_msg", token, g_id);
        if (result instanceof ResultError err) {
            return err;
        } else if (result instanceof ResultOk ok) {
            return Result.ok((ArrayList<ChatMessage>) ok.data());
        } else throw new RuntimeException("Unexpected");
    }

    public static ArrayList<LoginRecord> get_login_log(String token) {
        ArrayList<LoginRecord> login_records = null;
        Result rs = Client.Client.api_c.invoke_api("AdminService", "get_login_log", token);
        if (rs instanceof ResultError err) {
            String msg = err.msg();
            JOptionPane.showMessageDialog(null, msg, "INFO", JOptionPane.INFORMATION_MESSAGE);
        } else if (rs instanceof ResultOk ok) {
            login_records = (ArrayList<LoginRecord>) ok.data();
        }
        return login_records;
    }

    public static ArrayList<UserInfo> list_active_users(String token, Date from, Date to) {
        Result rs = Client.Client.api_c.invoke_api("AdminService", "list_active_users", token, from, to);
        if (rs instanceof ResultError err) {
            JOptionPane.showMessageDialog(null, err.msg());
            return null;
        } else if (rs instanceof ResultOk ok) {
            ArrayList<UserInfo> record_list = (ArrayList<UserInfo>) ok.data();
            if (record_list == null) {
                JOptionPane.showMessageDialog(null, "Error while connecting to server");
                return null;
            }
            return record_list;
        }
        return null;
    }

    public static ArrayList<Pair<UserInfo, Boolean>> get_list_users(String token) {
        String msg;
        ArrayList<Pair<UserInfo, Boolean>> user_list = null;
        Result rs = Client.Client.api_c.invoke_api("AdminService", "list_users", Client.Client.token);
        if (rs instanceof ResultError err) {
            msg = err.msg();
            return user_list;
        } else if (rs instanceof ResultOk ok) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            user_list = (ArrayList<Pair<UserInfo, Boolean>>) ok.data();
            return user_list;
        }
        return user_list;
    }
}
