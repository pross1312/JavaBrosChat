package view;

import Utils.LoginRecord;
import Utils.Pair;
import Utils.RegistrationRecord;
import Utils.Result;
import Utils.ResultError;
import Utils.ResultOk;
import Utils.UserInfo;
import java.awt.Font;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import view.API.CallAPI;
import static view.Version2AdminDashBoard.addRowtoTable;
import static view.Version2AdminDashBoard.model;

// This file provide some function to style UI
public class Utils {

    public static void initFilterCate(JComboBox cb, String[] cateList) {
        for (var cate : cateList) {
            cb.addItem(cate);
        }
    }

    public static void styleTable(JTable table) {
        table.setFont(new Font("Serif", Font.PLAIN, 16));
        table.setDefaultEditor(Object.class, null);
    }

   
}
