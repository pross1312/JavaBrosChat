package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class AdminDashboard extends javax.swing.JFrame {

    public static DefaultTableModel model;

    public AdminDashboard() {
        //TODO Get list of user from db and display in table. 
        initComponents();
        model = (DefaultTableModel) tblUser.getModel();

        //Filtering 
        cbFilter.addItem("Name");
        cbFilter.addItem("User name");
        cbFilter.addItem("Status");

        //Sorting 
        cbSort.addItem("Name");
        cbSort.addItem("Created At");
        cbSort.addItem("Log in Time ");

        //Filter Group 
        cbFilterGroup.addItem("Name");

        //Sorting Group
        cbSortGroup.addItem("Group Name");
        cbSortGroup.addItem("Created At");

        //Filter Span 
        cbFilterSpam.addItem("Username");
        cbFilterSpam.addItem("Time report");

        //Sorting Spam 
        cbSortSpam.addItem("Time report");
        cbSortSpam.addItem("Username");
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
        cbSort = new javax.swing.JComboBox<>();
        cbFilter = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        closeBtn2 = new javax.swing.JLabel();
        closeBtn3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUser1 = new javax.swing.JTable();
        btnListFriend1 = new javax.swing.JButton();
        btnUpdateUserPwd3 = new javax.swing.JButton();
        cbSortGroup = new javax.swing.JComboBox<>();
        cbFilterGroup = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUser2 = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnListFriend2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        closeBtn4 = new javax.swing.JLabel();
        cbSortSpam = new javax.swing.JComboBox<>();
        closeBtn5 = new javax.swing.JLabel();
        cbFilterSpam = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ADMIN DASHBOARD");

        txtMangeGroup.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMangeGroup.setForeground(new java.awt.Color(255, 255, 255));
        txtMangeGroup.setText("Manage Group");
        txtMangeGroup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtMangeGroup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMangeGroupMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Manage User");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        txtSpamList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSpamList.setForeground(new java.awt.Color(255, 255, 255));
        txtSpamList.setText("Spam List");
        txtSpamList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtSpamList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSpamListMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Statistical");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/group.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/open-box.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/spam.png"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/statistics.png"))); // NOI18N

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-add-user-male-24.png"))); // NOI18N

        txtManageNewUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtManageNewUser.setForeground(new java.awt.Color(255, 255, 255));
        txtManageNewUser.setText("Manage new user");
        txtManageNewUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtManageNewUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtManageNewUserMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout NavigateLayout = new javax.swing.GroupLayout(Navigate);
        Navigate.setLayout(NavigateLayout);
        NavigateLayout.setHorizontalGroup(
            NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NavigateLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMangeGroup)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
            .addGroup(NavigateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NavigateLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addGroup(NavigateLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtManageNewUser))
                    .addGroup(NavigateLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSpamList)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        NavigateLayout.setVerticalGroup(
            NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigateLayout.createSequentialGroup()
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(NavigateLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addGap(49, 49, 49)
                        .addComponent(txtMangeGroup))
                    .addComponent(jLabel7))
                .addGap(42, 42, 42)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSpamList)
                    .addComponent(jLabel8))
                .addGap(32, 32, 32)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtManageNewUser)
                    .addComponent(jLabel18))
                .addGap(37, 37, 37)
                .addGroup(NavigateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9))
                .addGap(0, 0, Short.MAX_VALUE))
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
                "Username", "Name", "Address", "Date of Birth", "Gender", "Email"
            }
        ));
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUser);
        if (tblUser.getColumnModel().getColumnCount() > 0) {
            tblUser.getColumnModel().getColumn(2).setHeaderValue("Address");
            tblUser.getColumnModel().getColumn(3).setHeaderValue("Date of Birth");
            tblUser.getColumnModel().getColumn(4).setHeaderValue("Gender");
            tblUser.getColumnModel().getColumn(5).setHeaderValue("Email");
        }

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

        btnUpdateUser.setText("UPDATE");
        btnUpdateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateUserActionPerformed(evt);
            }
        });

        btnDeleteUser.setText("DELETE");
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        btnLockUser.setText("LOCK");

        btnUpdateUserPwd.setText("SEE LOG");

        btnListFriend.setText("SEE LIST FRIEND");

        btnUpdateUserPwd1.setText("UPDATE PASWORD");

        cbSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortActionPerformed(evt);
            }
        });

        cbFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterActionPerformed(evt);
            }
        });

        jLabel2.setText("Sort by: ");

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
                        .addGap(0, 170, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnListFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnUpdateUserPwd1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnUpdateUserPwd, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(btnUpdateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnLockUser, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 268, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbSort, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(172, 172, 172)
                                .addComponent(closeBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        closeBtn3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn3.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn3.setText("-");
        closeBtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn3MouseClicked(evt);
            }
        });

        tblUser1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Group Name", "Created at"
            }
        ));
        tblUser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUser1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblUser1);

        btnListFriend1.setText("LIST ADMIN GROUP");

        btnUpdateUserPwd3.setText("LIST MEMBER GROUP");

        cbSortGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortGroupActionPerformed(evt);
            }
        });

        cbFilterGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterGroupActionPerformed(evt);
            }
        });

        jLabel13.setText("Sort by: ");

        jLabel14.setText("Filter by:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addComponent(btnListFriend1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnUpdateUserPwd3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(336, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbSortGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(57, 57, 57))
                            .addComponent(cbFilterGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addComponent(closeBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSortGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdateUserPwd3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListFriend1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43))
        );

        data.add(jPanel3, "card3");

        tblUser2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Time", "Report by", "Reason"
            }
        ));
        tblUser2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUser2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblUser2);

        jLabel15.setText("Sort by: ");

        jLabel16.setText("Filter by:");

        btnListFriend2.setText("LOCK USER");

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

        cbSortSpam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortSpamActionPerformed(evt);
            }
        });

        closeBtn5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeBtn5.setForeground(new java.awt.Color(204, 0, 0));
        closeBtn5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeBtn5.setText("-");
        closeBtn5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtn5MouseClicked(evt);
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
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbSortSpam, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(57, 57, 57))
                    .addComponent(cbFilterSpam, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(closeBtn5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(397, 397, 397)
                .addComponent(btnListFriend2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeBtn5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSortSpam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterSpam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnListFriend2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(45, 45, 45))
        );

        data.add(jPanel1, "card4");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 987, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 712, Short.MAX_VALUE)
        );

        data.add(jPanel4, "card5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Navigate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(data, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Navigate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(data, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1172, 709));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
                    model.getValueAt(index, 4).toString(), model.getValueAt(index, 5).toString(), "Update User", "UPDATE", index);
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
        if (index != -1) {
            int result = JOptionPane.showConfirmDialog((Component) null, "Are you sure?",
                    "alert", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                model.removeRow(tblUser.getSelectedRow());
                JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
            }
        } else {
            JOptionPane.showMessageDialog(null, "You must select user before delete", "WARNING", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void txtMangeGroupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMangeGroupMouseClicked
        data.removeAll();
        data.repaint();
        data.revalidate();
        data.add(jPanel3);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_txtMangeGroupMouseClicked

    private void cbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortActionPerformed

//        JOptionPane.showMessageDialog(null, cbSort.getSelectedItem());
    }//GEN-LAST:event_cbSortActionPerformed

    private void cbFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterActionPerformed
        // TODO add your handling code here:
//        JOptionPane.showMessageDialog(null, cbFilter.getSelectedItem());
    }//GEN-LAST:event_cbFilterActionPerformed

    private void closeBtn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_closeBtn2MouseClicked

    private void closeBtn3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_closeBtn3MouseClicked

    private void tblUser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUser1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUser1MouseClicked

    private void cbSortGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortGroupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSortGroupActionPerformed

    private void cbFilterGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterGroupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFilterGroupActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        data.removeAll();
        data.repaint();
        data.revalidate();
        data.add(jPanel2);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void tblUser2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUser2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUser2MouseClicked

    private void closeBtn4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_closeBtn4MouseClicked

    private void cbSortSpamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortSpamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSortSpamActionPerformed

    private void closeBtn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtn5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_closeBtn5MouseClicked

    private void cbFilterSpamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterSpamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFilterSpamActionPerformed

    private void txtSpamListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSpamListMouseClicked
        data.removeAll();
        data.repaint();
        data.revalidate();
        data.add(jPanel1);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_txtSpamListMouseClicked

    private void txtManageNewUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManageNewUserMouseClicked
        // TODO add your handling code here:
        data.removeAll();
        data.repaint();
        data.revalidate();
        data.add(jPanel4);
        data.repaint();
        data.revalidate();
    }//GEN-LAST:event_txtManageNewUserMouseClicked

    public static void addRowtoTable(Object[] dataRow) {
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
    private javax.swing.JButton btnListFriend;
    private javax.swing.JButton btnListFriend1;
    private javax.swing.JButton btnListFriend2;
    private javax.swing.JButton btnLockUser;
    private javax.swing.JButton btnUpdateUser;
    private javax.swing.JButton btnUpdateUserPwd;
    private javax.swing.JButton btnUpdateUserPwd1;
    private javax.swing.JButton btnUpdateUserPwd3;
    private javax.swing.JComboBox<String> cbFilter;
    private javax.swing.JComboBox<String> cbFilterGroup;
    private javax.swing.JComboBox<String> cbFilterSpam;
    private javax.swing.JComboBox<String> cbSort;
    private javax.swing.JComboBox<String> cbSortGroup;
    private javax.swing.JComboBox<String> cbSortSpam;
    private javax.swing.JLabel closeBtn;
    private javax.swing.JLabel closeBtn1;
    private javax.swing.JLabel closeBtn2;
    private javax.swing.JLabel closeBtn3;
    private javax.swing.JLabel closeBtn4;
    private javax.swing.JLabel closeBtn5;
    private javax.swing.JPanel data;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JTable tblUser;
    private static javax.swing.JTable tblUser1;
    private static javax.swing.JTable tblUser2;
    private javax.swing.JLabel txtManageNewUser;
    private javax.swing.JLabel txtMangeGroup;
    private javax.swing.JLabel txtSpamList;
    // End of variables declaration//GEN-END:variables
}
