/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class LockColumnRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        boolean isLocked = false;
        if (value instanceof String) {
            isLocked = Boolean.parseBoolean((String)value);
        } else if (value instanceof Boolean) {
            isLocked = (boolean) value;
        } else throw new RuntimeException("Unexpected " + value.getClass().getName());

        if (isLocked) {
            setBackground(Color.YELLOW);
        } else {
            setBackground(table.getBackground());
        }
        
        return this;
    }
}
