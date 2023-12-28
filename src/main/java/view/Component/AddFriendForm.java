package view.Component;

import Utils.P2PStatus;
import Utils.Pair;
import Utils.ResultError;
import Utils.ResultOk;
import Utils.UserInfo;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;

public class AddFriendForm extends javax.swing.JFrame {

    public AddFriendForm() {
        initComponents();
        init();
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void init() {
        jScrollPane1.getVerticalScrollBar().setUI(new ModernScrollPane());
        listFriend.setLayout(new MigLayout());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        input = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listFriend = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(281, 73, 110, 39));

        input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputActionPerformed(evt);
            }
        });
        getContentPane().add(input, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 73, 226, 39));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Add Friend");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 411, -1));

        jLabel2.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"));
        jLabel2.setText("__________________________________________________________________________________________");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 435, 5));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout listFriendLayout = new javax.swing.GroupLayout(listFriend);
        listFriend.setLayout(listFriendLayout);
        listFriendLayout.setHorizontalGroup(
            listFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
        );
        listFriendLayout.setVerticalGroup(
            listFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 528, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(listFriend);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 169, 435, 487));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        var pattern = input.getText().trim();
        if (!pattern.isBlank()) {
            listFriend.removeAll();
            var res = Client.Client.api_c.invoke_api("UserManagementService", "find_users",
                    Client.Client.token, pattern);
            if (res instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (res instanceof ResultOk ok) {
                var usrs = (ArrayList<Pair<UserInfo, P2PStatus>>) ok.data();
                for (var usr : usrs) {
                    var info = usr.a;
                    var status = usr.b;
                    listFriend.add(new ItemPeople(info.username, status), "wrap");
                }
                listFriend.validate();
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddFriendForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddFriendForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddFriendForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddFriendForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddFriendForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField input;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLayeredPane listFriend;
    // End of variables declaration//GEN-END:variables
}
