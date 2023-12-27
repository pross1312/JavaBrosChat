package view.Component;

import Utils.GroupChatMemberInfo;
import Utils.UserInfo;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DisplayTable extends javax.swing.JFrame {

    private static JTable table;
    private static DefaultTableModel tableModel;
    private String[] columnNames;

    public DisplayTable(ArrayList<GroupChatMemberInfo> memberInfoList, String[]columnNames, int type) {
        init(memberInfoList, columnNames);
        this.setTitle("Group Chat Member");
    }

    public DisplayTable(ArrayList<UserInfo> user_list, String[] columnNames) {
        initUserList(user_list, columnNames);
        this.columnNames = columnNames;
        this.pack();
        this.setTitle("List friend of user");
    }

    public DisplayTable(ArrayList<Date> login_Records, String[] columnNames, String username) {
        initLoginHistory(login_Records, columnNames);
        this.columnNames = columnNames;
        this.pack();
        this.setTitle("Log in History of " + username);
    }

    private void initLoginHistory(ArrayList<Date> login_Records, String[] columnNames) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        table = new JTable();

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnNames);
        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        for (var record : login_Records) {
            addRowtoTable(new Object[]{
                record
            });
        }
        add(scrollPane);
        this.setSize(400, 320);

        this.setLocationRelativeTo(null);
    }

    private void initUserList(ArrayList<UserInfo> user_list, String[] columnNames) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        table = new JTable();

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnNames);
        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        for (var user : user_list) {
            addRowtoTable(new Object[]{
                user.username, user.fullname, user.address, user.birthdate, user.gender, user.email
            });
        }
        add(scrollPane);
        this.setSize(700, 500);

        this.setLocationRelativeTo(null);
    }

    private void init(ArrayList<GroupChatMemberInfo> memberInfoList, String[]columnNames) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        table = new JTable();

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnNames);
        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        for (var member : memberInfoList) {
            this.addRowtoTable(new Object[]{
                member.group_id, member.username, member.joined_date
            });
        }
        this.add(scrollPane);
        this.setSize(500, 300);
        this.setLocationRelativeTo(null); 
    }

    private void addRowtoTable(Object[] dataRow) {
        tableModel.addRow(dataRow);
    }

}
