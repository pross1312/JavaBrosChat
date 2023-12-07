package Server.Service;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import Server.DB.KeyBundleDb;
import Server.DB.UserFriendDb;
import Utils.KeyBundle;

public class E2EService extends Service {
    ConcurrentHashMap<String, KeyBundle> key_bundles;
    public E2EService() {
        key_bundles = new ConcurrentHashMap<>();
        try {
            KeyBundleDb.get_all().forEach(x -> {
                key_bundles.put(x.a, x.b);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void add(String token, KeyBundle bundle) throws SQLException { // add a key bundle when create new account
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute add api, token not found");
        var username = acc.a;
        if (!key_bundles.containsKey(username)) {
            key_bundles.put(username, bundle);
            KeyBundleDb.add(username, bundle);
        }
    }
    public KeyBundle get(String token, String name) throws SQLException { // get a key bundle of an account
        var acc = Server.Main.accounts.get(token);
        if (acc == null) throw new Error("Can't execute add api, token not found");
        var username = acc.a;
        if (!UserFriendDb.is_friend(username, name)) throw new Error("Can't get key bundle of people that are not your friends");
        var bundle = key_bundles.get(name);
        if (bundle != null) return bundle;
        else throw new Error("No key bundle for user " + name);
    }
}
