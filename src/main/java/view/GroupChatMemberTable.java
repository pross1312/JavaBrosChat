package view;

import Utils.GroupChatMemberInfo;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class GroupChatMemberTable extends javax.swing.JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private final String[] columnsName = {"Group ID", "Username", "Joined Date"};

    public GroupChatMemberTable(ArrayList<GroupChatMemberInfo> memberInfoList) {
        initComponents();
        init(memberInfoList);
    }

    private void init(ArrayList<GroupChatMemberInfo> memberInfoList) {
        
        setTitle("Group Chat Member");
        table = new JTable(null, columnsName);
        for (int i = 0; i < columnsName.length; i++) {

            TableColumn tc = table.getColumnModel().getColumn(i);
            tc.setHeaderValue(columnsName[i]);

            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER); //For Aligning the Elements of all columns to CENTER
            tc.setCellRenderer(dtcr);
        }
        
        JScrollPane scrollPane = new JScrollPane(table);

        for (var member : memberInfoList) {
            addRowtoTable(new Object[]{
                member.group_id, member.username, member.joined_date
            }, tableModel);
        }

        add(scrollPane, BorderLayout.CENTER);
        setSize(500, 300);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);
    }

    public static void addRowtoTable(Object[] dataRow, DefaultTableModel model) {
        model.addRow(dataRow);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(GroupChatMemberTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GroupChatMemberTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GroupChatMemberTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GroupChatMemberTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

     
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
