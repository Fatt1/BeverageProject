package com.fat.GUI.Panels.Products;

import com.fat.GUI.Panels.TableRow;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ProductRow extends TableRow {
    public ProductRow(String layoutConstraints) {

        super(layoutConstraints);
        add(new JLabel("1"));
        add(new JLabel(new FlatSVGIcon(getImageUrl()).derive(40,40))); // Ảnh
        add(new JLabel("Trà Đào Cam Sả")); // Tên
        add(new JLabel("Thức uống")); // Loại
        add(new JLabel("45.000 đ")); // Giá
        add(new JLabel("120.000đ")) ; // Giá vốn
        add(new JLabel("Còn hàng")); // Trạng thái
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.setOpaque(false);
        JButton editButton = new JButton(new FlatSVGIcon(getEditIconUrl()).derive(22,22));
        editButton.setFocusPainted(false);
        editButton.setContentAreaFilled(false);
        editButton.setBorderPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton deleteButton = new JButton(new FlatSVGIcon(getDeleteIconUrl()).derive(22,22));
        deleteButton.setFocusPainted(false);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        add(actionPanel); // Hành động
    }

    public URL getImageUrl() {
        return getClass().getResource("/images/image1.svg");
    }

    public URL getDeleteIconUrl() {
        return getClass().getResource("/Icons/delete.svg");
    }
    public URL getEditIconUrl() {
        return getClass().getResource("/Icons/Edit.svg");
    }
}
