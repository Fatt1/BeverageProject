package com.fat.GUI.Components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ImagePicker extends JPanel {
    private JLabel lblIcon;
    private JLabel lblImage;
    private File selectedFile;

    public ImagePicker() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 0", "[center]"));

        setPreferredSize(new Dimension(150, 150));
        setBackground(Color.WHITE);
        putClientProperty(FlatClientProperties.STYLE, "" +
                "border: 1,1,1,1, #cccccc, 1;" +
                "background: #f9f9f9;"
                );

        lblIcon = new JLabel("<html><center>Nhấn để<br>tải ảnh lên</center></html>");
        lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblIcon.setForeground(Color.GRAY);
        lblIcon.setIcon(new FlatSVGIcon(getClass().getResource("/Icons/Camera.svg")).derive(15,15));

        lblImage = new JLabel();
        lblImage.setVisible(false);

        add(lblIcon);
        add(lblImage, "cell 0 0, grow");


        // 4. Sự kiện Click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chooseImage();
            }
        });

    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "svg", "jpeg", "gif"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            int w = getPreferredSize().width - 4;
            int h = getPreferredSize().height - 4;
            selectedFile = fileChooser.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            // Resize image to fit the label
            Image image = imageIcon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(image));
            lblImage.setVisible(true);
            lblIcon.setVisible(false);
        }
    }

    // Hàm lấy file để lưu xuống DB
    public File getSelectedFile() {
        return selectedFile;
    }

}
