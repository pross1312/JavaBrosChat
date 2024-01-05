/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.Component;

import Client.*;
import Client.MessageClient.ChatType;
import Utils.ChatMessage;
import Utils.Notify.NewFriendMsg;
import Utils.Notify.NewGroupMsg;
import Utils.*;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.function.BiConsumer;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import view.API.CallAPI;
import view.ListItem;
import java.util.List;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author dvtuong
 */
public class ChatArea extends javax.swing.JPanel {

    /**
     * Creates new form ChatArea
     */
    static private ChatArea instance = null;
    private ChatSession session;
    private ApiClient api_c;
    private MessageClient msg_c;
    private NotifyClient noti_c;
    private String token;
    private String username;
    private List<ChatItem> messages;
    private String previous_pattern = "";

    public static ChatArea update_and_get(ChatSession session) {
        if (instance == null) instance = new ChatArea();
        if (instance.session == null || !instance.session.id.equals(session.id)) {
            if (instance.session != null && instance.session.type != session.type) {
                instance.session_label.setIcon(new javax.swing.ImageIcon(
                        ChatArea.class.getResource(
                            session.type == ChatType.USER ?
                                    "/images/icons8-person-48.png"
                                    : "/images/icons8-group-50.png"))); // NOI18N
                instance.session_label.setCursor(new java.awt.Cursor(
                        session.type == ChatType.GROUP ? java.awt.Cursor.HAND_CURSOR : java.awt.Cursor.DEFAULT_CURSOR));
            }
            instance.previous_pattern = "";
            instance.search_input.setText("");
            instance.session = session;
            instance.fetch_all_msg();
            instance.register_notification();
            instance.session_label.setText(session.get_name());
        }
        return instance;
    }

    private ChatArea() {
        api_c = Client.api_c;
        msg_c = Client.msg_c;
        noti_c = Client.noti_c;
        token = Client.token;
        username = Client.username;
        session = null;
        initComponents();
        chat_box.setLayout(new MigLayout());
        chat_container.getVerticalScrollBar().setUI(new ModernScrollPane());
    }

