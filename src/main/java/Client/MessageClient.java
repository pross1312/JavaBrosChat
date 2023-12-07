package Client;

import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.whispersystems.libsignal.IdentityKey;
import org.whispersystems.libsignal.IdentityKeyPair;
import org.whispersystems.libsignal.InvalidKeyException;
import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.ecc.*;
import org.whispersystems.libsignal.protocol.PreKeySignalMessage;
import org.whispersystems.libsignal.state.*;
import org.whispersystems.libsignal.state.impl.InMemorySignalProtocolStore;
import org.whispersystems.libsignal.util.KeyHelper;

import Utils.*;

public class MessageClient {
    private static final int SIGNED_PRE_KEY_ID = 0; // NOTE: Currently support 1 and only 1 signed prekey
    private static final int DEV_ID = 0; // NOTE: currently support 1 device per account
                                         // TODO: update later
    ConcurrentHashMap<String, Session> sessions;
    ConcurrentHashMap<String, List<String>> group_members;
    SignalProtocolStore store;
    SignalProtocolAddress address;

    private void init_identity() throws InvalidKeyException {
        // NOTE: Generate a bunch of keys and initialize a store, also save all keys ... to secret file
        store = new InMemorySignalProtocolStore(
                KeyHelper.generateIdentityKeyPair(),
                KeyHelper.generateRegistrationId(false));
        IdentityKeyPair identityKeyPair = store.getIdentityKeyPair();
        ECKeyPair preKeyPair = Curve.generateKeyPair();
        ECKeyPair signedPreKeyPair = Curve.generateKeyPair();
        byte[] signedPreKeySignature = Curve.calculateSignature(
                identityKeyPair.getPrivateKey(),
                signedPreKeyPair.getPublicKey().serialize());
        IdentityKey identityKey = identityKeyPair.getPublicKey();

        // NOTE: send keys to server
        long timestamp = System.currentTimeMillis();
        SignedPreKeyRecord signedPreKeyRecord = new SignedPreKeyRecord(
                SIGNED_PRE_KEY_ID, timestamp, signedPreKeyPair, signedPreKeySignature);
        store.storeSignedPreKey(SIGNED_PRE_KEY_ID, signedPreKeyRecord);
    }

    public void update_identity() throws IOException { // NOTE: send a key bundle to server
        var signed_pre_keys = store.loadSignedPreKeys();
        assert signed_pre_keys.size() == 1 : "Must not happen now until i implement e2e properly";
        var signed_pre_key = signed_pre_keys.get(0);
        var bundle = new KeyBundle(
                store.getLocalRegistrationId(),
                DEV_ID,
                signed_pre_key.getId(),
                signed_pre_key.getKeyPair().getPublicKey().serialize(),
                signed_pre_key.getSignature(),
                store.getIdentityKeyPair().getPublicKey().serialize());
        var result = ClientA.api_c.invoke_api("E2EService", "add", ClientA.authen_token, bundle);
        if (result instanceof ResultError err) {
            System.out.println(err.msg());
        }
    }

    private void save_identity(String file_path) throws IOException {
        var fout = new ObjectOutputStream(new FileOutputStream(file_path));
        fout.writeObject(address.getName());
        fout.writeInt(address.getDeviceId());
        IdentityKeyPair identityKeyPair = store.getIdentityKeyPair();
        int registrationId = store.getLocalRegistrationId();
        fout.writeInt(registrationId);
        fout.writeObject(identityKeyPair.serialize());
        var signed_pre_keys = store.loadSignedPreKeys();
        assert signed_pre_keys.size() == 1 : "Must not happen now until i implement e2e properly";
        var signed_pre_key = signed_pre_keys.get(0);
        fout.writeObject(signed_pre_key.serialize());
        fout.close();
    }

    private void load_identity(String file_path) throws Exception {
        var fin = new ObjectInputStream(new FileInputStream(file_path));
        address = new SignalProtocolAddress((String)fin.readObject(), fin.readInt());
        var regis_id = fin.readInt();
        var identity_key_pair = new IdentityKeyPair((byte[])fin.readObject());
        var signed_pre_key = new SignedPreKeyRecord((byte[])fin.readObject());
        store = new InMemorySignalProtocolStore(identity_key_pair, regis_id);
        store.storeSignedPreKey(SIGNED_PRE_KEY_ID, signed_pre_key);
        fin.close();
    }

    public MessageClient(String name) throws Exception {
        address = new SignalProtocolAddress(name, DEV_ID);
        sessions = new ConcurrentHashMap<>();
        group_members = new ConcurrentHashMap<>();
        File dir = new File(".client-secret");
        if (!dir.exists()) dir.mkdirs();
        var secret_path = String.format(".client-secret" + File.separatorChar + "%s.identity", name);
        var secret_file = new File(secret_path);
        if (!secret_file.isFile()) {
            init_identity();
            save_identity(secret_path);
        } else {
            load_identity(secret_path);
        }
        update_identity();
    }

    private Optional<Session> get_session(String name) throws IOException {
        Session session = sessions.get(name);
        if (session == null) {
            var result = ClientA.api_c.invoke_api("E2EService", "get", ClientA.authen_token, name);
            if (result instanceof ResultOk ok) {
                var bundle = (KeyBundle)ok.data();
                try {
                    session = new Session(store,
                            bundle.to_pre_key_bundle(),
                            new SignalProtocolAddress(name, DEV_ID));
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
            } else if (result instanceof ResultError err) {
                System.out.println(err.msg());
            }
            if (session != null) sessions.put(name, session);
        }
        return session == null ? Optional.empty() : Optional.of(session);
    }

    public boolean send_msg(String name, String msg) throws IOException {
        var op_session = get_session(name);
        if (op_session.isEmpty()) return false;
        var session = op_session.get();
        try {
            var result = ClientA.api_c.invoke_api("FriendChatService", "send_msg",
                    ClientA.authen_token, session.encrypt(msg).serialize(), name);
            if (result instanceof ResultError err) {
                System.out.println(err.msg());
                return false;
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) { // encrypt exception
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean send_group_msg(String group_id, String msg) throws IOException {
        var members = group_members.get(group_id);
        if (members == null) {
            var result = ClientA.api_c.invoke_api("GroupChatService", "list_members", ClientA.authen_token, group_id);
            if (result instanceof ResultError err) {
                System.out.println(err.msg());
                return false;
            } else if (result instanceof ResultOk ok) {
                members = (List<String>)ok.data();
                group_members.put(group_id, members);
            }
        }
        for (var member : members) {
            if (member.equals(address.getName())) continue;
            try {
                var op_session = get_session(member);
                if (op_session.isEmpty()) return false;
                var session = op_session.get();
                var cipher_msg = session.encrypt(msg).serialize();
                var result = ClientA.api_c.invoke_api("GroupChatService", "send_msg",
                        ClientA.authen_token, cipher_msg, group_id, member);
                if (result instanceof ResultError err) {
                    System.out.println(err.msg());
                    return false;
                }
            } catch (IOException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public Optional<String> decrypt(String sender, byte[] cipher_msg) {
        try {
            var op_session = get_session(sender);
            if (op_session.isEmpty()) return Optional.empty();
            var session = op_session.get();
            return Optional.of(session.decrypt(new PreKeySignalMessage(cipher_msg)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
