/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.Component;

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

import Utils.GroupChatMemberInfo;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GroupChatMemberTable extends javax.swing.JFrame {

    private JTable table;
    private static DefaultTableModel tableModel;
    private final String[] columnNames = {"Group ID", "Username", "Joined Date"};

    public GroupChatMemberTable(ArrayList<GroupChatMemberInfo> memberInfoList) {
        init(memberInfoList);
    }

    private void init(ArrayList<GroupChatMemberInfo> memberInfoList) {
        this.setTitle("Group Chat Member");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        table = new JTable();
        
        tableModel = new DefaultTableModel(); 
        GroupChatMemberTable.tableModel.setColumnIdentifiers(columnNames);
        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        for (var member : memberInfoList) {
            System.out.println(member.username);
            GroupChatMemberTable.addRowtoTable(new Object[]{
                member.group_id.toString(), member.username.toString(), member.joined_date.toString()
            });
        }
        add(scrollPane);
        this.setSize(500, 300);

        this.setLocationRelativeTo(null);
    }

    private static void addRowtoTable(Object[] dataRow) {
        tableModel.addRow(dataRow);
    }                      

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

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
