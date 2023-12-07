package Server.Service;
import Utils.*;
import Utils.Notify.NewFriendLogin;

import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

import Server.DB.*;

public class AccountService extends Service {
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
                    Server.Main.server.notify(x.username, new NewFriendLogin(username));
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
            Server.Main.accounts.remove(token); // TODO: notify user's ?
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

    void recover_pass(String username) {
        throw new Error("Unimplemented");
    }
}

