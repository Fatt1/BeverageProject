package com.fat.GUI.Components;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;

public class MenuItem extends JToggleButton {

    private final Color defaultBg = new Color(217, 217, 217);
    private final Color selectedBg = new Color(0, 0, 0, 80);
    public MenuItem(String text, Icon icon) {
        super(text, icon);

        // Can le trai cho text va icon
        setHorizontalAlignment(SwingConstants.LEFT);

        // Khoang cach giua icon va text
        setIconTextGap(8);


        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(true); // De hien thi mau nen khi active

        setFont(new Font("Segoe UI", Font.PLAIN, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBackground(defaultBg);
        this.setHorizontalAlignment(SwingConstants.LEFT);
        // Bo tron 10px
        this.putClientProperty(FlatClientProperties.STYLE, ""  +
                "font: bold"
        );

        // Khi trang thái thay đổi thì sự kiện này mới chạy
        addItemListener(l -> {
            updateBackground();
        });


    }

    private void updateBackground() {
        if (isSelected()) {
            setBackground(selectedBg);
        } else {
            setBackground(defaultBg);
        }
    }


}
