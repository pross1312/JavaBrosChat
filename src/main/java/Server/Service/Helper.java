package Server.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.security.NoSuchAlgorithmException;

public class Helper {
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

