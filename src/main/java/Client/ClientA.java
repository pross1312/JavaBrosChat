package Client;

import java.io.IOException;
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
import Utils.Notify.*;

public class ClientA {
    static NotifyClient notify;
    static ApiClient api_c;
    static final int SERVER_PORT = 13123;
    static String authen_token;
    static String cur_group_id;
    static ArrayList<GroupChatInfo> groups;
    static MessageClient msg_client;
    static String username;
    static {
        try {
            Connection.init_ssl("/.ssl/root/ca.jks", "changeit");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void main(String[] args) throws Exception {
        api_c = new ApiClient("localhost", SERVER_PORT);
        shell();
    }
    static void init(String token, String username) throws Exception {
        authen_token = token;
        notify = new NotifyClient(authen_token, "localhost", SERVER_PORT);
        msg_client = new MessageClient(username, token, "localhost", SERVER_PORT);
        notify.register(FriendLogin.class, "main", (noti) -> {
            var new_login = (FriendLogin)noti;
            System.out.printf("[%s] just logged in\n", new_login.friend);
        });
        notify.register(NewGroupMsg.class, "main", (notification) -> {
            var noti = (NewGroupMsg)notification;
            api_c.async_invoke_api(result -> {
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                } else if (result instanceof ResultOk ok) {
                    ArrayList<ChatMessage> msgs = (ArrayList<ChatMessage>)ok.data();
                    msgs.forEach(x -> {
                        System.out.printf("%s [%s] %s\n",
                                x.sent_date.toLocaleString(),
                                x.sender,
                                msg_client.decrypt_group_msg(x.cipher_msg, x.target).orElse("NOPE"));
                    });
                }
            }, "GroupChatService", "get_unread_msg", authen_token, noti.group_id);
        });
        notify.register(NewFriendMsg.class, "main", (notification) -> {
            var noti = (NewFriendMsg)notification;
            api_c.async_invoke_api(result -> {
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                } else if (result instanceof ResultOk ok) {
                    ArrayList<ChatMessage> msgs = (ArrayList<ChatMessage>)ok.data();
                    msgs.forEach(x -> {
                        System.out.printf("%s [%s] %s\n",
                                x.sent_date.toLocaleString(),
                                x.sender,
                                msg_client.decrypt_usr_msg(x.cipher_msg, x.sender).orElse("NOPE"));
                    });
                }
            }, "FriendChatService", "get_unread_msg", authen_token, noti.sender);
        });
    }
    public static void shell() throws Exception {
        var scanner = new Scanner(System.in);
        while (true) {
            var line = scanner.nextLine().trim();
            var tokens = Arrays.stream(line.split(" ")).map(x -> x.trim()).toList();
            switch (tokens.get(0)) {
                case "/logout": {
                    var result = api_c.invoke_api("AccountService", "logout", authen_token);
                    if (result instanceof ResultError err) {
                        System.out.println(err.msg());
                    }
                    notify.close();
                    break;
                }
                case "/register": {
                    var result = api_c.invoke_api("AccountService", "register", tokens.get(1), tokens.get(2),
                            new UserInfo(tokens.get(1), "tuong321", "1j2oi", "helloeverybody648@gmail.com", new java.util.Date(), UserInfo.Gender.Male));
                    if (result instanceof ResultError err) {
                        System.out.println(err.msg());
                    }
                    break;
                }
                case "/login": {
                    username = tokens.get(1);
                    var result = api_c.invoke_api("AccountService", "login",
                            tokens.get(1), tokens.get(2));
                    if (result instanceof ResultError err) {
                        System.out.println(err.msg());
                    } else if (result instanceof ResultOk ok) {
                        init(((Pair<String, AccountType>)ok.data()).a, tokens.get(1));
                    }
                    break;
                }
                case "/listfriend": {
                    var result = api_c.invoke_api("UserManagementService", "list_friends", authen_token);
                    if (result instanceof ResultError err) {
                        System.out.println(err.msg());
                    } else if (result instanceof ResultOk ok) {
                        var friends = (ArrayList<Pair<UserInfo, Boolean>>)ok.data();
                        for (var x : friends) {
                            System.out.printf("%s : %s\n", x.a.username, x.b);
                        }
                    }
                    break;
                }
                case "/friend": {
                    msg_client.add_friend(tokens.get(1));
                    break;
                }
                case "/unfriend": {
                    var result = api_c.invoke_api("UserManagementService", "unfriend", authen_token, tokens.get(1));
                    if (result instanceof ResultError err) {
                        System.out.println(err.msg());
                    }
                    break;
                }
                case "/create": {
                    var users = Arrays.stream(scanner.nextLine().split(" ")).map(x -> x.trim()).toList();
                    var res = msg_client.create_group(tokens.get(1), new ArrayList<>(users));
                    if (res instanceof ResultError err) {
                        System.out.println(err.msg());
                    } else if (res instanceof ResultOk ok) {
                        cur_group_id = (String)ok.data();
                    }
                    break;
                }
                case "/use": {
                    cur_group_id = groups.get(Integer.parseInt(tokens.get(1))).id;
                    break;
                }
                // case "/newmsg": {
                //     var result = api_c.invoke_api("GroupChatService", "get_unread_msg", authen_token, cur_group_id);
                //     if (result instanceof ResultError err) {
                //         System.out.println(err.msg());
                //     } else if (result instanceof ResultOk ok) {
                //         var msgs = (ArrayList<ChatMessage>)ok.data();
                //         for (int i = 0; i < msgs.size(); i++) {
                //             var sender = msgs.get(i).sender;
                //             var msg = msg_client.decrypt_user_msg(sender, msgs.get(i).cipher_msg);
                //             if (msg.isEmpty()) System.out.println("Can't decrypt message from " + sender);
                //             else System.out.printf("[%s] %s\n", sender, msg.get());
                //         }
                //     }
                //     break;
                // }
                case "/delete": {
                    var result = api_c.invoke_api("GroupChatService", "remove_member", authen_token, cur_group_id, tokens.get(1));
                    if (result instanceof ResultError err) {
                        System.out.println(err.msg());
                    }
                    break;
                }
                case "/sendfriend": {
                    System.out.print("MSG: ");
                    var text = scanner.nextLine();
                    String res = msg_client.send_friend(tokens.get(1), text);
                    if (res != null) {
                        System.out.println("[ERROR] " + res);
                    }
                    break;
                }
                case "/send": {
                    System.out.print("MSG: ");
                    var text = scanner.nextLine();
                    String res = msg_client.send_group(cur_group_id, text);
                    if (res != null) {
                        System.out.println("[ERROR] " + res);
                    }
                    break;
                }
                case "/rename": {
                    var result = api_c.invoke_api("GroupChatService", "rename",
                            authen_token, cur_group_id, tokens.get(1));
                    if (result instanceof ResultError err) {
                        System.out.println(err.msg());
                    }
                    break;
                }
                case "/set_admin": {
                    var result = api_c.invoke_api("GroupChatService", "set_admin",
                            authen_token, cur_group_id, tokens.get(1), Boolean.TRUE);
                    if (result instanceof ResultError err) {
                        System.out.println(err.msg());
                    }
                    break;
                }
                case "/add_member": {
                    String res = msg_client.add_usr_to_group(tokens.get(1), cur_group_id);
                    if (res != null) {
                        System.out.println(res);
                    }
                    break;
                }
                case "/recover": {
                    var result = api_c.invoke_api("AccountService",
                            "recover_pass", tokens.get(1));
                    if (result instanceof ResultError err) {
                        System.out.println(err.msg());
                    }
                    break;
                }
                case "/report": {
                    var api_res = api_c.invoke_api("UserManagementService", "report_spam",
                            authen_token, tokens.get(1), tokens.stream().skip(2).reduce("", (acc, x) -> acc + x));
                    if (api_res instanceof ResultError err) {
                        System.out.println(err.msg());
                    }
                    break;
                }
                case "/allgroup": {
                    var api_res = api_c.invoke_api("GroupChatService", "get_all_msg", authen_token, cur_group_id);
                    if (api_res instanceof ResultError err) {
                        System.out.println(err.msg());
                    } else if (api_res instanceof ResultOk ok) {
                        ArrayList<ChatMessage> msgs = (ArrayList<ChatMessage>)ok.data();
                        msgs.forEach(x -> {
                            System.out.printf("%s [%s] %s\n",
                                    x.sent_date.toLocaleString(),
                                    x.sender,
                                    x.cipher_msg != null ?
                                        msg_client.decrypt_group_msg(x.cipher_msg, x.target)
                                                  .orElse("NOPE") :
                                        "[DELETED]");
                        });
                    }
                    break;
                }
                // case "/allfriend": {
                //     msg_client.all_friend_msg(tokens.get(1)).forEach(msg -> {
                //         var sender = msg.sender;
                //         System.out.printf("[%s] %s\n", sender, msg.text);
                //     });
                //     break;
                // }
                case "/list": {
                    var result = api_c.invoke_api("GroupChatService", "list_groups", authen_token);
                    if (result instanceof ResultError err) {
                        System.out.println(err.msg());
                    } else if (result instanceof ResultOk ok) {
                        groups = (ArrayList<GroupChatInfo>)ok.data();
                        for (int i = 0; i < groups.size(); i++) {
                            System.out.printf("%3s: %s\n", i, groups.get(i).name);
                        }
                    }
                    break;
                }
                default:
                    System.out.printf("Unknown command '%s'\n", tokens.get(0));
            }
        }
    }
}
