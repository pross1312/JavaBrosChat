package Server.DB;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Utils.KeyBundle;
import Utils.Pair;

public class KeyBundleDb {
    private static Database db = Server.Main.db;
    private static PreparedStatement insert_sm;
    static {
        try {
            insert_sm = db.conn.prepareStatement("INSERT INTO KeyBundle VALUES(?, ?, ?, ?, ?, ?, ?)");
        } catch (Exception e) {
            // TODO: properly handle exception
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void add(String name, KeyBundle key_bundle) throws SQLException {
        insert_sm.setString(1, name);
        insert_sm.setInt(2, key_bundle.regis_id);
        insert_sm.setInt(3, key_bundle.dev_id);
        insert_sm.setInt(4, key_bundle.signed_key_id);
        insert_sm.setBytes(5, key_bundle.signed_key_pub);
        insert_sm.setBytes(6, key_bundle.signed_key_sig);
        insert_sm.setBytes(7, key_bundle.identity_pub);
        if (insert_sm.executeUpdate() != 1) throw new RuntimeException("Expect insert statement to affect 1 row at least");
    }
    public static List<Pair<String, KeyBundle>> get_all() throws SQLException {
        Statement st = db.conn.createStatement();
        var result = st.executeQuery("SELECT * FROM KeyBundle");
        if (result == null) throw new RuntimeException("Result set of query operation can't be null");
        ArrayList<Pair<String, KeyBundle>> identities = new ArrayList<>();
        while (result.next()) {
            String name = result.getString("address");
            int regis_id = result.getInt("regis_id");
            int dev_id = result.getInt("dev_id");
            int signed_key_id = result.getInt("signed_key_id");
            byte[] signed_key_pub = result.getBytes("signed_key_pub");
            byte[] signed_key_sig = result.getBytes("signed_key_sig");
            byte[] identity_pub = result.getBytes("identity_pub");
            identities.add(new Pair<>(name, new KeyBundle(regis_id, dev_id, signed_key_id, signed_key_pub, signed_key_sig, identity_pub)));
        }
        return identities;
    }
}


