package Server.Service;
import Utils.*;
import Utils.Notify.*;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

import Server.DB.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AccountService extends Service {
    ConcurrentHashMap<Integer, Pair<String, ScheduledFuture>> codes = new ConcurrentHashMap<>();

    Pair<String, AccountType> login(String username, String pass) throws SQLException { // return a token from Account
        var account = AccountDb.query(username);
        if (account == null) throw new Error("Invalid username or password");
        if (account.is_locked) throw new Error("Can't log in, account is locked");
        var hash_pass = Helper.hash_password(pass + username);
        if (account.hashed_pass.compareTo(hash_pass) == 0) {
            var token_raw = String.format("%s:%s", username, hash_pass);
            var token = Base64.getEncoder().encodeToString(token_raw.getBytes());
            if (Server.Main.accounts.containsKey(token)) { // TODO: notify user's ?
                // TODO: handle this case properly
                LoginRecordDb.add(username);
                Server.Main.accounts.put(token, new Pair<String, AccountType>(username, account.type));
                return new Pair<String, AccountType>(token, account.type);
            } else {
                UserFriendDb.list_friends_info(username).forEach(x -> {
                    Server.Main.server.notify(x.username, new FriendLogin(username));
                });
                LoginRecordDb.add(username);
                Server.Main.accounts.put(token, new Pair<String, AccountType>(username, account.type));
                return new Pair<String, AccountType>(token, account.type);
            }
        } else {
            throw new Error("Invalid username or password");
        }
    }

    void logout(String token) throws SQLException { // return a token from Account
        var acc = Server.Main.accounts.get(token);
        if (acc != null) {
            var username = acc.a;
            Server.Main.accounts.remove(token); // TODO: notify user's ?
            Server.Main.server.remove_noti_client(username);
            UserFriendDb.list_friends_info(username).forEach(x -> {
                Server.Main.server.notify(x.username, new FriendLogout(username));
            });
        } else throw new Error("Can't execute logout api, token not found");
    }

    void register(String username, String pass, UserInfo info) throws SQLException {
        if (username.compareTo(info.username) != 0) {
            throw new Error("Can't register account with different username and info.username");
        }
        if (AccountDb.query(username) == null) {
            AccountDb.add(new AccountDb(username, Helper.hash_password(pass + username), AccountType.User, false));
            Server.Main.db.set_auto_commit(false);
            if (!UserInfoDb.add(info)) {
                throw new RuntimeException("Can't execute register api");
            }
            if (!RegistrationRecordDb.add(username)) {
                throw new RuntimeException("Can't execute register api");
            }
            Server.Main.db.commit();
            Server.Main.db.set_auto_commit(true);
        } else {
            throw new Error(String.format("Username '%s' existed", username));
        }
    }
    void change_pass(Integer code, String pass) throws SQLException {
        var data = codes.get(code);
        if (data == null) {
            throw new Error("Invalid code");
        }
        var username = data.a;
        if (!AccountDb.change_pass(username, Helper.hash_password(pass + username))) {
            throw new Error("Can't change user password");
        }
        codes.remove(code);
        data.b.cancel(false);
    }
    void recover_pass(String username) throws SQLException {
        var user = UserInfoDb.query(username);
        if (user == null) {
            throw new Error("Username not existsed");
        }
        Integer code = -1;
        do {
            code = (int)(Math.random() * 100000000);
        } while(codes.containsKey(code));
        var result = Server.Main.mailer.send(user.email,
                "Recover password",
                String.format("Code: %d\nThis code will expire after 10mins.\nPlease don't show it to anybody",
                        code.intValue()));
        if (!result) {
            throw new Error("Can't send mail to user " + username);
        }
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final Integer _code = code;
        ScheduledFuture future = scheduler.schedule(() -> {
            codes.remove(_code);
        }, 10, TimeUnit.MINUTES);
        codes.put(code, new Pair<>(username, future));
    }
}

