package Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;


import Utils.AccountType;
import Utils.Connection;
import Utils.GroupChatInfo;
import Utils.ChatMessage;
import Utils.Pair;
import Utils.ResultError;
import Utils.ResultOk;
import Utils.UserInfo;

public class ClientA {
    static NotifyClient notify;
    static ApiClient api_c;
    static final int API_PORT = 13123;
    static final int NOTIFY_PORT = 13122;
    static String authen_token;
    static String cur_group_id;
    static ArrayList<GroupChatInfo> groups;
    static {
        try {
            Connection.init_ssl("/.ssl/root/ca.jks", "changeit");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println(args[0]);
        api_c = new ApiClient("localhost", API_PORT);
        var result = api_c.invoke_api("AccountService", "register", args[0], "123",
                new UserInfo(args[0], "tuong321", "1j2oi", "123tuong@gmail.com", new java.util.Date(), UserInfo.Gender.Female));
        if (result instanceof ResultError err) {
            System.out.println(err.msg());
        }
        result = api_c.invoke_api("AccountService", "login", args[0], "123");
        if (result instanceof ResultError err) {
            System.out.println(err.msg());
        } else if (result instanceof ResultOk ok) {
            authen_token = ((Pair<String, AccountType>)ok.data()).a;
            notify = new NotifyClient(authen_token, "localhost", NOTIFY_PORT);
            System.out.println(authen_token);
            shell();
        }
    }
    public static void shell() throws Exception {
        var scanner = new Scanner(System.in);
        while (true) {
            var line = scanner.nextLine().trim();
            var tokens = Arrays.stream(line.split(" ")).map(x -> x.trim()).toList();
            if (tokens.get(0).compareTo("/quit") == 0) {
                notify.close();
                break;
            } else if (tokens.get(0).compareTo("/listfriend") == 0) {
                var result = api_c.invoke_api("UserManagementService", "list_friends", authen_token);
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                } else if (result instanceof ResultOk ok) {
                    var friends = (ArrayList<Pair<UserInfo, Boolean>>)ok.data();
                    for (var x : friends) {
                        System.out.printf("%s : %s\n", x.a.username, x.b);
                    }
                }
            } else if (tokens.get(0).compareTo("/friend") == 0) {
                var result = api_c.invoke_api("UserManagementService", "add_friend", authen_token, tokens.get(1));
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                }
            } else if (tokens.get(0).compareTo("/create") == 0) {
                var result = api_c.invoke_api("GroupChatService", "create_group", authen_token,
                                              tokens.get(1), new ArrayList<>(List.of(tokens.get(2))));
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                } else if (result instanceof ResultOk ok) {
                }
            } else if (tokens.get(0).compareTo("/use") == 0) {
                cur_group_id = groups.get(Integer.parseInt(tokens.get(1))).id;
            } else if (tokens.get(0).compareTo("/newmsg") == 0) {
                var result = api_c.invoke_api("GroupChatService", "get_unread_msg", authen_token, cur_group_id);
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                } else if (result instanceof ResultOk ok) {
                    var msgs = (ArrayList<GroupChatMessage>)ok.data();
                    for (int i = 0; i < msgs.size(); i++) {
                        System.out.println(msgs.get(i).msg);
                    }
                }
            } else if (tokens.get(0).compareTo("/send") == 0) {
                System.out.print("MSG: ");
                var text = scanner.nextLine();
                var result = api_c.invoke_api("GroupChatService", "send_msg_to_group", authen_token, text, cur_group_id);
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                }
            } else if (tokens.get(0).compareTo("/list") == 0) {
                var result = api_c.invoke_api("GroupChatService", "list_groups", authen_token);
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                } else if (result instanceof ResultOk ok) {
                    groups = (ArrayList<GroupChatInfo>)ok.data();
                    for (int i = 0; i < groups.size(); i++) {
                        System.out.printf("%3s: %s\n", i, groups.get(i).name);
                    }
                }
            }
        }
    }
}
