package com.fat.GUI;

import com.fat.BUS.Services.ProductService;
import com.fat.DAO.Repositories.ProductDAO;
import com.fat.DTO.ProductDTO;
import com.fat.DTO.ProductUnitDTO;
import com.fat.GUI.Panels.AdminPanel;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main extends JFrame {
    public Main() {
        init();
    }

    private void init() {
        setTitle("Quản lý bán nước giải khát");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1350, 800);
        setLocationRelativeTo(null);
        setContentPane(new AdminPanel());




    }

    public static void main(String[] args) {
        // Cai font Roboto lam font mac dinh cho ung dung
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14));

        // Cai dat giao dien FlatLaf
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }
}
