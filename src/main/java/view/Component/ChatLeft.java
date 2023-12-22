package view.Component;

import java.awt.Color;


public class ChatLeft extends javax.swing.JLayeredPane  {

  
    public ChatLeft() {
        initComponents();
        txt.setBackground(new Color(242,242,242));
    }
   
    public void setText(String text){
          txt.setText(text);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
        add(txt);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel txt;
    // End of variables declaration//GEN-END:variables
}
