package Utils;

import java.io.Serializable;

import org.whispersystems.libsignal.IdentityKey;
import org.whispersystems.libsignal.InvalidKeyException;
import org.whispersystems.libsignal.ecc.Curve;
import org.whispersystems.libsignal.state.PreKeyBundle;

public class KeyBundle implements Serializable {
    private static long serialVersionUID = 1L;
    public int regis_id;
    public int dev_id;
    public int signed_key_id;
    public byte[] signed_key_pub;
    public byte[] signed_key_sig;
    public byte[] identity_pub;
    public KeyBundle(
            int regis_id, int dev_id,
            int signed_key_id, byte[] signed_key_pub,
            byte[] signed_key_sig, byte[] identity_pub) {
        this.regis_id = regis_id;
        this.dev_id = dev_id;
        this.signed_key_id = signed_key_id;
        this.signed_key_pub = signed_key_pub;
        this.signed_key_sig = signed_key_sig;
        this.identity_pub = identity_pub;
    }
    public KeyBundle(PreKeyBundle bundle) {
        this.regis_id = bundle.getRegistrationId();
        this.dev_id = bundle.getDeviceId();
        this.signed_key_id = bundle.getSignedPreKeyId();
        this.signed_key_pub = bundle.getSignedPreKey().serialize();
        this.identity_pub = bundle.getIdentityKey().serialize();
    }
    public PreKeyBundle to_pre_key_bundle() throws InvalidKeyException {
        return new PreKeyBundle(
            regis_id,
            dev_id,
            0,
            null,
            signed_key_id,
            Curve.decodePoint(signed_key_pub, 0),
            signed_key_sig,
            new IdentityKey(identity_pub, 0));
    }
}
