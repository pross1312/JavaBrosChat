package Utils;

import java.io.Serializable;

public class GroupIdentity implements Serializable {
    private static final long serialVersionUID = 1L; // version for compatibility
    public String id;
    public byte[] identity_key_pair;
    public byte[] signed_prekey_record;
    public GroupIdentity(String group_id, byte[] iden, byte[] prekey) {
        this.id = group_id;
        this.identity_key_pair = iden;
        this.signed_prekey_record = prekey;
    }
}
