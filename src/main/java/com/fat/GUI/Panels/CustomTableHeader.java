package com.fat.GUI.Panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class CustomTableHeader extends JPanel {
    public static final Color HEADER_BG = new Color(255, 255, 255);

    public static final Color TEXT_HEADER_COLOR = new Color(121, 121, 121);

    public CustomTableHeader(String layoutConstraints, String[] columns) {
        setLayout(new MigLayout("insets 0, fillx", layoutConstraints));
        setBorder(BorderFactory.createMatteBorder(0,0,1,0, new Color(216, 216, 216)));
        setBackground(HEADER_BG);
        for(String column : columns) {
            JLabel headerLabel = new JLabel(column);
            headerLabel.setForeground(TEXT_HEADER_COLOR);
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            headerLabel.setPreferredSize(new Dimension(0, 40));
            add(headerLabel);
        }
    }
}