    private void register_notification() {
        noti_c.register(session.type == ChatType.USER ? NewFriendMsg.class : NewGroupMsg.class,
                "UD:CHAT_BOX", x -> {
            if (session.type != ChatType.USER) return;
            Result res = CallAPI.get_unread_friend(token, session.id);
            if (res instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (res instanceof ResultOk ok) {
                var data = (ArrayList<ChatMessage>) ok.data();
                data.forEach(msg -> {
                    var text = msg_c.decrypt_msg(msg.cipher_msg, session.id, session.type);
                    chat_box.add(new ChatItem(
                            msg.sender.equals(username) ? null : msg.sender, msg.sent_date,
                            text.orElse("[Message is not available]"),
                            !msg.sender.equals(username)), "wrap");
                });
                scroll_and_repaint();
            }
        });
    }

    private void scroll_and_repaint() {
        SwingUtilities.invokeLater(() -> {
            chat_container.validate();
            JScrollBar vertical = chat_container.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
            chat_container.repaint();
        });
    }

    private void send_msg() {
        String msg = input_area.getText();
        String err = msg_c.send_msg(session.id, msg, session.type);
        if (err != null) {
            JOptionPane.showMessageDialog(null, err);
        } else if (chat_box != null) {
            chat_box.add(new ChatItem(null, new Date(), msg, false), "wrap");
            input_area.setText("");
            scroll_and_repaint();
        }
    }

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        final int length = searchStr.length();
        if (length == 0) {
            return true;
        }
        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length)) {
                return true;
            }
        }
        return false;
    }

    private void fetch_all_msg() {
        chat_box.removeAll();
        var api_res = api_c.invoke_api(
                session.type == ChatType.USER ? "FriendChatService" : "GroupChatService",
                "get_all_msg", token, session.id);
        if (api_res instanceof ResultError err) {
            JOptionPane.showMessageDialog(null, err.msg());
        } else if (api_res instanceof ResultOk ok) {
            var msgs = (ArrayList<ChatMessage>) ok.data();
            messages = msgs.stream().map(msg -> {
                Optional<String> text = msg_c.decrypt_msg(msg.cipher_msg, session.id, session.type);
                var chat_item = new ChatItem(
                        msg.sender.equals(username) ? null : msg.sender, msg.sent_date,
                        text.orElse("[Message is not available]"),
                        !msg.sender.equals(username));
                chat_box.add(chat_item, "wrap");
                return chat_item;
            }).toList();
        }
        scroll_and_repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        session_label = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        search_input = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        chat_container = new javax.swing.JScrollPane();
        chat_box = new javax.swing.JLayeredPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        input_area = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        jPanel1.setForeground(new java.awt.Color(195, 191, 191));

        session_label.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        session_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-group-50.png"))); // NOI18N
        session_label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        session_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                session_labelMouseClicked(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-delete-chat-50.png"))); // NOI18N
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-ban-64.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-create-32.png"))); // NOI18N
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        search_input.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        search_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_inputActionPerformed(evt);
            }
        });
        search_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_inputKeyPressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-spam-50.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(session_label, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search_input, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(session_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(search_input)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        chat_container.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chat_container.setToolTipText("");
        chat_container.setHorizontalScrollBar(null);

        chat_box.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout chat_boxLayout = new javax.swing.GroupLayout(chat_box);
        chat_box.setLayout(chat_boxLayout);
        chat_boxLayout.setHorizontalGroup(
            chat_boxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1002, Short.MAX_VALUE)
        );
        chat_boxLayout.setVerticalGroup(
            chat_boxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 614, Short.MAX_VALUE)
        );

        chat_container.setViewportView(chat_box);

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
                .addComponent(input_area)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chat_container, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chat_container, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void session_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_session_labelMouseClicked
        // TODO add your handling code here:
        if (session.type == ChatType.GROUP) {
            String new_name = JOptionPane.showInputDialog("New name").trim();
            api_c.async_invoke_api(res -> {
                if (res instanceof ResultError err ) {
                    JOptionPane.showMessageDialog(null, err.msg());
                } else if (res instanceof ResultOk) {
                    session_label.setText(new_name);
                } else throw new RuntimeException("Unexpected");
            }, "GroupChatService", "rename",
            token, session.id, new_name);
        }
    }//GEN-LAST:event_session_labelMouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
        var res = api_c.invoke_api(
            session.type == ChatType.USER ? "FriendChatService" : "GroupChatService",
            "clear_history", token, session.id
        );
        if (res instanceof ResultError err) {
            JOptionPane.showMessageDialog(null, err.msg());
        } else {
            fetch_all_msg();
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        if (session.type == ChatType.USER) {
            var res = api_c.invoke_api("UserManagementService", "block_user",
                token, session.id);
            if (res instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            }
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        if (session.type == ChatType.GROUP) {
            api_c.async_invoke_api(res -> {
                if (res instanceof ResultError err) {
                    JOptionPane.showMessageDialog(null, err.msg());
                } else if (res instanceof ResultOk ok) {
                    var friends = (ArrayList<Pair<UserInfo, Boolean>>) ok.data();
                    res = api_c.invoke_api("GroupChatService", "list_members",
                        token, session.id);
                    if (res instanceof ResultError err) {
                        JOptionPane.showMessageDialog(null, err.msg());
                    } else if (res instanceof ResultOk okk) {
                        var members = (ArrayList<String>) okk.data();
                        BiConsumer<String, Boolean> on_click = (usr, on) -> {
                            if (on) {
                                String err = msg_c.add_usr_to_group(usr, session.id);
                                if (err != null) {
                                    JOptionPane.showMessageDialog(null, err);
                                }
                            } else {
                                var rs = api_c.invoke_api("GroupChatService", "remove_member",
                                    token, session.id, usr);
                                if (rs instanceof ResultError err) {
                                    JOptionPane.showMessageDialog(null, err.msg());
                                }
                            }
                        };
                        ListItem.get_instance(friends.stream().map(x -> {
                            return (Component) new ToggleItem(x.a.username,
                                members.contains(x.a.username), "Remove", "Add", on_click
                            );
                        }).toList(), "Manage group members").setVisible(true);
                    }
                } else {
                    throw new RuntimeException("Unexpected");
                }
            }, "UserManagementService", "list_friends", token);
        } else if (session.type == ChatType.USER) {
            api_c.async_invoke_api(res -> {
                if (res instanceof ResultError err) {
                    JOptionPane.showMessageDialog(null, err.msg());
                } else if (res instanceof ResultOk ok) {
                    var data = (ArrayList<Pair<UserInfo, Boolean>>) ok.data();
                    List<String> other_friends = data.stream().map(x -> x.a.username)
                    .filter(x -> !x.equals(session.id))
                    .toList();
                    new CreateGroupForm(other_friends, session.id).setVisible(true);
                } else {
                    throw new RuntimeException("Unexpected");
                }
            }, "UserManagementService", "list_friends", token);
        } else throw new RuntimeException("Unexpected");
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        send_msg();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void input_areaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_areaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_areaActionPerformed

    private void input_areaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_areaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            send_msg();
        }
    }//GEN-LAST:event_input_areaKeyReleased

    private void search_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_inputActionPerformed

    private void search_inputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_inputKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String pattern = search_input.getText().trim();
            if (pattern.equals(previous_pattern)) {
                return;
            }
            previous_pattern = pattern;
            chat_box.removeAll();
            if (pattern.isEmpty()) {
                messages.forEach(x -> chat_box.add(x, "wrap"));
            } else {
                messages.stream().filter(x -> {
                    return containsIgnoreCase(x.get_msg(), pattern);
                }).forEach(x -> chat_box.add(x, "wrap"));
            }
            scroll_and_repaint();
        }
    }//GEN-LAST:event_search_inputKeyPressed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        if (session.type == ChatType.USER) {
            String reason = JOptionPane.showInputDialog("Reason").trim();
            if (!reason.isEmpty()) {
                api_c.async_invoke_api(res -> {
                    if (res instanceof ResultError err) {
                        JOptionPane.showMessageDialog(null, err.msg());
                    } else if (res instanceof ResultOk) {
                    } else {
                        throw new RuntimeException("Unexpected");
                    }
                }, "UserManagementService", "report_spam",
                        token, session.id, reason);
            }
        }
    }//GEN-LAST:event_jLabel1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane chat_box;
    private javax.swing.JScrollPane chat_container;
    private javax.swing.JTextField input_area;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField search_input;
    private javax.swing.JLabel session_label;
    // End of variables declaration//GEN-END:variables
}
