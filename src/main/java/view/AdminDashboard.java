package view;

import view.API.CallAPI;
import Utils.GroupChatInfo;
import Utils.GroupChatMemberInfo;
import Utils.LoginRecord;
import Utils.Pair;
import Utils.RegistrationRecord;
import Utils.Result;
import Utils.ResultError;
import Utils.ResultOk;
import Utils.SpamReport;
import Utils.UserInfo;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import view.Component.DateSelectionForm;
import view.Component.DisplayTable;
import view.Interface.DateSelectionListener;
import static view.Utils.styleTable;

public class AdminDashboard extends javax.swing.JFrame implements DateSelectionListener {

    public static DefaultTableModel model;
    public static DefaultTableModel modelGroup;
    public static DefaultTableModel modelSpam;
    public static DefaultTableModel modelNewUser;
    public static DefaultTableModel modelHistoryLogin;
    public static DefaultTableModel modelActiveUser;
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    public static String token = Client.Client.token; 
    public AdminDashboard() {
        //TODO Get list of user from db and display in table. 
        initComponents();
        model = (DefaultTableModel) tblUser.getModel();
        
        Utils.initFilterCate(cbFilter, new String[]{"Name", "User name", "Status"});
        Utils.initFilterCate(cbFilterGroup, new String[]{"Name"});
        Utils.initFilterCate(cbFilterSpam, new String[]{"User name", "Time report"});
        Utils.initFilterCate(cbFilterNewUser, new String[]{"User name"});
        Utils.initFilterCate(cbStatiscal, new String[]{"New User Graph", "Active User Graph"});
        Utils.initFilterCate(cbFilterMoreUserInfo, new String[]{"Name", "Direct Friends"});
//        Utils.initFilterCate(cbFilterActivseUser, new String[]{"Name"});
        Utils.initFilterCate(cbFilterHistoryLog, new String[]{"User name"});

        styleTable(tblGroup);
        styleTable(tblNewUser);
        styleTable(tblSpam);
        styleTable(tblUser);
        styleTable(tblMoreUser);
        styleTable(tblActiveUser);
        styleTable(tblHistoryLogin);

        String msg;

        ArrayList<Pair<UserInfo, Boolean>> user_list = CallAPI.get_list_users(token);
        for (var item : user_list) {
            var user = item.a;
            var lock = item.b;
            // Get first log in Time 
            Date first_login = CallAPI.getFirstLoginTime(Client.Client.token, user.username);

            String formattedDate = sdf.format(user.birthdate);
            String formattedDate_firstLogin = null;
            if (first_login != null) {
                formattedDate_firstLogin = sdf.format(first_login);
            }

            addRowtoTable(new Object[]{user.username, user.fullname, user.address, formattedDate,
                user.gender.toString(), user.email, lock, formattedDate_firstLogin}, lock, model);
        }
        tblUser.getColumnModel().getColumn(6).setCellRenderer(new LockColumnRenderer());
        initSorter(model, tblUser);
        initFilter(jTextField1, cbFilter, tblUser);
    }

    // Gradinent 
    class JPanelGradient extends JPanel {

        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            Color color1 = new Color(52, 143, 80);
            Color color2 = new Color(56, 180, 211);

