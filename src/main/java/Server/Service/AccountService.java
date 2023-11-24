package Server.Service;
import Utils.*;
import Utils.Account.AccountType;

import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

import Server.Server;
import Server.DB.*;

public class AccountService extends Service {
     Pair<String, AccountType> login(String username, String pass) { // return a token from Account
        try {
            var account = AccountDb.query(username);
            if (account == null) throw new Error("Invalid account.");
            if (account.password.compareTo(pass) == 0) {
                var token_raw = String.format("%s:%s", username, pass);
                var token = Base64.getEncoder().encodeToString(token_raw.getBytes());
                if (Server.accounts.containsKey(token)) {
                    // TODO: handle this case properly
                    Server.accounts.put(token, account);
                    return new Pair<String, AccountType>(token, account.type);
                } else {
                    Server.accounts.put(token, account);
                    // TODO: add new entry to login record table
                    return new Pair<String, AccountType>(token, account.type);
                }
            } else {
                throw new Error("Invalid password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Server error, can't query database.");
        }
    }
    void register(String username, String pass, UserInfo info) {
        try {
            if (AccountDb.query(username) == null) {
                AccountDb.add(new Account(username, pass, AccountType.User));
                // TODO: add info to UserInfo table
            } else {
                throw new Error(String.format("Username '%s' existed", username));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Server error, can't query database.");
        }
    }
    void recover_pass(String username) {
        throw new Error("Unimplemented");
    }
}

