package com.fat.GUI.Components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class FormGroup extends JPanel {
    // 1. Constructor rút gọn (Không cần truyền isOverride -> mặc định là false)
    public FormGroup(String title, JComponent inputComponent) {
        this(title, inputComponent, false); // Gọi constructor chính bên dưới
    }
    public FormGroup(String title, JComponent inputComponent, boolean isOverride) {
        setLayout(new MigLayout("fill, wrap, insets 0"));
        JLabel titleLabel = new JLabel(title);

        titleLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font: bold"
        );
        if(!isOverride)
        if(inputComponent instanceof JTextField) {
            ((JTextField) inputComponent).putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập " + title.toLowerCase());
        }

        String constraints = "growx";
        if (inputComponent instanceof JScrollPane) {

            constraints += ", h 100!";
        } else {
            // Nếu là TextField, ComboBox...
            // Giữ chiều cao chuẩn 40px
            constraints += ", h 30!";
        }

        add(titleLabel);
        add(inputComponent, constraints);

        setOpaque(false);
    }
}