            GradientPaint gp = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 180, height, Color.decode("#000046"));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }

    private void initSorter(DefaultTableModel model, JTable table) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        Comparator<String> compare = Comparator.naturalOrder();
        sorter.sort();
    }

    private void initFilter(JTextField textField, JComboBox cbBox, JTable table) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter(textField, cbBox, table);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter(textField, cbBox, table);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFilter(textField, cbBox, table);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Navigate = new JPanelGradient();
        jLabel1 = new javax.swing.JLabel();
        txtMangeGroup = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtSpamList = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtManageNewUser = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtManageNewUser1 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        data = new javax.swing.JPanel();
        pnlUser = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        closeBtn = new javax.swing.JLabel();
        closeBtn1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        btnAddUser = new javax.swing.JButton();
        btnUpdateUser = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        btnLockUser = new javax.swing.JButton();
        btnSeeLogHistory = new javax.swing.JButton();
        btnListFriend = new javax.swing.JButton();
        btnUpdateUserPwd1 = new javax.swing.JButton();
        cbFilter = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        pnlGroup = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        closeBtn2 = new javax.swing.JLabel();
        btnMinimize = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGroup = new javax.swing.JTable();
        btnListAdminGroup = new javax.swing.JButton();
        btnListMember = new javax.swing.JButton();
        cbFilterGroup = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        pnlSpam = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSpam = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        btnListFriend2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        closeBtn4 = new javax.swing.JLabel();
        btnMinimizeSpam = new javax.swing.JLabel();
        cbFilterSpam = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        pnlActiveUser = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        closeBtn7 = new javax.swing.JLabel();
        btnMinimizeNewUser1 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblActiveUser = new javax.swing.JTable();
        cbFilterActiveUser = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        lbDate1 = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        tfActiveUser = new javax.swing.JTextField();
        lbActiveUser = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        pnlNewUser = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        closeBtn6 = new javax.swing.JLabel();
        btnMinimizeNewUser = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNewUser = new javax.swing.JTable();
        cbFilterNewUser = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        lbDate = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jTextField4 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        pnlMoreUserData = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        closeBtn10 = new javax.swing.JLabel();
        closeBtn11 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblMoreUser = new javax.swing.JTable();
        cbFilterMoreUserInfo = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        cbCompareNumber = new javax.swing.JComboBox<>();
        pnlStatiscal = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        closeBtn8 = new javax.swing.JLabel();
        closeBtn9 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        tfYear = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        cbStatiscal = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        pnlHistoryLog = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        closeBtn14 = new javax.swing.JLabel();
        closeBtn15 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblHistoryLogin = new javax.swing.JTable();
        cbFilterHistoryLog = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ADMIN DASHBOARD");

        txtMangeGroup.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMangeGroup.setForeground(new java.awt.Color(255, 255, 255));
        txtMangeGroup.setText("Manage Group");
        txtMangeGroup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtMangeGroup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMangeGroupMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Manage User");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        txtSpamList.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSpamList.setForeground(new java.awt.Color(255, 255, 255));
        txtSpamList.setText("Spam List");
        txtSpamList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtSpamList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSpamListMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Statistical");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/group.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/open-box.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/spam.png"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/statistics.png"))); // NOI18N

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-add-user-male-24.png"))); // NOI18N

        txtManageNewUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtManageNewUser.setForeground(new java.awt.Color(255, 255, 255));
        txtManageNewUser.setText("Manage new user");
        txtManageNewUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtManageNewUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtManageNewUserMouseClicked(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Active User");
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-activity-history-32.png"))); // NOI18N

        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-friends-32.png"))); // NOI18N

        txtManageNewUser1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtManageNewUser1.setForeground(new java.awt.Color(255, 255, 255));
        txtManageNewUser1.setText("More User Info");
        txtManageNewUser1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtManageNewUser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtManageNewUser1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtManageNewUser1MouseEntered(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("History Log in");
        jLabel32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
        });

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-more-32 (1).png"))); // NOI18N

        javax.swing.GroupLayout NavigateLayout = new javax.swing.GroupLayout(Navigate);
        Navigate.setLayout(NavigateLayout);
        NavigateLayout.setHorizontalGroup(
            NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(NavigateLayout.createSequentialGroup()
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NavigateLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtManageNewUser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(NavigateLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(NavigateLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(NavigateLayout.createSequentialGroup()
                                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtManageNewUser)
                                    .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtMangeGroup))
                                    .addComponent(txtSpamList, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(NavigateLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        NavigateLayout.setVerticalGroup(
            NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigateLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(45, 45, 45)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMangeGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSpamList))
                .addGap(42, 42, 42)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtManageNewUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtManageNewUser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(50, 50, 50)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        data.setLayout(new java.awt.CardLayout());

        pnlUser.setPreferredSize(new java.awt.Dimension(1002, 712));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("User Data Table ");

        closeBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn.setText("X");
        closeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtnMouseClicked(evt);
            }
        });

        closeBtn1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn1.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn1.setText("-");
        closeBtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn1MouseClicked(evt);
            }
        });

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Name", "Address", "Date of Birth", "Gender", "Email", "Lock", "Created at"
            }
        ));
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUser);

        btnAddUser.setBackground(new java.awt.Color(0, 125, 73));
        btnAddUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddUser.setForeground(new java.awt.Color(255, 255, 255));
        btnAddUser.setText("ADD");
        btnAddUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddUserMouseClicked(evt);
            }
        });
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        btnUpdateUser.setBackground(new java.awt.Color(0, 125, 73));
        btnUpdateUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateUser.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateUser.setText("UPDATE");
        btnUpdateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateUserActionPerformed(evt);
            }
        });

        btnDeleteUser.setBackground(new java.awt.Color(255, 51, 51));
        btnDeleteUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteUser.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteUser.setText("DELETE");
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        btnLockUser.setBackground(new java.awt.Color(102, 102, 102));
        btnLockUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLockUser.setForeground(new java.awt.Color(255, 255, 255));
        btnLockUser.setText("LOCK");
        btnLockUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLockUserMouseClicked(evt);
            }
        });

        btnSeeLogHistory.setText("SEE LOG");
        btnSeeLogHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSeeLogHistoryMouseClicked(evt);
            }
        });

        btnListFriend.setText("SEE LIST FRIEND");
        btnListFriend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnListFriendMouseClicked(evt);
            }
        });

        btnUpdateUserPwd1.setBackground(new java.awt.Color(0, 125, 73));
        btnUpdateUserPwd1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateUserPwd1.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateUserPwd1.setText("UPDATE PASWORD");
        btnUpdateUserPwd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateUserPwd1MouseClicked(evt);
            }
        });

        cbFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterActionPerformed(evt);
            }
        });

        jLabel11.setText("Filter by:");

        javax.swing.GroupLayout pnlUserLayout = new javax.swing.GroupLayout(pnlUser);
        pnlUser.setLayout(pnlUserLayout);
        pnlUserLayout.setHorizontalGroup(
            pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnlUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUserLayout.createSequentialGroup()
                        .addGap(0, 191, Short.MAX_VALUE)
                        .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlUserLayout.createSequentialGroup()
                                .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(btnUpdateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnLockUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlUserLayout.createSequentialGroup()
                                .addComponent(btnListFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(btnUpdateUserPwd1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnSeeLogHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 274, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUserLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUserLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(244, 244, 244)
                                .addComponent(closeBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlUserLayout.createSequentialGroup()
                                .addComponent(cbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnlUserLayout.setVerticalGroup(
            pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserLayout.createSequentialGroup()
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUserLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddUser)
                    .addComponent(btnUpdateUser)
                    .addComponent(btnDeleteUser)
                    .addComponent(btnLockUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnListFriend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdateUserPwd1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeeLogHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );

        data.add(pnlUser, "card3");

        pnlGroup.setPreferredSize(new java.awt.Dimension(1002, 712));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Group Data Table ");

        closeBtn2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn2.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn2.setText("X");
        closeBtn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn2MouseClicked(evt);
            }
        });

        btnMinimize.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMinimize.setForeground(new java.awt.Color(204, 0, 0));
        btnMinimize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMinimize.setText("-");
        btnMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseClicked(evt);
            }
        });

        tblGroup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Group id", "Group Name", "Created at"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGroup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGroupMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGroup);

        btnListAdminGroup.setText("LIST ADMIN GROUP");
        btnListAdminGroup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnListAdminGroupMouseClicked(evt);
            }
        });

        btnListMember.setText("LIST MEMBER GROUP");
        btnListMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnListMemberMouseClicked(evt);
            }
        });

        cbFilterGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterGroupActionPerformed(evt);
            }
        });

        jLabel14.setText("Filter by:");

        javax.swing.GroupLayout pnlGroupLayout = new javax.swing.GroupLayout(pnlGroup);
        pnlGroup.setLayout(pnlGroupLayout);
        pnlGroupLayout.setHorizontalGroup(
            pnlGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGroupLayout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addComponent(btnListAdminGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnListMember, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlGroupLayout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 531, Short.MAX_VALUE)
                .addGroup(pnlGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGroupLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGroupLayout.createSequentialGroup()
                        .addComponent(cbFilterGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94))))
            .addComponent(jScrollPane2)
        );
        pnlGroupLayout.setVerticalGroup(
            pnlGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGroupLayout.createSequentialGroup()
                .addGroup(pnlGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlGroupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbFilterGroup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(pnlGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnListMember, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListAdminGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43))
        );

        data.add(pnlGroup, "card3");

        tblSpam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Reporter", "Target", "Reason", "Reported at"
            }
        ));
        tblSpam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSpamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSpam);

        jLabel16.setText("Filter by:");

        btnListFriend2.setBackground(new java.awt.Color(102, 102, 102));
        btnListFriend2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnListFriend2.setForeground(new java.awt.Color(255, 255, 255));
        btnListFriend2.setText("LOCK USER");
        btnListFriend2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnListFriend2MouseClicked(evt);
            }
        });
        btnListFriend2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListFriend2ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Spam List");

        closeBtn4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn4.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn4.setText("X");
        closeBtn4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn4MouseClicked(evt);
            }
        });

        btnMinimizeSpam.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMinimizeSpam.setForeground(new java.awt.Color(204, 0, 0));
        btnMinimizeSpam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMinimizeSpam.setText("-");
        btnMinimizeSpam.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMinimizeSpam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizeSpamMouseClicked(evt);
            }
        });

        cbFilterSpam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterSpamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSpamLayout = new javax.swing.GroupLayout(pnlSpam);
        pnlSpam.setLayout(pnlSpamLayout);
        pnlSpamLayout.setHorizontalGroup(
            pnlSpamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSpamLayout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlSpamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSpamLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(187, 187, 187)
                        .addComponent(btnMinimizeSpam, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSpamLayout.createSequentialGroup()
                        .addComponent(cbFilterSpam, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(pnlSpamLayout.createSequentialGroup()
                .addGap(397, 397, 397)
                .addComponent(btnListFriend2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(464, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );
        pnlSpamLayout.setVerticalGroup(
            pnlSpamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSpamLayout.createSequentialGroup()
                .addGroup(pnlSpamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSpamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMinimizeSpam, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSpamLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSpamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterSpam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnListFriend2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(45, 45, 45))
        );

        data.add(pnlSpam, "card4");

        pnlActiveUser.setPreferredSize(new java.awt.Dimension(1002, 712));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("Manage Active User");

        closeBtn7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn7.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn7.setText("X");
        closeBtn7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn7MouseClicked(evt);
            }
        });

        btnMinimizeNewUser1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMinimizeNewUser1.setForeground(new java.awt.Color(204, 0, 0));
        btnMinimizeNewUser1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMinimizeNewUser1.setText("-");
        btnMinimizeNewUser1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMinimizeNewUser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizeNewUser1MouseClicked(evt);
            }
        });

        tblActiveUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Name"
            }
        ));
        tblActiveUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblActiveUserMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblActiveUser);

        cbFilterActiveUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterActiveUserActionPerformed(evt);
            }
        });

        jLabel35.setText("Filter by:");

        lbDate1.setText("`");

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        jButton3.setText("Select New Date");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlActiveUserLayout = new javax.swing.GroupLayout(pnlActiveUser);
        pnlActiveUser.setLayout(pnlActiveUserLayout);
        pnlActiveUserLayout.setHorizontalGroup(
            pnlActiveUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActiveUserLayout.createSequentialGroup()
                .addGroup(pnlActiveUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlActiveUserLayout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(jButton3))
                    .addGroup(pnlActiveUserLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(139, 139, 139)
                        .addComponent(lbActiveUser, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                .addGroup(pnlActiveUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlActiveUserLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(24, 24, 24)
                        .addComponent(lbDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMinimizeNewUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn7, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActiveUserLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cbFilterActiveUser, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfActiveUser, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77))))
        );
        pnlActiveUserLayout.setVerticalGroup(
            pnlActiveUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActiveUserLayout.createSequentialGroup()
                .addGroup(pnlActiveUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlActiveUserLayout.createSequentialGroup()
                        .addGroup(pnlActiveUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(closeBtn7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMinimizeNewUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlActiveUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDate1)
                            .addComponent(jLabel35))
                        .addGap(12, 12, 12)
                        .addGroup(pnlActiveUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbFilterActiveUser)
                            .addComponent(tfActiveUser))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pnlActiveUserLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbActiveUser, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(38, 38, 38)))
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        data.add(pnlActiveUser, "card3");

        pnlNewUser.setPreferredSize(new java.awt.Dimension(1002, 712));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Manage New User");

        closeBtn6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn6.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn6.setText("X");
        closeBtn6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn6MouseClicked(evt);
            }
        });

        btnMinimizeNewUser.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMinimizeNewUser.setForeground(new java.awt.Color(204, 0, 0));
        btnMinimizeNewUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMinimizeNewUser.setText("-");
        btnMinimizeNewUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMinimizeNewUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizeNewUserMouseClicked(evt);
            }
        });

        tblNewUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Date"
            }
        ));
        tblNewUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNewUserMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblNewUser);

        cbFilterNewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterNewUserActionPerformed(evt);
            }
        });

        jLabel20.setText("Filter by:");

        lbDate.setText("`");

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        jButton2.setText("Select New Date");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlNewUserLayout = new javax.swing.GroupLayout(pnlNewUser);
        pnlNewUser.setLayout(pnlNewUserLayout);
        pnlNewUserLayout.setHorizontalGroup(
            pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNewUserLayout.createSequentialGroup()
                .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNewUserLayout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(jButton2))
                    .addGroup(pnlNewUserLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(139, 139, 139)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 197, Short.MAX_VALUE)
                .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNewUserLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(24, 24, 24)
                        .addComponent(lbDate, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMinimizeNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn6, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNewUserLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cbFilterNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77))))
        );
        pnlNewUserLayout.setVerticalGroup(
            pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNewUserLayout.createSequentialGroup()
                .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlNewUserLayout.createSequentialGroup()
                        .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(closeBtn6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMinimizeNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDate)
                            .addComponent(jLabel20))
                        .addGap(12, 12, 12)
                        .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbFilterNewUser)
                            .addComponent(jTextField4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pnlNewUserLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(38, 38, 38)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        data.add(pnlNewUser, "card3");

        pnlMoreUserData.setPreferredSize(new java.awt.Dimension(1002, 712));
        pnlMoreUserData.setLayout(null);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("More User Data ");
        pnlMoreUserData.add(jLabel23);
        jLabel23.setBounds(6, 30, 150, 30);

        closeBtn10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn10.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn10.setText("X");
        closeBtn10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn10MouseClicked(evt);
            }
        });
        pnlMoreUserData.add(closeBtn10);
        closeBtn10.setBounds(978, 0, 18, 26);

        closeBtn11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn11.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn11.setText("-");
        closeBtn11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn11MouseClicked(evt);
            }
        });
        pnlMoreUserData.add(closeBtn11);
        closeBtn11.setBounds(954, 0, 18, 26);

        tblMoreUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Name", "Direct Friends", "Matual Friends", "First Login At"
            }
        ));
        tblMoreUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMoreUserMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblMoreUser);

        pnlMoreUserData.add(jScrollPane5);
        jScrollPane5.setBounds(0, 109, 1000, 508);

        cbFilterMoreUserInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterMoreUserInfoActionPerformed(evt);
            }
        });
        pnlMoreUserData.add(cbFilterMoreUserInfo);
        cbFilterMoreUserInfo.setBounds(656, 60, 120, 36);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setText("Filter by:");
        pnlMoreUserData.add(jLabel25);
        jLabel25.setBounds(656, 21, 110, 20);
        pnlMoreUserData.add(jTextField5);
        jTextField5.setBounds(794, 60, 102, 36);

        cbCompareNumber.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Greater than", "Less than", "Equal" }));
        cbCompareNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCompareNumberActionPerformed(evt);
            }
        });
        pnlMoreUserData.add(cbCompareNumber);
        cbCompareNumber.setBounds(794, 22, 102, 26);

        data.add(pnlMoreUserData, "card3");

        pnlStatiscal.setPreferredSize(new java.awt.Dimension(1002, 712));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setText("Statiscal");

        closeBtn8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn8.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn8.setText("X");
        closeBtn8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn8MouseClicked(evt);
            }
        });

        closeBtn9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn9.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn9.setText("-");
        closeBtn9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn9MouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setText("Enter year:");

        tfYear.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jButton1.setBackground(new java.awt.Color(0, 125, 73));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Line Graph");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Criteria:");

        javax.swing.GroupLayout pnlStatiscalLayout = new javax.swing.GroupLayout(pnlStatiscal);
        pnlStatiscal.setLayout(pnlStatiscalLayout);
        pnlStatiscalLayout.setHorizontalGroup(
            pnlStatiscalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatiscalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addGroup(pnlStatiscalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStatiscalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeBtn9, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn8, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStatiscalLayout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(pnlStatiscalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlStatiscalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(tfYear)
                            .addComponent(cbStatiscal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 563, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlStatiscalLayout.setVerticalGroup(
            pnlStatiscalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatiscalLayout.createSequentialGroup()
                .addGroup(pnlStatiscalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeBtn8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeBtn9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlStatiscalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfYear, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(30, 30, 30)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(pnlStatiscalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbStatiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(509, Short.MAX_VALUE))
        );

        data.add(pnlStatiscal, "card3");

        pnlHistoryLog.setPreferredSize(new java.awt.Dimension(1002, 712));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setText("History Log in");

        closeBtn14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn14.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn14.setText("X");
        closeBtn14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn14MouseClicked(evt);
            }
        });

        closeBtn15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn15.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn15.setText("-");
        closeBtn15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn15MouseClicked(evt);
            }
        });

        tblHistoryLogin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Datet"
            }
        ));
        tblHistoryLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHistoryLoginMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblHistoryLogin);

        cbFilterHistoryLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterHistoryLogActionPerformed(evt);
            }
        });

        jLabel36.setText("Filter by:");

        javax.swing.GroupLayout pnlHistoryLogLayout = new javax.swing.GroupLayout(pnlHistoryLog);
        pnlHistoryLog.setLayout(pnlHistoryLogLayout);
        pnlHistoryLogLayout.setHorizontalGroup(
            pnlHistoryLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistoryLogLayout.createSequentialGroup()
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlHistoryLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHistoryLogLayout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(258, 258, 258)
                        .addComponent(closeBtn15, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn14, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHistoryLogLayout.createSequentialGroup()
                        .addComponent(cbFilterHistoryLog, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)
        );
        pnlHistoryLogLayout.setVerticalGroup(
            pnlHistoryLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistoryLogLayout.createSequentialGroup()
                .addGroup(pnlHistoryLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHistoryLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeBtn15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHistoryLogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel36)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlHistoryLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterHistoryLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );

        data.add(pnlHistoryLog, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Navigate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(data, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Navigate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1213, 709));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public void updateFilter(JTextField textField, JComboBox cbFilter, JTable table) {
        String filterText = textField.getText();
        String filterCate = cbFilter.getSelectedItem().toString();
        int indices = 1;
        if ("Name".equals(filterCate)) {
            indices = 1;
        } else if ("User name".equals(filterCate)) {
            indices = 0;
        } else if ("Time report".equals(filterCate)) {
            indices = 3;

        } // Lock filter 
        else if ("Status".equals(filterCate)) {
            indices = 6;
        } else if ("Direct Friends".equals(filterCate)) {
            String typeCompare = (String) cbCompareNumber.getSelectedItem();
            RowFilter.ComparisonType typeFilter = RowFilter.ComparisonType.EQUAL;
            switch (typeCompare) {
                case "Greater than":
                    typeFilter = RowFilter.ComparisonType.AFTER;
                    break;
                case "Less than":
                    typeFilter = RowFilter.ComparisonType.BEFORE;
                    break;
                case "Equal":
                    typeFilter = RowFilter.ComparisonType.EQUAL;
                    break;
                default:
                    break;
            }

            try {
                int filterValue = Integer.parseInt(filterText);
                RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.numberFilter(
                        typeFilter, filterValue, 2);
                ((TableRowSorter<DefaultTableModel>) table.getRowSorter()).setRowFilter(rowFilter);
            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
//                ex.printStackTrace();
            }
            return;
        }

        // STATUS HERE
//        else if()
//        {
//        
//        }
        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + filterText, indices);

        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
        sorter.setRowFilter(rowFilter);
    }
    private void closeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtnMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closeBtnMouseClicked

    private void closeBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn1MouseClicked
        // TODO add your handling code here:
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_closeBtn1MouseClicked

    private void btnAddUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddUserMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btnAddUserMouseClicked

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        AddUserForm addForm = new AddUserForm();
        addForm.setVisible(true);
        addForm.pack();
        addForm.setLocationRelativeTo(null);
        addForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        int index;
        index = tblUser.getSelectedRow();
        if (index == -1) {
            return;
        }

        String lock = model.getValueAt(index, 6).toString();
        if (lock == "true") {
            btnLockUser.setText("UNLOCK");
        } else if (lock == "false") {
            btnLockUser.setText("LOCK");
        }
    }//GEN-LAST:event_tblUserMouseClicked

    private void btnUpdateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateUserActionPerformed
        int index;
        try {
            index = tblUser.getSelectedRow();
            AddUserForm addForm = new AddUserForm(model.getValueAt(index, 0).toString(), model.getValueAt(index, 1).toString(),
                    model.getValueAt(index, 2).toString(), model.getValueAt(index, 3).toString(),
                    model.getValueAt(index, 4).toString(), model.getValueAt(index, 5).toString(),
                    model.getValueAt(index, 6).toString(), "Update User", "UPDATE", index);
            addForm.setVisible(true);
            addForm.pack();
            addForm.setLocationRelativeTo(null);
            addForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "You must select user before update", "WARNING", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateUserActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        int index;
        index = tblUser.getSelectedRow();
        String msg = null;
        if (index != -1) {
            int result = JOptionPane.showConfirmDialog((Component) null, "Are you sure?",
                    "alert", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                try {

                    // Call delete
                    Result rs = Client.Client.api_c.invoke_api("AdminService", "del_user",
                            Client.Client.token, tblUser.getValueAt(index, 0));
                    if (rs instanceof ResultError err) {
                        msg = err.msg();
                        System.out.println(err.msg());
                    } else {
                        msg = "Selected row deleted successfully";
                        model.removeRow(tblUser.getSelectedRow());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null, msg);
            }
        } else {
            JOptionPane.showMessageDialog(null, "You must select user before delete", "WARNING", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void txtMangeGroupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMangeGroupMouseClicked
        String token = Client.Client.token;
        data.removeAll();
        data.repaint();
        data.revalidate();
        modelGroup = (DefaultTableModel) tblGroup.getModel();
        modelGroup.setRowCount(0);

        try {
            Result rs = Client.Client.api_c.invoke_api("AdminService", "list_groups", token);
            if (rs instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (rs instanceof ResultOk ok) {
                ArrayList<GroupChatInfo> group_list = (ArrayList<GroupChatInfo>) ok.data();
                if (group_list == null) {
                    JOptionPane.showMessageDialog(null, "Error for connect to server");
                    return;
                }
                for (var group : group_list) {
                    addRowtoTable(new Object[]{group.id, group.name, group.created_date}, false, modelGroup);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

        initSorter(modelGroup, tblGroup);
        initFilter(jTextField2, cbFilterGroup, tblGroup);

        data.add(pnlGroup);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_txtMangeGroupMouseClicked


    private void cbFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterActionPerformed
        // TODO add your handling code here:
//        JOptionPane.showMessageDialog(null, cbFilter.getSelectedItem());
    }//GEN-LAST:event_cbFilterActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        data.removeAll();
        data.repaint();
        data.revalidate();
        data.add(pnlUser);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void tblSpamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSpamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSpamMouseClicked

    private void closeBtn4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn4MouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeBtn4MouseClicked

    private void btnMinimizeSpamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeSpamMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_btnMinimizeSpamMouseClicked

    private void cbFilterSpamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterSpamActionPerformed

    }//GEN-LAST:event_cbFilterSpamActionPerformed

    private void txtSpamListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSpamListMouseClicked
        String token = Client.Client.token;
        data.removeAll();
        data.repaint();
        data.revalidate();
        modelSpam = (DefaultTableModel) tblSpam.getModel();
        modelSpam.setRowCount(0);
        try {
            Result rs = Client.Client.api_c.invoke_api("AdminService", "list_spam_reports", token);
            if (rs instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (rs instanceof ResultOk ok) {
                ArrayList<SpamReport> spam_list = (ArrayList<SpamReport>) ok.data();
                if (spam_list == null) {
                    JOptionPane.showMessageDialog(null, "Error while connecting to server");
                    return;
                }
                for (var spam : spam_list) {
                    addRowtoTable(new Object[]{spam.reporter, spam.target, spam.reason, spam.date}, false, modelSpam);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

        initSorter(modelSpam, tblSpam);
        initFilter(jTextField3, cbFilterSpam, tblSpam);

        data.add(pnlSpam);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_txtSpamListMouseClicked

    private String start;
    private String end;


    private void txtManageNewUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManageNewUserMouseClicked
        new DateSelectionForm(this);
    }//GEN-LAST:event_txtManageNewUserMouseClicked

    public void onDateSelected(Date start, Date end) {
        data.removeAll();
        data.repaint();
        data.revalidate();
        modelNewUser = (DefaultTableModel) tblNewUser.getModel();
        modelNewUser.setRowCount(0);
        String token = Client.Client.token;
        Result rs = null;
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            rs = Client.Client.api_c.invoke_api("AdminService", "list_registers", token, start, end);
        } catch (IOException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (rs instanceof ResultError err) {
            JOptionPane.showMessageDialog(null, err.msg());
        } else if (rs instanceof ResultOk ok) {
            ArrayList<RegistrationRecord> record_list = (ArrayList<RegistrationRecord>) ok.data();
            if (record_list == null) {
                JOptionPane.showMessageDialog(null, "Error while connecting to server");
                return;
            }
            for (var record : record_list) {
                String formatRecord = formatDate.format(record.ts);
                addRowtoTable(new Object[]{record.username, formatRecord}, false, modelNewUser);
            }
        }

        String formatStart = formatDate.format(start);
        String formatEnd = formatDate.format(end);
        jLabel13.setText("Filter user From: " + formatStart + " To: " + formatEnd);
        initSorter(modelNewUser, tblNewUser);
        initFilter(jTextField4, cbFilterNewUser, tblNewUser);
        data.add(pnlNewUser);
        data.repaint();
        data.revalidate();
    }

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        data.removeAll();
        data.repaint();
        data.revalidate();
        data.add(pnlStatiscal);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void closeBtn6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn6MouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeBtn6MouseClicked

    private void btnMinimizeNewUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeNewUserMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_btnMinimizeNewUserMouseClicked

    private void tblNewUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNewUserMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblNewUserMouseClicked

    private void cbFilterNewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterNewUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFilterNewUserActionPerformed

    private void closeBtn8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn8MouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeBtn8MouseClicked

    private void closeBtn9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn9MouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_closeBtn9MouseClicked

//  LINE GRAPH 
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        if ("".equals(tfYear.getText())) {
            JOptionPane.showMessageDialog(null, "You must select year before draw Graph", "WARNING", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int year = 0;
        try {
            year = Integer.parseInt(tfYear.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid year !!!");
            return;
        }

        int currentYear = Year.now().getValue();
        int sinceYear = 2023;
        if (year > currentYear) {
            JOptionPane.showMessageDialog(null, "Invalid year !!! Current year is " + currentYear, "WARNING", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (year < 2023) {
            JOptionPane.showMessageDialog(null, "Invalid year !!! Release year is " + sinceYear, "WARNING", JOptionPane.WARNING_MESSAGE);
            return;

        }
        String token = Client.Client.token;
        String criteria = (String) cbStatiscal.getSelectedItem();
        ArrayList<Integer> data = new ArrayList<>();
        if (criteria.equals("New User Graph")) {
            data = CallAPI.getUserEachMonthinYear(year, token, "new");
        } else if (criteria.equals("Active User Graph")) {
            data = CallAPI.getUserEachMonthinYear(year, token, "active");
        }
        if (data == null) {
            System.out.println("Data is null !!! Please chek the server");
            return;
        }
        GraphPanel.createAndShowGui(data);
    }//GEN-LAST:event_jButton1MouseClicked

    private void closeBtn10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn10MouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeBtn10MouseClicked

    private void closeBtn11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn11MouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_closeBtn11MouseClicked

    private void tblMoreUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMoreUserMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMoreUserMouseClicked

    private void cbFilterMoreUserInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterMoreUserInfoActionPerformed
        String cate = cbFilterMoreUserInfo.getSelectedItem().toString();
        if (cate.equals("Name")) {
            cbCompareNumber.setVisible(false);
        } else if (cate.equals("Direct Friends")) {
            cbCompareNumber.setVisible(true);
        }
    }//GEN-LAST:event_cbFilterMoreUserInfoActionPerformed
    public void onDateSelectedForActiveUser(Date start, Date end) {
        data.removeAll();
        data.repaint();
        data.revalidate();
        modelActiveUser = (DefaultTableModel) tblActiveUser.getModel();
        modelActiveUser.setRowCount(0);
        String token = Client.Client.token;
        Result rs = null;
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<UserInfo> list_active_users = CallAPI.list_active_users(token, start, end);
        if (list_active_users == null) {
            JOptionPane.showMessageDialog(null, "Error while connecting to server");
            return;
        }
        for (var user : list_active_users) {
            addRowtoTable(new Object[]{user.username, user.fullname}, false, modelActiveUser);
        }
        String formatStart = formatDate.format(start);
        String formatEnd = formatDate.format(end);
        lbActiveUser.setText("Filter user From: " + formatStart + " To: " + formatEnd);
        initSorter(modelActiveUser, tblActiveUser);
        initFilter(tfActiveUser, cbFilterActiveUser, tblActiveUser);

        data.add(pnlActiveUser);
        data.repaint();
        data.revalidate();
    }

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        new DateSelectionForm(this, 1);
    }//GEN-LAST:event_jLabel29MouseClicked

    private void txtManageNewUser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManageNewUser1MouseClicked
        data.removeAll();
        data.repaint();
        data.revalidate();
        DefaultTableModel moreInfoModel = (DefaultTableModel) tblMoreUser.getModel();
        moreInfoModel.setRowCount(0);
        String msg = "";

        try {
            Result rs = Client.Client.api_c.invoke_api("AdminService", "list_users", Client.Client.token);
            if (rs instanceof ResultError err) {
                msg = err.msg();
            } else if (rs instanceof ResultOk ok) {
                ArrayList<Pair<UserInfo, Boolean>> user_list = (ArrayList<Pair<UserInfo, Boolean>>) ok.data();
                for (var item : user_list) {
                    var user = item.a;
                    int amount_friend = 0;
                    int amount_matual_friend = 0;
                    //GET First log in Time
                    Date first_LogIn = CallAPI.getFirstLoginTime(Client.Client.token, user.username);
                    if (first_LogIn == null) {
                        System.out.println("Cant get first Login time");
                    } else {
                        // GET Getfriend, FOAF
                        Result rsFriend = Client.Client.api_c.invoke_api("AdminService", "list_user_friends", Client.Client.token, user.username);
                        if (rsFriend instanceof ResultError err) {
                            msg = err.msg();
                        } else if (rsFriend instanceof ResultOk ok1) {
                            ArrayList<UserInfo> friend_list = (ArrayList<UserInfo>) ok1.data();
                            amount_friend = friend_list.size();
                            for (var friend : friend_list) {
                                Result rsFriendoFriend = Client.Client.api_c.invoke_api("AdminService", "list_user_friends", Client.Client.token, friend.username);
                                if (rsFriendoFriend instanceof ResultError err) {
                                    msg = err.msg();
                                } else if (rsFriendoFriend instanceof ResultOk ok2) {
                                    ArrayList<UserInfo> friend_of_friend_list = (ArrayList<UserInfo>) ok2.data();
                                    amount_matual_friend += friend_of_friend_list.size();
                                }
                            }
                        }

                        moreInfoModel.addRow(new Object[]{user.username, user.fullname, amount_friend, amount_matual_friend, first_LogIn.toString()});
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        if (msg != "") {
            JOptionPane.showMessageDialog(null, msg);
        }
        initSorter(moreInfoModel, tblMoreUser);
        initFilter(jTextField5, cbFilterMoreUserInfo, tblMoreUser);
        data.add(pnlMoreUserData);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_txtManageNewUser1MouseClicked

    private void btnLockUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLockUserMouseClicked
        // Handle Lock User 
        String functional = btnLockUser.getText().toString();

        String token = Client.Client.token;
        String msg = "";
        int index = tblUser.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "You must select user before action", "WARNING", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = (String) model.getValueAt(index, 0);
        Result rs = null;
        try {
            if ("LOCK".equals(functional)) {
                rs = Client.Client.api_c.invoke_api("AdminService", "lock_user", token, username);
            } else if ("UNLOCK".equals(functional)) {
                rs = Client.Client.api_c.invoke_api("AdminService", "unlock_user", token, username);
            }

            if (rs instanceof ResultError err) {
                msg = err.msg();
                JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (rs instanceof ResultOk ok) {
                if ("LOCK".equals(functional)) {
                    msg = "Lock user successfully";
                    tblUser.setValueAt(true, index, 6);
                } else if ("UNLOCK".equals(functional)) {
                    msg = "Un Lock user successfully";
                    tblUser.setValueAt(false, index, 6);
                }

                JOptionPane.showMessageDialog(null, msg, "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLockUserMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void btnListFriendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnListFriendMouseClicked
        // List friend of user 
        final String token = Client.Client.token;
        final String username = (String) tblUser.getValueAt(tblUser.getSelectedRow(), 0);
        try {
            Result rs = Client.Client.api_c.invoke_api("AdminService", "list_user_friends", token, username);
            if (rs instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, "Can't not show list friends of this user " + err.msg());
            } else if (rs instanceof ResultOk ok) {
                ArrayList<UserInfo> user_list = (ArrayList<UserInfo>) ok.data();
                if (user_list == null) {
                    JOptionPane.showMessageDialog(null, "User does'nt have friends");
                    return;
                }
                if (user_list.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "This user doesn't have friends", "INFOMATION", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
//                new DisplayTable(user_list, new String[]{"User name", "Name", "Address", "Date of Birth", "Gender", "Email"}).setVisible(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnListFriendMouseClicked

    private void btnUpdateUserPwd1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateUserPwd1MouseClicked
        final String token = Client.Client.token;
        final String username = (String) tblUser.getValueAt(tblUser.getSelectedRow(), 0);

        JFrame newPwdFrame = new JFrame();
        newPwdFrame.setTitle("New Password Frame");

        newPwdFrame.setLayout(new GridLayout(3, 2));

        JLabel label = new JLabel("New Password:");
        newPwdFrame.add(label);

        JPasswordField newPasswordField = new JPasswordField();
        newPwdFrame.add(newPasswordField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = new String(newPasswordField.getPassword());
                String msg = "";
                try {
                    Result rs = Client.Client.api_c.invoke_api("AdminService", "change_user_pass", token, username, newPassword);
                    if (rs instanceof ResultError err) {
                        msg = err.msg();
                    } else if (rs instanceof ResultOk ok) {
                        msg = "Password updated to: " + newPassword;
                    }
                    JOptionPane.showMessageDialog(null, msg);
                    newPwdFrame.dispose();
                } catch (IOException ex) {
                    Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        newPwdFrame.add(updateButton);
        newPwdFrame.setSize(350, 150);

        newPwdFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        newPwdFrame.setLocationRelativeTo(null);
        newPwdFrame.setVisible(true);
        newPwdFrame.setResizable(false);


    }//GEN-LAST:event_btnUpdateUserPwd1MouseClicked

    private void btnSeeLogHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSeeLogHistoryMouseClicked
        // See Log history
        final String token = Client.Client.token;
        final String username = (String) tblUser.getValueAt(tblUser.getSelectedRow(), 0);

        ArrayList<Date> login_Records = CallAPI.getLogInHistory(token, username);
        if (login_Records.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Some thing went wrong in See Log History", "INFOMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
//        new DisplayTable(login_Records, new String[]{"Date"}, username).setVisible(true);
    }//GEN-LAST:event_btnSeeLogHistoryMouseClicked

    private void cbFilterGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterGroupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFilterGroupActionPerformed

    private void tblGroupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGroupMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGroupMouseClicked

    private void btnMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_btnMinimizeMouseClicked

    private void closeBtn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn2MouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeBtn2MouseClicked

    private void btnListAdminGroupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnListAdminGroupMouseClicked
        final String token = Client.Client.token;
        int index = tblGroup.getSelectedRow();
        if (index == -1) {
            JOptionPane.showConfirmDialog(null, "You must select Group before this action", "WARNING", JOptionPane.WARNING_MESSAGE);
            return;
        }
        final String group_id = (String) tblGroup.getValueAt(tblGroup.getSelectedRow(), 0);

        ArrayList<GroupChatMemberInfo> list_log = CallAPI.list_group_members(token, group_id);
        for (int i = 0; i < list_log.size(); ++i) {
            if (list_log.get(i).is_admin == false) {
                list_log.remove(i);
            }
        }
//        new DisplayTable(list_log).setVisible(true);
    }//GEN-LAST:event_btnListAdminGroupMouseClicked

    private void btnListMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnListMemberMouseClicked
        final String token = Client.Client.token;
        int index = tblGroup.getSelectedRow();
        if (index == -1) {
            JOptionPane.showConfirmDialog(null, "You must select Group before this action", "WARNING", JOptionPane.WARNING_MESSAGE);
            return;
        }
        final String group_id = (String) tblGroup.getValueAt(tblGroup.getSelectedRow(), 0);
        ArrayList<GroupChatMemberInfo> list_log = CallAPI.list_group_members(token, group_id);
//        new DisplayTable(list_log).setVisible(true);
    }//GEN-LAST:event_btnListMemberMouseClicked

    private void btnListFriend2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListFriend2ActionPerformed
    }//GEN-LAST:event_btnListFriend2ActionPerformed

    private void cbCompareNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCompareNumberActionPerformed
        String cate = cbCompareNumber.getSelectedItem().toString();
        updateFilter(jTextField5, cbFilterMoreUserInfo, tblMoreUser);
    }//GEN-LAST:event_cbCompareNumberActionPerformed

    private void btnListFriend2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnListFriend2MouseClicked
        final String token = Client.Client.token;
        int index = tblSpam.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "You must select user before lock");
            return;
        } else {
            String username = (String) modelSpam.getValueAt(index, 1);
            CallAPI.lockUser(token, username);
        }
    }//GEN-LAST:event_btnListFriend2MouseClicked

    private void txtManageNewUser1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManageNewUser1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtManageNewUser1MouseEntered

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        new DateSelectionForm(this);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked

        data.removeAll();
        data.repaint();
        data.revalidate();
        modelHistoryLogin = (DefaultTableModel) tblHistoryLogin.getModel();
        ArrayList<LoginRecord> login_list = CallAPI.get_login_log(Client.Client.token);
        for (var login : login_list) {
            addRowtoTable(new Object[]{login.username, login.ts}, false, modelHistoryLogin);
        }
        initSorter(modelHistoryLogin, tblHistoryLogin);
        initFilter(jTextField6, cbFilterHistoryLog, tblHistoryLogin);
        data.add(pnlHistoryLog);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_jLabel32MouseClicked

    private void closeBtn14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn14MouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeBtn14MouseClicked

    private void closeBtn15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn15MouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeBtn15MouseClicked

    private void tblHistoryLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHistoryLoginMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHistoryLoginMouseClicked

    private void cbFilterHistoryLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterHistoryLogActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFilterHistoryLogActionPerformed

    private void closeBtn7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn7MouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeBtn7MouseClicked

    private void btnMinimizeNewUser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeNewUser1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMinimizeNewUser1MouseClicked

    private void tblActiveUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblActiveUserMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblActiveUserMouseClicked

    private void cbFilterActiveUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterActiveUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFilterActiveUserActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        new DateSelectionForm(this, 1);
    }//GEN-LAST:event_jButton3MouseClicked

    /**
     *
     * @param dataRow
     * @param lock
     * @param model1
     * @param model
     */
    public static void addRowtoTable(Object[] dataRow, boolean lock, DefaultTableModel model) {
        model.addRow(dataRow);
    }

    public static void updateRow(Object[] newDataRow, int rowIndex) {
        for (int i = 0; i < model.getColumnCount(); ++i) {
            model.setValueAt(newDataRow[i], rowIndex, i);
        }
    }

    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Navigate;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnListAdminGroup;
    private javax.swing.JButton btnListFriend;
    private javax.swing.JButton btnListFriend2;
    private javax.swing.JButton btnListMember;
    private javax.swing.JButton btnLockUser;
    private javax.swing.JLabel btnMinimize;
    private javax.swing.JLabel btnMinimizeNewUser;
    private javax.swing.JLabel btnMinimizeNewUser1;
    private javax.swing.JLabel btnMinimizeSpam;
    private javax.swing.JButton btnSeeLogHistory;
    private javax.swing.JButton btnUpdateUser;
    private javax.swing.JButton btnUpdateUserPwd1;
    private javax.swing.JComboBox<String> cbCompareNumber;
    private javax.swing.JComboBox<String> cbFilter;
    private javax.swing.JComboBox<String> cbFilterActiveUser;
    private javax.swing.JComboBox<String> cbFilterGroup;
    private javax.swing.JComboBox<String> cbFilterHistoryLog;
    private javax.swing.JComboBox<String> cbFilterMoreUserInfo;
    private javax.swing.JComboBox<String> cbFilterNewUser;
    private javax.swing.JComboBox<String> cbFilterSpam;
    private javax.swing.JComboBox<String> cbStatiscal;
    private javax.swing.JLabel closeBtn;
    private javax.swing.JLabel closeBtn1;
    private javax.swing.JLabel closeBtn10;
    private javax.swing.JLabel closeBtn11;
    private javax.swing.JLabel closeBtn14;
    private javax.swing.JLabel closeBtn15;
    private javax.swing.JLabel closeBtn2;
    private javax.swing.JLabel closeBtn4;
    private javax.swing.JLabel closeBtn6;
    private javax.swing.JLabel closeBtn7;
    private javax.swing.JLabel closeBtn8;
    private javax.swing.JLabel closeBtn9;
    private javax.swing.JPanel data;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JLabel lbActiveUser;
    private javax.swing.JLabel lbDate;
    private javax.swing.JLabel lbDate1;
    private javax.swing.JPanel pnlActiveUser;
    private javax.swing.JPanel pnlGroup;
    private javax.swing.JPanel pnlHistoryLog;
    private javax.swing.JPanel pnlMoreUserData;
    private javax.swing.JPanel pnlNewUser;
    private javax.swing.JPanel pnlSpam;
    private javax.swing.JPanel pnlStatiscal;
    private javax.swing.JPanel pnlUser;
    private static javax.swing.JTable tblActiveUser;
    private static javax.swing.JTable tblGroup;
    private static javax.swing.JTable tblHistoryLogin;
    private static javax.swing.JTable tblMoreUser;
    private static javax.swing.JTable tblNewUser;
    private static javax.swing.JTable tblSpam;
    private static javax.swing.JTable tblUser;
    private javax.swing.JTextField tfActiveUser;
    private javax.swing.JTextField tfYear;
    private javax.swing.JLabel txtManageNewUser;
    private javax.swing.JLabel txtManageNewUser1;
    private javax.swing.JLabel txtMangeGroup;
    private javax.swing.JLabel txtSpamList;
    // End of variables declaration//GEN-END:variables
}
