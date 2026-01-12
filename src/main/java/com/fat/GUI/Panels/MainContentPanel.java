package com.fat.GUI.Panels;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MainContentPanel extends JPanel {
    public MainContentPanel() {
        setLayout(new MigLayout("fill, insets 0", "[grow]"));

        putClientProperty(FlatClientProperties.STYLE, "" +
                "border: 2,2,2,2, #000000, 1, 15"
        );
        setBackground(Color.WHITE);
    }

}
