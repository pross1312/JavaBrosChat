package view;

import Utils.GroupChatInfo;
import Utils.GroupChatMemberInfo;
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
import static java.lang.ProcessBuilder.Redirect.to;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import static java.util.Date.from;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import view.Component.DateListFrame;
import view.Component.DateSelectionForm;
import view.Component.GroupChatMemberTable;
import view.Component.NewJPanel;
import view.Interface.DateSelectionListener;
import view.Sync.SyncObject;

public class AdminDashboard extends javax.swing.JFrame implements DateSelectionListener {

    public static DefaultTableModel model;
    public static DefaultTableModel modelGroup;
    public static DefaultTableModel modelSpam;
    public static DefaultTableModel modelNewUser;
    private SyncObject syncObject;

    public static DateSelectionListener dateSelectionListener;

    public AdminDashboard() {
        //TODO Get list of user from db and display in table. 
        initComponents();
        model = (DefaultTableModel) tblUser.getModel();

        //Filtering 
        cbFilter.addItem("Name");
        cbFilter.addItem("User name");
        cbFilter.addItem("Status");

        //Filter Group 
        cbFilterGroup.addItem("Name");

        //Filter Spam
        cbFilterSpam.addItem("Username");
        cbFilterSpam.addItem("Time report");

        //Filter New User 
        cbFilterNewUser.addItem("Name");

        cbStatiscal.addItem("New User Graph");
        cbStatiscal.addItem("Active User Graph");

        //Filter More User Info  
        cbFilterSpam.addItem("Name");
        cbFilterSpam.addItem("Direct Friends");

        //Filter Active User Info  
        cbFilterActiveUser.addItem("Name");
        cbFilterActiveUser.addItem("Times Log in");

        //Sorting Active User Info 
        cbSortActiveUser.addItem("Created Time");
        cbSortActiveUser.addItem("Name");
        String msg;
        try {
            Result rs = Client.Client.api_c.invoke_api("AdminService", "list_users", Client.Client.token);
            if (rs instanceof ResultError err) {
                msg = err.msg();
            } else if (rs instanceof ResultOk ok) {
                ArrayList<Pair<UserInfo, Boolean>> user_list = (ArrayList<Pair<UserInfo, Boolean>>) ok.data();
                for (var item : user_list) {
                    var user = item.a;
                    var lock = item.b;
                    addRowtoTable(new Object[]{user.username, user.fullname, user.address, user.birthdate.toString(),
                        user.gender.toString(), user.email, lock}, lock, model);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }

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
        data = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        closeBtn = new javax.swing.JLabel();
        closeBtn1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        btnAddUser = new javax.swing.JButton();
        btnUpdateUser = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        btnLockUser = new javax.swing.JButton();
        btnUpdateUserPwd = new javax.swing.JButton();
        btnListFriend = new javax.swing.JButton();
        btnUpdateUserPwd1 = new javax.swing.JButton();
        cbFilter = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSpam = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        btnListFriend2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        closeBtn4 = new javax.swing.JLabel();
        btnMinimizeSpam = new javax.swing.JLabel();
        cbFilterSpam = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        closeBtn6 = new javax.swing.JLabel();
        btnMinimizeNewUser = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNewUser = new javax.swing.JTable();
        cbFilterNewUser = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        lbDate = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        closeBtn10 = new javax.swing.JLabel();
        closeBtn11 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblUser4 = new javax.swing.JTable();
        cbSortMoreUserInfo = new javax.swing.JComboBox<>();
        cbFilterMoreUserInfo = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        closeBtn8 = new javax.swing.JLabel();
        closeBtn9 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        tfYear = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        cbStatiscal = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        closeBtn12 = new javax.swing.JLabel();
        closeBtn13 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblUser5 = new javax.swing.JTable();
        cbSortActiveUser = new javax.swing.JComboBox<>();
        cbFilterActiveUser = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();

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

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-more-32 (1).png"))); // NOI18N

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
        });

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
                        .addGap(6, 6, 6)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        data.setLayout(new java.awt.CardLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(1002, 712));

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
                "Username", "Name", "Address", "Date of Birth", "Gender", "Email", "Lock"
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

        btnUpdateUserPwd.setText("SEE LOG");
        btnUpdateUserPwd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateUserPwdMouseClicked(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 185, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(btnUpdateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnLockUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnListFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(btnUpdateUserPwd1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnUpdateUserPwd, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 268, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(244, 244, 244)
                                .addComponent(closeBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddUser)
                    .addComponent(btnUpdateUser)
                    .addComponent(btnDeleteUser)
                    .addComponent(btnLockUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnListFriend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdateUserPwd1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateUserPwd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );

        data.add(jPanel2, "card3");

        jPanel3.setPreferredSize(new java.awt.Dimension(1002, 712));

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addComponent(btnListAdminGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnListMember, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 531, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(cbFilterGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94))))
            .addComponent(jScrollPane2)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbFilterGroup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnListMember, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListAdminGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43))
        );

        data.add(jPanel3, "card3");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(187, 187, 187)
                        .addComponent(btnMinimizeSpam, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbFilterSpam, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(397, 397, 397)
                .addComponent(btnListFriend2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(451, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMinimizeSpam, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterSpam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnListFriend2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(45, 45, 45))
        );

        data.add(jPanel1, "card4");

        jPanel6.setPreferredSize(new java.awt.Dimension(1002, 712));

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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addGap(182, 182, 182)
                .addComponent(lbDate, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(289, 289, 289)
                        .addComponent(btnMinimizeNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn6, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(cbFilterNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jScrollPane4)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMinimizeNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDate))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))
        );

        data.add(jPanel6, "card3");

        jPanel4.setPreferredSize(new java.awt.Dimension(1002, 712));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("More User Data ");

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

        tblUser4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Name", "Direct Friends", "Relative Friends"
            }
        ));
        tblUser4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUser4MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblUser4);

        cbSortMoreUserInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortMoreUserInfoActionPerformed(evt);
            }
        });

        cbFilterMoreUserInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterMoreUserInfoActionPerformed(evt);
            }
        });

        jLabel24.setText("Sort by: ");

        jLabel25.setText("Filter by:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 486, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbSortMoreUserInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(172, 172, 172)
                        .addComponent(closeBtn11, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbFilterMoreUserInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeBtn11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSortMoreUserInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterMoreUserInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );

        data.add(jPanel4, "card3");

        jPanel7.setPreferredSize(new java.awt.Dimension(1002, 712));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
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

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("Enter year:");

        tfYear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Line Graph");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeBtn9, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn8, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(tfYear, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(28, 28, 28)
                                .addComponent(cbStatiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 531, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeBtn8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeBtn9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbStatiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))))
                .addContainerGap(594, Short.MAX_VALUE))
        );

        data.add(jPanel7, "card3");

        jPanel5.setPreferredSize(new java.awt.Dimension(1002, 712));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setText("Active User Infomation");

        closeBtn12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn12.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn12.setText("X");
        closeBtn12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn12MouseClicked(evt);
            }
        });

        closeBtn13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn13.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn13.setText("-");
        closeBtn13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn13MouseClicked(evt);
            }
        });

        tblUser5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Name", "Open App", "Chat with", "Group Joined"
            }
        ));
        tblUser5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUser5MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblUser5);

        cbSortActiveUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortActiveUserActionPerformed(evt);
            }
        });

        cbFilterActiveUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterActiveUserActionPerformed(evt);
            }
        });

        jLabel27.setText("Sort by: ");

        jLabel28.setText("Filter by:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 437, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(cbSortActiveUser, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(162, 162, 162)
                        .addComponent(closeBtn13, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn12, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbFilterActiveUser, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jScrollPane6)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeBtn13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(jLabel27))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSortActiveUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterActiveUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );

        data.add(jPanel5, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Navigate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(data, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Navigate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(data, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        data.add(jPanel3);
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
        data.add(jPanel2);
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

        data.add(jPanel1);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_txtSpamListMouseClicked

    private String start;
    private String end;

    public void onDateSelected(Date startDate, Date endDate) {
        // Xử lý ngày đã chọn từ DateSelectionForm
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.start = dateFormat.format(startDate);
        this.end = dateFormat.format(endDate);

        lbDate.setText("Filter from " + start + " to " + end);
        System.out.println("Ngày bắt đầu: " + start);
        System.out.println("Ngày kết thúc: " + end);
    }


    private void txtManageNewUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManageNewUserMouseClicked
        data.removeAll();
        data.repaint();
        data.revalidate();
        syncObject = new SyncObject();
        dateSelectionListener = this;
        Runnable myRunnable = new Runnable() {
            public void run() {
                DateSelectionForm dateSelectionForm = new DateSelectionForm(AdminDashboard.dateSelectionListener, syncObject);
            }
        };
        
        Thread thread = new Thread(myRunnable);
        thread.start();

        try {
            // Wait Date Select 
            syncObject.waitForDateSelection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String token = Client.Client.token;
        modelNewUser = (DefaultTableModel) tblNewUser.getModel();

        // Manage new user 
        try {
            Result rs = Client.Client.api_c.invoke_api("AdminService", "list_registers", token);
            if (rs instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (rs instanceof ResultOk ok) {
                ArrayList<RegistrationRecord> record_list = (ArrayList<RegistrationRecord>) ok.data();
                if (record_list == null) {
                    JOptionPane.showMessageDialog(null, "Error while connecting to server");
                    return;
                }
                for (var record : record_list) {
                    addRowtoTable(new Object[]{record.username, record.date}, false, modelNewUser);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

        initSorter(modelNewUser, tblNewUser);
        initFilter(jTextField4, cbFilterNewUser, tblNewUser);

        data.add(jPanel6);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_txtManageNewUserMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        data.removeAll();
        data.repaint();
        data.revalidate();
        data.add(jPanel7);
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
        final int year = Integer.parseInt(tfYear.getText());
        String token = Client.Client.token;

        ArrayList<Integer> list_amount_users = new ArrayList<>();
        for (int i = 1; i <= 11; ++i) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, i - 1, 1);

            int end_of_Month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            Date from = new Date(year, i, 1);
            Date to = new Date(year, i + 1, end_of_Month);
            try {
                Result rs = Client.Client.api_c.invoke_api("AdminService", "list_registers", token, from, to);
                if (rs instanceof ResultError err) {
                    JOptionPane.showMessageDialog(null, err.msg());
                } else if (rs instanceof ResultOk ok) {
                    ArrayList<RegistrationRecord> record_list = (ArrayList<RegistrationRecord>) ok.data();
                    if (record_list == null) {
                        JOptionPane.showMessageDialog(null, "Error while connecting to server");
                        return;
                    }
                    int amount_of_User = record_list.size();
                    list_amount_users.add(amount_of_User);
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        GraphPanel.createAndShowGui(list_amount_users);


    }//GEN-LAST:event_jButton1MouseClicked

    private void closeBtn10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn10MouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeBtn10MouseClicked

    private void closeBtn11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn11MouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_closeBtn11MouseClicked

    private void tblUser4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUser4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUser4MouseClicked

    private void cbSortMoreUserInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortMoreUserInfoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSortMoreUserInfoActionPerformed

    private void cbFilterMoreUserInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterMoreUserInfoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFilterMoreUserInfoActionPerformed

    private void closeBtn12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn12MouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeBtn12MouseClicked

    private void closeBtn13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn13MouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_closeBtn13MouseClicked

    private void tblUser5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUser5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUser5MouseClicked

    private void cbSortActiveUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortActiveUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSortActiveUserActionPerformed

    private void cbFilterActiveUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterActiveUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFilterActiveUserActionPerformed

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        data.removeAll();
        data.repaint();
        data.revalidate();
        data.add(jPanel5);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void txtManageNewUser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManageNewUser1MouseClicked
        data.removeAll();
        data.repaint();
        data.revalidate();
        data.add(jPanel4);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_txtManageNewUser1MouseClicked

    private void btnLockUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLockUserMouseClicked
        // Handle Lock User 
        String token = Client.Client.token;
        String msg = "";
        int index = tblUser.getSelectedRow();
        Boolean lock = (Boolean) tblUser.getValueAt(index, 6);
        if (lock == true) {
            msg = "User already lock";
            JOptionPane.showMessageDialog(null, msg, "INFO", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String username = (String) model.getValueAt(index, 0);
        Result rs;
        try {
            rs = Client.Client.api_c.invoke_api("AdminService", "lock_user", token, username);
            if (rs instanceof ResultError err) {
                msg = err.msg();
                JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (rs instanceof ResultOk ok) {
                msg = "Lock User Successfully";
                tblUser.setValueAt(true, index, 6);
                JOptionPane.showMessageDialog(null, msg, "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLockUserMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void btnListFriendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnListFriendMouseClicked
        // LIST friend of user 
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
                for (var user : user_list) {
                    JPanel list_friends = new NewJPanel();
                    list_friends.setVisible(true);
                    NewJPanel.addRowtoTable(new Object[]{user.username, user.fullname,
                        user.gender.toString(), user.email});
                }
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

    private void btnUpdateUserPwdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateUserPwdMouseClicked
        // See Log history
        final String token = Client.Client.token;
        final String username = (String) tblUser.getValueAt(tblUser.getSelectedRow(), 0);

        try {
            Result rs = Client.Client.api_c.invoke_api("AdminService", "get_login_log", token, username);
            if (rs instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (rs instanceof ResultOk ok) {
                ArrayList<Date> list_log = (ArrayList<Date>) ok.data();
                new DateListFrame(list_log).setVisible(true);
            }

        } catch (IOException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnUpdateUserPwdMouseClicked

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
        final String group_id = (String) tblGroup.getValueAt(tblGroup.getSelectedRow(), 0);

        try {
            Result rs = Client.Client.api_c.invoke_api("AdminService", "list_group_members", token, group_id);
            if (rs instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (rs instanceof ResultOk ok) {
                ArrayList<GroupChatMemberInfo> list_log = (ArrayList<GroupChatMemberInfo>) ok.data();
                for (int i = 0; i < list_log.size(); ++i) {
                    if (list_log.get(i).is_admin == false) {
                        list_log.remove(i);
                    }
                }
                new GroupChatMemberTable(list_log).setVisible(true);
            }

        } catch (IOException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnListAdminGroupMouseClicked

    private void btnListMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnListMemberMouseClicked
        final String token = Client.Client.token;
        final String group_id = (String) tblGroup.getValueAt(tblGroup.getSelectedRow(), 0);

        try {
            Result rs = Client.Client.api_c.invoke_api("AdminService", "list_group_members", token, group_id);
            if (rs instanceof ResultError err) {
                JOptionPane.showMessageDialog(null, err.msg());
            } else if (rs instanceof ResultOk ok) {
                ArrayList<GroupChatMemberInfo> list_log = (ArrayList<GroupChatMemberInfo>) ok.data();
                for (var list : list_log) {
                    System.out.println(list);
                }
                new GroupChatMemberTable(list_log).setVisible(true);
            }

        } catch (IOException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnListMemberMouseClicked

    private void btnListFriend2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListFriend2ActionPerformed
        final String token = Client.Client.token;
        int index = tblSpam.getSelectedRow();
        String msg = "";
        if (index == -1) {
            msg = "You must select user before lock";
        } else {
            String username = (String) modelSpam.getValueAt(index, 1);
            try {
                Result rs = Client.Client.api_c.invoke_api("AdminService", "lock_user", token, username);
                if (rs instanceof ResultError err) {
                    msg = err.msg();
                } else if (rs instanceof ResultOk ok) {
                    msg = "Lock User Successfully";
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(null, msg);
    }//GEN-LAST:event_btnListFriend2ActionPerformed

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JLabel btnMinimizeSpam;
    private javax.swing.JButton btnUpdateUser;
    private javax.swing.JButton btnUpdateUserPwd;
    private javax.swing.JButton btnUpdateUserPwd1;
    private javax.swing.JComboBox<String> cbFilter;
    private javax.swing.JComboBox<String> cbFilterActiveUser;
    private javax.swing.JComboBox<String> cbFilterGroup;
    private javax.swing.JComboBox<String> cbFilterMoreUserInfo;
    private javax.swing.JComboBox<String> cbFilterNewUser;
    private javax.swing.JComboBox<String> cbFilterSpam;
    private javax.swing.JComboBox<String> cbSortActiveUser;
    private javax.swing.JComboBox<String> cbSortMoreUserInfo;
    private javax.swing.JComboBox<String> cbStatiscal;
    private javax.swing.JLabel closeBtn;
    private javax.swing.JLabel closeBtn1;
    private javax.swing.JLabel closeBtn10;
    private javax.swing.JLabel closeBtn11;
    private javax.swing.JLabel closeBtn12;
    private javax.swing.JLabel closeBtn13;
    private javax.swing.JLabel closeBtn2;
    private javax.swing.JLabel closeBtn4;
    private javax.swing.JLabel closeBtn6;
    private javax.swing.JLabel closeBtn8;
    private javax.swing.JLabel closeBtn9;
    private javax.swing.JPanel data;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lbDate;
    private static javax.swing.JTable tblGroup;
    private static javax.swing.JTable tblNewUser;
    private static javax.swing.JTable tblSpam;
    private static javax.swing.JTable tblUser;
    private static javax.swing.JTable tblUser4;
    private static javax.swing.JTable tblUser5;
    private javax.swing.JTextField tfYear;
    private javax.swing.JLabel txtManageNewUser;
    private javax.swing.JLabel txtManageNewUser1;
    private javax.swing.JLabel txtMangeGroup;
    private javax.swing.JLabel txtSpamList;
    // End of variables declaration//GEN-END:variables
}
