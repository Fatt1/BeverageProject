package com.fat.GUI.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CheckboxRenderer extends DefaultTableCellRenderer {
    private final JCheckBox checkBox = new JCheckBox();

    public CheckboxRenderer() {
        checkBox.setHorizontalAlignment(JLabel.CENTER);
        checkBox.setBackground(Color.WHITE); // Hoặc màu nền bảng của bạn
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value == null) {
            JPanel panel = new JPanel();
            panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return panel;
        }

        checkBox.setSelected(Boolean.TRUE.equals(value));
        // Xử lý màu nền khi được chọn (Select row)
        if (isSelected) {
            checkBox.setBackground(table.getBackground());
            checkBox.setForeground(table.getSelectionForeground());
        } else {
            checkBox.setBackground(table.getBackground());
            checkBox.setForeground(table.getForeground());
        }
        return checkBox;
    }
}
