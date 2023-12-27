package Utils;
import java.io.Serializable;

public class AESParam implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public byte[] secret;
    public byte[] iv;
    public AESParam(byte[] secret, byte[] iv) {
        this.secret = secret;
        this.iv = iv;
    }
}
