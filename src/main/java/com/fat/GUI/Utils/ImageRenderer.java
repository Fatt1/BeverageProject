package com.fat.GUI.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         JLabel label = (JLabel)  super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         if(value instanceof  ImageIcon) {
             label.setIcon((ImageIcon) value);
             label.setText("");
         }
         else{
             label.setText("");
             label.setIcon(null);
         }
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
    }
}
