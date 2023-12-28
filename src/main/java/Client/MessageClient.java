package Client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import Utils.AESParam;
import Utils.ResultError;
import Utils.ResultOk;

public class MessageClient {

    public enum ChatType {
        GROUP,
        USER
    }
    String username, token;
    ApiClient api_c;
    KeyPair identity;
    static Cipher cipher;
    HashMap<String, PublicKey> identities;
    HashMap<String, ChatSession> chat_sessions;

    static {
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MessageClient(String username, String token, String addr, int port) {
        this.username = username;
        this.token = token;
        this.api_c = new ApiClient(addr, port);
        this.identities = new HashMap<>();
        this.chat_sessions = new HashMap<>();
        File dir = new File(".client-secret");
        if (!dir.exists()) dir.mkdirs();
        var secret_path = String.format(".client-secret%c%s.identity", File.separatorChar, username);
        var secret_file = new File(secret_path);
        try {
            if (!secret_file.isFile()) {
                init_identity(secret_path);
            } else {
                load_identity(secret_path);
            }
            update_identity();
            identities.put(username, identity.getPublic());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Object> byte_to_obj(byte[] data) {
        try {
            ByteArrayInputStream boi = new ByteArrayInputStream(data);
            ObjectInputStream obj_in = new ObjectInputStream(boi);
            Object result = obj_in.readObject();
            return Optional.of(result);
        } catch(Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<byte[]> obj_to_byte(Object k) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream obj_out = new ObjectOutputStream(bos);
            obj_out.writeObject(k);
            obj_out.flush();
            obj_out.close();
            return Optional.of(bos.toByteArray());
        } catch(Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private synchronized Optional<PublicKey> get_identity(String target)  {
        var result = identities.get(target);
        if (result == null) {
            var api_result = api_c.invoke_api("E2EService", "get_identity", token, target);
            if (api_result instanceof ResultError err) {
                System.out.println(err.msg());
                return Optional.empty();
            } else if (api_result instanceof ResultOk ok) {
                var data = (byte[])ok.data();
                try {
                    var key_spec = new X509EncodedKeySpec(data);
                    result = KeyFactory.getInstance("RSA").generatePublic(key_spec);
                    identities.put(target, result);
                } catch(Exception e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
            }
        }
        return Optional.of(result);
    }

    public static byte[] encrypt(byte[] data, PublicKey public_key) { // NOTE: encrypt must never fail
        try {
            cipher.init(Cipher.ENCRYPT_MODE, public_key);
            cipher.update(data);
            return cipher.doFinal();	
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<byte[]> decrypt(byte[] data, PrivateKey key) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            cipher.update(data);
            return Optional.of(cipher.doFinal());
        } catch(Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public synchronized Optional<ChatSession> get_user_session(String user) {
        var result = chat_sessions.get(user);
        if (result == null) {
            var api_result = api_c.invoke_api("E2EService", "get_user_secret", token, user);
            if (api_result instanceof ResultError err) {
                System.out.println(err.msg());
                return Optional.empty();
            } else if (api_result instanceof ResultOk ok) {
                var data = (byte[])ok.data();
                AESParam aes_params = (AESParam)byte_to_obj(decrypt(data, identity.getPrivate()).get()).get();
                result = new ChatSession(aes_params);
                chat_sessions.put(user, result);
            }
        }
        return Optional.of(result);
    }

    public void init_identity(String path) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        identity = keyPairGen.generateKeyPair();
        System.out.println(identity.getPublic().getClass().getName());
        ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(path));
        fout.writeObject(identity);
        fout.flush();
        fout.close();
    }

    public void update_identity() {
        try {
            var result = api_c.invoke_api("E2EService", "add_identity",
                    this.token, this.identity.getPublic().getEncoded());
            if (result instanceof ResultError err) {
                System.out.println(err.msg());
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void load_identity(String path) throws Exception {
        ObjectInputStream fin = new ObjectInputStream(new FileInputStream(path));
        var obj = fin.readObject();
        if (obj instanceof KeyPair pair) {
            this.identity = pair;
        } else throw new RuntimeException("Corrupted file");
    }

    public boolean send_friend(String friend, String text) {
        var op_session = get_user_session(friend);
        if (op_session.isEmpty()) return false;
        var data = op_session.get().encrypt(text.getBytes());
        var api_result = api_c.invoke_api("FriendChatService", "send_msg", token, data, friend);
        if (api_result instanceof ResultError err) {
            System.out.println(err.msg());
            return false;
        }
        return true;
    }

    public synchronized boolean add_friend(String friend) {
        var api_result = api_c.invoke_api("UserManagementService", "add_friend", token, friend);
        if (api_result instanceof ResultError err) {
            System.out.println(err.msg());
            return false;
        } else if (api_result instanceof ResultOk ok) {
            var added = (boolean)ok.data();
            if (added) {
                try {
                    var aes_params = AESCipher.generate_param();
                    var data = obj_to_byte(aes_params).get();
                    var op_public = get_identity(friend);
                    if (op_public.isEmpty()) return false;
                    api_result = api_c.invoke_api("E2EService", "add_user_secret",
                            token, friend,
                            encrypt(data, identity.getPublic()), // my key to chat with friend
                            encrypt(data, op_public.get())); // friend key to chat with me
                    if (api_result instanceof ResultError err) {
                        System.out.println(err.msg());
                    } else if (api_result instanceof ResultOk) {
                        chat_sessions.put(username, new ChatSession(aes_params));
                        chat_sessions.put(friend, new ChatSession(aes_params));
                    }
                } catch(Exception e) {
                    throw new RuntimeException(e); // must never happen
                }
            }
        }
        return true;
    }

    public Optional<String> decrypt_usr_msg(byte[] cipher_msg, String friend) {
        if (cipher_msg == null) return Optional.empty();
        var op_session = get_user_session(friend);
        if (op_session.isEmpty()) return Optional.empty();
        var result = op_session.get().decrypt(cipher_msg);
        if (result.isEmpty()) return Optional.empty();
        return Optional.of(new String(result.get(), StandardCharsets.UTF_8));
    }

    public synchronized Optional<String> create_group(String name, ArrayList<String> usrs) {
        var aes_params = AESCipher.generate_param();
        byte[] group_secret = obj_to_byte(aes_params).get();
        var api_res = api_c.invoke_api("GroupChatService", "create", token, name, usrs);
        if (api_res instanceof ResultError err) {
            System.out.println(err.msg());
        } else if (api_res instanceof ResultOk ok) {
            var g_id = (String)ok.data();
            if (usrs.indexOf(username) == -1) usrs.add(username); // add to make things easier
            usrs.stream().map(member -> {
                var op_pub_key = get_identity(member);
                if (op_pub_key.isPresent()) {
                    var data = encrypt(group_secret, op_pub_key.get());
                    return api_c.async_invoke_api(res -> {
                        if (res instanceof ResultError err) {
                            System.out.println(err.msg());
                        }
                    }, "E2EService", "add_group_secret", token, member, g_id, data);
                }
                return null;
            }).forEach(x -> {
                try {
                    if (x != null) x.join();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            });
            chat_sessions.put(g_id, new ChatSession(aes_params));
            return Optional.of(g_id);
        }
        return Optional.empty();
    }

    public synchronized Optional<ChatSession> get_group_session(String g_id) {
        var result = chat_sessions.get(g_id);
        if (result == null) {
            var api_result = api_c.invoke_api("E2EService", "get_group_secret", token, g_id);
            if (api_result instanceof ResultError err) {
                System.out.println(err.msg());
                return Optional.empty();
            } else if (api_result instanceof ResultOk ok) {
                var data = (byte[])ok.data();
                AESParam aes_params = (AESParam)byte_to_obj(decrypt(data, identity.getPrivate()).get()).get();
                result = new ChatSession(aes_params);
                chat_sessions.put(g_id, result);
            }
        }
        return Optional.of(result);
    }

    public boolean add_usr_to_group(String username, String g_id) {
        var op_session = get_group_session(g_id);
        var pub_key = get_identity(username);
        if (op_session.isPresent() && pub_key.isPresent()) {
            var data = obj_to_byte(op_session.get().get_param()).get();
            var api_res = api_c.invoke_api("GroupChatService", "add_member", token, g_id, username);
            if (api_res instanceof ResultError err) {
                System.out.println(err.msg());
                return false;
            }
            api_res = api_c.invoke_api("E2EService", "add_group_secret",
                    token, username, g_id, encrypt(data, pub_key.get()));
            if (api_res instanceof ResultError err) {
                System.out.println(err.msg());
                return false;
            }
        }
        return true;
    }

    public boolean send_group(String g_id, String text) {
        var op_session = get_group_session(g_id);
        if (op_session.isEmpty()) return false;
        var data = op_session.get().encrypt(text.getBytes());
        var api_result = api_c.invoke_api("GroupChatService", "send_msg", token, data, g_id);
        if (api_result instanceof ResultError err) {
            System.out.println(err.msg());
            return false;
        }
        return true;
    }

    public Optional<String> decrypt_group_msg(byte[] cipher_msg, String g_id) {
        if (cipher_msg == null) return Optional.empty();
        var op_session = get_group_session(g_id);
        if (op_session.isEmpty()) return Optional.empty();
        var result = op_session.get().decrypt(cipher_msg);
        if (result.isEmpty()) return Optional.empty();
        return Optional.of(new String(result.get(), StandardCharsets.UTF_8));
    }

    public Optional<String> decrypt_msg(byte[] cipher_msg, String target, ChatType type) {
        if (type == ChatType.GROUP) {
            return decrypt_group_msg(cipher_msg, target);
        } else {
            return decrypt_usr_msg(cipher_msg, target);
        }
    }

    public boolean send_msg(String target, String text, ChatType type) {
        var op_session = get_group_session(target);
        if (op_session.isEmpty()) {
            return false;
        }
        var data = op_session.get().encrypt(text.getBytes());
        var api_result = api_c.invoke_api(
                type == ChatType.GROUP ? "GroupChatService" : "FriendChatService", "send_msg", token, data, target);
        if (api_result instanceof ResultError err) {
            System.out.println(err.msg());
            return false;
        }
        return true;
    }
}
