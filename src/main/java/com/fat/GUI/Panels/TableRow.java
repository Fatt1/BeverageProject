package com.fat.GUI.Panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableRow extends JPanel {
    private JCheckBox chkSelect;
    public static final Color ROW_BG = new Color(255, 255, 255);
    public static final Color ROW_HOVER_BG = new Color(245, 250, 255);
    public  TableRow(String layoutConstraints) {
        setLayout(new MigLayout("insets 0, fillx, aligny center", layoutConstraints));
        setBackground(ROW_BG);
        setPreferredSize(new Dimension(0, 60)); // set chieu cao hang mac dinh

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(ROW_HOVER_BG);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(ROW_BG);
            }
        });

    }

    public void addCheckbox(JCheckBox chkSelect) {
        this.chkSelect = chkSelect;
    }

    public boolean isSelected() {
        return chkSelect != null && chkSelect.isSelected();
    }
}
