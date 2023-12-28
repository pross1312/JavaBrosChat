package Client;

import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Utils.AESParam;

public class ChatSession {
    private SecretKey key;
    private IvParameterSpec iv;
    public ChatSession(AESParam params) {
        this.key = new SecretKeySpec(params.secret, "AES");
        this.iv = new IvParameterSpec(params.iv);
    }
    public AESParam get_param() {
        return new AESParam(key.getEncoded(), iv.getIV());
    }
    public byte[] encrypt(byte[] clear) { // NOTE: encrypt should never fail
        try {
            return AESCipher.encrypt(clear, key, iv);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<byte[]> decrypt(byte[] cipher_text) {
        try {
            return Optional.of(AESCipher.decrypt(cipher_text, key, iv));
        } catch(Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

