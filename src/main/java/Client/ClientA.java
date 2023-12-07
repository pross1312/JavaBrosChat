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
    static final int SERVER_PORT = 13123;
    static String authen_token;
    static String cur_group_id;
    static ArrayList<GroupChatInfo> groups;
    static String username;
    static String password;
    static MessageClient msg_client;
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
        api_c = new ApiClient("localhost", SERVER_PORT);
        var result = api_c.invoke_api("AccountService", "register", args[0], "123",
                new UserInfo(args[0], "tuong321", "1j2oi", "123tuong@gmail.com", new java.util.Date(), UserInfo.Gender.Female));
        if (result instanceof ResultError err) {
            System.out.println(err.msg());
        }
        username = args[0];
        password = "123";
        result = api_c.invoke_api("AccountService", "login", username, password);
        if (result instanceof ResultError err) {
            System.out.println(err.msg());
        } else if (result instanceof ResultOk ok) {
            authen_token = ((Pair<String, AccountType>)ok.data()).a;
            notify = new NotifyClient(authen_token, "localhost", SERVER_PORT);
            msg_client = new MessageClient(username);
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
            } else if (tokens.get(0).compareTo("/login") == 0) {
                var result = api_c.invoke_api("AccountService", "login", username, password);
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                } else if (result instanceof ResultOk ok) {
                    authen_token = ((Pair<String, AccountType>)ok.data()).a;
                    System.out.println(authen_token);
                    if (notify != null) notify.close();
                    notify = new NotifyClient(authen_token, "localhost", SERVER_PORT);
                }
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
            } else if (tokens.get(0).compareTo("/unfriend") == 0) {
                var result = api_c.invoke_api("UserManagementService", "unfriend", authen_token, tokens.get(1));
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                }
            } else if (tokens.get(0).compareTo("/create") == 0) {
                var users = Arrays.stream(scanner.nextLine().split(" ")).map(x -> x.trim()).toList();
                var result = api_c.invoke_api("GroupChatService", "create", authen_token,
                                              tokens.get(1), new ArrayList<>(users));
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
                    var msgs = (ArrayList<ChatMessage>)ok.data();
                    for (int i = 0; i < msgs.size(); i++) {
                        var sender = msgs.get(i).sender;
                        var msg = msg_client.decrypt(sender, msgs.get(i).cipher_msg);
                        if (msg.isEmpty()) System.out.println("Can't decrypt message from " + sender);
                        else System.out.printf("[%s] %s\n", sender, msg.get());
                    }
                }
            } else if (tokens.get(0).compareTo("/delete") == 0) {
                var result = api_c.invoke_api("GroupChatService", "remove_member", authen_token, cur_group_id, tokens.get(1));
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                }
            } else if (tokens.get(0).compareTo("/sendfriend") == 0) {
                System.out.print("MSG: ");
                var text = scanner.nextLine();
                if (!msg_client.send_msg(tokens.get(1), text)) {
                    System.out.println("[ERROR] Could not send message to " + tokens.get(1));
                }
            } else if (tokens.get(0).compareTo("/send") == 0) {
                System.out.print("MSG: ");
                var text = scanner.nextLine();
                if (!msg_client.send_group_msg(cur_group_id, text)) {
                    System.out.println("[ERROR] Could not send message to group");
                }
            } else if (tokens.get(0).compareTo("/rename") == 0) {
                var result = api_c.invoke_api("GroupChatService", "rename",
                        authen_token, cur_group_id, tokens.get(1));
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                }
            } else if (tokens.get(0).compareTo("/set_admin") == 0) {
                var result = api_c.invoke_api("GroupChatService", "set_admin",
                        authen_token, cur_group_id, tokens.get(1), Boolean.TRUE);
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                }
            } else if (tokens.get(0).compareTo("/add_member") == 0) {
                var result = api_c.invoke_api("GroupChatService", "add_member", authen_token, cur_group_id, tokens.get(1));
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
