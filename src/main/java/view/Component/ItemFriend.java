package view.Component;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

public class ItemFriend extends javax.swing.JPanel {

    public ItemFriend(String name) {
        initComponents();
        txtName.setText(name);
        init();
    }

    private void init() {
        lbAdd.setVerticalAlignment(SwingConstants.NORTH);

        lbAdd.setBorder(new CompoundBorder( // sets two borders
                BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(25, 118, 211)), // outer border
                BorderFactory.createEmptyBorder(5, 5, 5, 5))); // inner invisible border as the margin
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbAdd = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        txtName.setBackground(new java.awt.Color(255, 255, 255));
        txtName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtName.setText("Name");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-person-48.png"))); // NOI18N

        lbAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbAdd.setForeground(new java.awt.Color(25, 118, 211));
        lbAdd.setText("Add Friend");
        lbAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbAddMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbAddMouseExited(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                .addComponent(lbAdd)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbAdd))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lbAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAddMouseEntered
       
        
    }//GEN-LAST:event_lbAddMouseEntered

    private void lbAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAddMouseExited
        
    }//GEN-LAST:event_lbAddMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbAdd;
    private javax.swing.JLabel txtName;
    // End of variables declaration//GEN-END:variables
}
