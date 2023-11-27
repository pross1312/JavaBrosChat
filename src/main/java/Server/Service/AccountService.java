package Server.Service;
import Utils.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

import Server.Server;
import Server.DB.*;
class Helper {
    static String hash_password(String raw_pass) {
        try {
            var digester = MessageDigest.getInstance("SHA-256");
            byte[] encoded_hash = digester.digest(raw_pass.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encoded_hash);
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}

public class AccountService extends Service {
     Pair<String, AccountType> login(String username, String pass) throws Exception { // return a token from Account
        try {
            var account = AccountDb.query(username);
            if (account == null) throw new Error("Invalid account.");
            var hash_pass = Helper.hash_password(pass + username);
            if (account.hashed_pass.compareTo(hash_pass) == 0) {
                var token_raw = String.format("%s:%s", username, hash_pass);
                var token = Base64.getEncoder().encodeToString(token_raw.getBytes());
                if (Server.accounts.containsKey(token)) {
                    // TODO: handle this case properly
                    LoginRecordDb.add(username);
                    Server.accounts.put(token, new Pair(username, account.type));
                    return new Pair<String, AccountType>(token, account.type);
                } else {
                    LoginRecordDb.add(username);
                    Server.accounts.put(token, new Pair(username, account.type));
                    return new Pair<String, AccountType>(token, account.type);
                }
            } else {
                throw new Error("Invalid username or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Server error, can't query database");
        }
    }
    void register(String username, String pass, UserInfo info) throws Exception {
        try {
            if (username.compareTo(info.username) != 0) {
                throw new Error("Can't register account with different username and info.username");
            }
            if (AccountDb.query(username) == null) {
                AccountDb.add(new AccountDb(username, Helper.hash_password(pass + username), AccountType.User, false));
                if (!UserInfoDb.add(info)) {
                    System.out.println("Failed userinfo");
                }
                if (!RegistrationRecordDb.add(username)) {
                    System.out.println("Failed register");
                }
            } else {
                throw new Error(String.format("Username '%s' existed", username));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Server error, can't query database.");
        }
    }
    void recover_pass(String username) {
        throw new Error("Unimplemented");
    }
}

