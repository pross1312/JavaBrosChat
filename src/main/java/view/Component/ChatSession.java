package view.Component;

import Client.MessageClient.ChatType;
import Utils.ResultError;
import Utils.ResultOk;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import view.UserDashboard;

public class ChatSession extends javax.swing.JPanel {
    public String id;
    public ChatType type;
    public Consumer<ChatSession> on_click;
    private static final Color active_c = new Color(0, 204, 51);
    private static final Color inactive_c = new Color(48, 48, 48);
    public ChatSession(String label, String id, Consumer<ChatSession> on_click,
            boolean is_active, ChatType type) {
        initComponents();
        this.id = id; 
        this.type = type;
        txtName.setText(label);
        set_active(is_active);
        this.on_click = on_click;
        this.new_msg_count.setText("0");
        this.new_msg_count.setVisible(false);
        init();
    }
    
    public void rename(String name) {
        this.txtName.setText(name);
        view.Utils.swing_repaint(this.txtName);
    }
    
    public String get_name() {
        return this.txtName.getText();
    }
    
    public void set_active(boolean is_active) {
        txtName.setForeground(is_active ? active_c : inactive_c);
        view.Utils.swing_repaint(this.txtName);
    }
    
    public void set_new_msg_count(int count) {
        if (count > 0) {
            this.new_msg_count.setText(Integer.toString(count));
            this.new_msg_count.setVisible(true);
        } else {
            this.new_msg_count.setVisible(false);
        }
    }
    
    private void init() {
        var original_bg = this.getBackground();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(original_bg);
            }
        });
        if (this.type == ChatType.GROUP) {
            this.unfriend_btn.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        unfriend_btn = new javax.swing.JButton();
        new_msg_count = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setMaximumSize(new java.awt.Dimension(32767, 64));
        setMinimumSize(new java.awt.Dimension(200, 64));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        txtName.setBackground(new java.awt.Color(255, 255, 255));
        txtName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtName.setText("Name");
        txtName.setPreferredSize(null);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-person-48.png"))); // NOI18N

        unfriend_btn.setText("Unfriend");
        unfriend_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unfriend_btnActionPerformed(evt);
            }
        });

        new_msg_count.setForeground(new java.awt.Color(255, 51, 51));
        new_msg_count.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(new_msg_count)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(unfriend_btn))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(unfriend_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
            .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(new_msg_count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        //TODO send username to User Dash Board 
        this.on_click.accept(this);
    }//GEN-LAST:event_formMouseClicked

    private void unfriend_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unfriend_btnActionPerformed
        // TODO add your handling code here:
        if (this.type == ChatType.USER) {
            Client.Client.api_c.async_invoke_api(res -> {
                if (res instanceof ResultError err) {
                    JOptionPane.showMessageDialog(null, err.msg());
                } else if (res instanceof ResultOk) {
                } else throw new RuntimeException("Unexpected");
            }, "UserManagementService", "unfriend", Client.Client.get_instance().token, this.id);
        }
    }//GEN-LAST:event_unfriend_btnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel new_msg_count;
    private javax.swing.JLabel txtName;
    private javax.swing.JButton unfriend_btn;
    // End of variables declaration//GEN-END:variables
}
