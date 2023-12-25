package view.Component;

import com.toedter.calendar.JDateChooser;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import view.Interface.DateSelectionListener;
import view.Sync.SyncObject;

public class DateSelectionForm extends javax.swing.JFrame {

    private JLabel startDateLabel, endDateLabel;
    private static JDateChooser startDateChooser, endDateChooser;
    private JButton confirmButton;
    private final SyncObject syncObject;
    private final DateSelectionListener dateSelectionListener;

    public DateSelectionForm(DateSelectionListener listener, SyncObject syncObject) {
        this.dateSelectionListener = listener;
        this.syncObject = syncObject;
        initComponents();
        init();
    }

    private void init() {
        setTitle("Select Date");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        startDateLabel = new JLabel("From:");
        endDateLabel = new JLabel("To:");
        startDateChooser = new JDateChooser();
        endDateChooser = new JDateChooser();
        confirmButton = new JButton("Filter");
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(startDateLabel);
        panel.add(startDateChooser);
        panel.add(endDateLabel);
        panel.add(endDateChooser);

        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new GridLayout(1, 1));
        panelBtn.add(confirmButton);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        add(panel);
        add(panelBtn);
        setResizable(false);
        setVisible(true);
        
        confirmButton.addActionListener((ActionEvent e) -> {
            Date[] selectedDates = getSelectedDates();
            if (selectedDates != null && dateSelectionListener != null) {
                dateSelectionListener.onDateSelected(selectedDates[0], selectedDates[1]);
                syncObject.setDateSelected(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Date, Please try again !!!");
            }

        });

    }

    public static Date[] getSelectedDates() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = startDateChooser.getDate();
            Date endDate = endDateChooser.getDate();
            return new Date[]{
                startDate, endDate
            };
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Date must not be null");
            ex.printStackTrace();
        }
        return null;
    }

//////    @SuppressWarnings("unchecked")
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
