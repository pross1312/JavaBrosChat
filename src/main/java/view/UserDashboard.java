package view;

import Client.ApiClient;
import Client.MessageClient;
import Client.MessageClient.ChatType;
import Client.NotifyClient;
import Utils.*;
import Utils.Notify.*;
import com.google.inject.internal.MoreTypes;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import view.API.CallAPI;
import view.Component.AddFriendForm;
import view.Component.ChatArea;
import view.Component.ChatItem;
import view.Component.ChatLeft;
import view.Component.CreateGroupForm;
import view.Component.ChatSession;
import view.Component.ModernScrollPane;

public class UserDashboard extends javax.swing.JFrame {

    private static String userNameSelected;
    private static ApiClient api_c = Client.Client.api_c;
    private static MessageClient msg_c = Client.Client.msg_c;
    private static NotifyClient noti_c = Client.Client.noti_c;
    private static String token = Client.Client.token;
    private static List<ChatSession> chats;
    private static String username = Client.Client.username;
    private String current_target = null;
    private ChatType current_type = null;
    private ChatArea chat_box;
    private BiConsumer<String, ChatType> on_click_chat_session;

    public UserDashboard() {
        initComponents();
        init();
    }

    private void init() {
        menuList.setLayout(new MigLayout());
        jScrollPane1.getVerticalScrollBar().setUI(new ModernScrollPane());
        on_click_chat_session = (name, type) -> {
            current_target = name;
            current_type = type;
            chat_box = new ChatArea();
            chat_container.setViewportView(chat_box);
            chat_box.setLayout(new BoxLayout(chat_box, BoxLayout.Y_AXIS));
            register_chat_box_notification_event(type);
            var api_res = api_c.invoke_api(
                    type == ChatType.USER ? "FriendChatService" : "GroupChatService",
                    "get_all_msg", token, name);
            if (api_res instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (api_res instanceof ResultOk ok) {
                var msgs = (ArrayList<ChatMessage>) ok.data();
                msgs.forEach(msg -> {
                    Optional<String> text = msg_c.decrypt_msg(msg.cipher_msg, name, type);
                    chat_box.add(new ChatItem(
                            msg.sender.equals(username) ? null : msg.sender, msg.sent_date,
                            text.orElse("[Message is not available]"),
                            !msg.sender.equals(username)));
                });
            }
            chat_box.validate();
            chat_container.setViewportView(chat_box);
            JScrollBar vertical = chat_container.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        };
        showPeople();
    }

    public void register_menu_notification_event() {
        noti_c.register(NewFriend.class, "UD:MENU_LIST", x -> {
            var noti = (NewFriend) x;
            var session = new ChatSession(noti.friend, noti.friend, true,
                    on_click_chat_session, ChatType.USER);
            menuList.add(session, "wrap");
            chats.add(session);
            menuList.validate();
        });
        noti_c.register(NewFriendRequest.class, "UD:WINDOW", x -> {
            var noti = (NewFriendRequest) x;
               
        });
        noti_c.register(FriendLogin.class, "UD:MENU_LIST", x -> {
            var noti = (FriendLogin) x;
            for (var session : chats) {
                if (session.name.equals(noti.friend)) {
                    session.set_active(true);
                    break;
                }
            }
        });
        noti_c.register(NewFriend.class, "UD:MENU_LIST", x -> {
            var noti = (NewFriend) x;
            for (var session : chats) {
                if (session.name.equals(noti.friend)) {
                    session.set_active(true);
                    break;
                }
            }
        });
        noti_c.register(NewGroup.class, "UD:MENU_LIST", x -> {
            var noti = (NewGroup) x;
            add_group(noti.info);
        });
        noti_c.register(FriendLogout.class, "UD:MENU_LIST", x -> {
            var noti = (FriendLogout) x;
            for (var session : chats) {
                if (session.name.equals(noti.friend)) {
                    session.set_active(false);
                    break;
                }
            }
        });
    }

    public void add_group(GroupChatInfo info) {
        var session = new ChatSession(info.name, info.id, true,
                on_click_chat_session, ChatType.GROUP);
        menuList.add(session, "wrap");
        chats.add(session);
        menuList.validate();
    }

    public void register_chat_box_notification_event(ChatType type) {
        noti_c.register(NewFriendMsg.class, "UD:CHAT_BOX", x -> {
            if (current_target == null) {
                return;
            }
            var noti = (NewFriendMsg) x;
            if (!current_target.equals(noti.sender)) {
                return;
            }
            var res = type == ChatType.USER
                    ? CallAPI.get_unread_friend(token, current_target)
                    : CallAPI.get_unread_group(token, current_target);
            if (res.isEmpty()) {
                // TODO: handle error
            } else {
                res.get().forEach(msg -> {
                    var text = msg_c.decrypt_msg(msg.cipher_msg, current_target, type);
                    chat_box.add(new ChatItem(
                            msg.sender.equals(username) ? null : msg.sender, msg.sent_date,
                            text.orElse("[Message is not available]"),
                            !msg.sender.equals(username)));
                });
                chat_container.setViewportView(chat_box);
                JScrollBar vertical = chat_container.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }
        });
    }

    public void showPeople() {

        api_c.async_invoke_api(api_res -> {
            List<ChatSession> chat_sessions = null;
            if (api_res instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (api_res instanceof ResultOk ok) {
                var friends = (ArrayList<Pair<UserInfo, Boolean>>) ok.data();
                chat_sessions = new ArrayList<>(friends.stream().map(x -> {
                    var chat_item = new ChatSession(x.a.username, x.a.username, x.b,
                            on_click_chat_session, ChatType.USER);
                    menuList.add(chat_item, "wrap");
                    return chat_item;
                }).toList());
            }
            var res = api_c.invoke_api("GroupChatService", "list_groups", token);
            if (res instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (res instanceof ResultOk ok) {
                var groups = (ArrayList<GroupChatInfo>) ok.data();
                chat_sessions.addAll(groups.stream().map(x -> {
                    var chat_item = new ChatSession(x.name, x.id, true,
                            on_click_chat_session, ChatType.GROUP);
                    menuList.add(chat_item, "wrap");
                    return chat_item;
                }).toList());
                chats = chat_sessions;
            }
        }, "UserManagementService", "list_friends", token);

        register_menu_notification_event();
    }

    void handle_send_msg() {
        if (current_target == null) {
            return;
        }
        String msg = input_area.getText();
        if (!msg_c.send_msg(current_target, msg, current_type)) {
            // TODO: handle can't send message
        } else if (chat_box != null) {
            chat_box.add(new ChatItem(null, new Date(), msg, false));
            input_area.setText("");
            chat_box.validate();
            chat_container.setViewportView(chat_box);
            JScrollBar vertical = chat_container.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chat_area = new javax.swing.JScrollPane();
        jRadioButton1 = new javax.swing.JRadioButton();
        pnToolBar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        menuList = new javax.swing.JLayeredPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        input_area = new javax.swing.JTextField();
        chat_container = new javax.swing.JScrollPane();

        chat_area.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnToolBar.setBackground(new java.awt.Color(25, 118, 211));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-friends-32.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-create-32 (1).png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-person-48.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-message-32.png"))); // NOI18N
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout pnToolBarLayout = new javax.swing.GroupLayout(pnToolBar);
        pnToolBar.setLayout(pnToolBarLayout);
        pnToolBarLayout.setHorizontalGroup(
            pnToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnToolBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnToolBarLayout.setVerticalGroup(
            pnToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnToolBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addContainerGap(559, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setForeground(new java.awt.Color(102, 102, 102));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-search-30.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add-user (2).png"))); // NOI18N
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add-group (1).png"))); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        jPanel1.setForeground(new java.awt.Color(195, 191, 191));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        menuList.setBackground(new java.awt.Color(255, 255, 255));
        menuList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout menuListLayout = new javax.swing.GroupLayout(menuList);
        menuList.setLayout(menuListLayout);
        menuListLayout.setHorizontalGroup(
            menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 383, Short.MAX_VALUE)
        );
        menuListLayout.setVerticalGroup(
            menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(menuList);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setBackground(new java.awt.Color(204, 204, 204));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-send-48.png"))); // NOI18N
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        input_area.setFont(new java.awt.Font("SansSerif.plain", 0, 26)); // NOI18N
        input_area.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_areaActionPerformed(evt);
            }
        });
        input_area.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                input_areaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(input_area, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                    .addComponent(input_area))
                .addContainerGap())
        );

        chat_container.setHorizontalScrollBar(null);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1033, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chat_container))
                    .addContainerGap()))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(chat_container, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1))
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1109, 807));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        new AddFriendForm().setVisible(true);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        Consumer<GroupChatInfo> cb = (info) -> {
            add_group(info);
        };
        api_c.async_invoke_api(res -> {
            if (res instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (res instanceof ResultOk ok) {
                var data = (ArrayList<Pair<UserInfo, Boolean>>) ok.data();
                new CreateGroupForm(data.stream().map(x -> x.a).toList(),
                        cb).setVisible(true);
            } else {
                throw new RuntimeException("Unexpected");
            }
        }, "UserManagementService", "list_friends", token);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void input_areaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_areaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_areaActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        handle_send_msg();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void input_areaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_areaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            handle_send_msg();
        }
    }//GEN-LAST:event_input_areaKeyReleased

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        ListFriendRequest.get_instance().setVisible(true);
    }//GEN-LAST:event_jLabel2MouseClicked

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserDashboard.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserDashboard.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserDashboard.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserDashboard.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane chat_area;
    private javax.swing.JScrollPane chat_container;
    private javax.swing.JTextField input_area;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLayeredPane menuList;
    private javax.swing.JPanel pnToolBar;
    // End of variables declaration//GEN-END:variables
}
