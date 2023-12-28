package Client;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import Utils.AESParam;

class AESCipher {
    static public final String algorithm = "AES/CBC/PKCS5Padding";
    public static AESParam generate_param() {
        return new AESParam(generate_key().getEncoded(), generate_iv());
    }
    public static SecretKey generate_key() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey key = keyGenerator.generateKey();
            return key;
        } catch(Exception e) {
            throw new RuntimeException(e); // make sure this does not happen
        }
    }
    public static byte[] generate_iv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
    public static byte[] encrypt(byte[] input, SecretKey key,
            IvParameterSpec iv) throws Exception {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input);
        return cipherText;
    }
    public static byte[] decrypt(byte[] cipherText, SecretKey key,
            IvParameterSpec iv) throws Exception {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(cipherText);
        return plainText;
    }
}

