package com.fat.GUI.Panels.Statistics;

import javax.swing.*;
import java.awt.*;

public class StatisticPanel extends javax.swing.JPanel{
    private JButton btnDoanhThu, btnTonKho, btnKhachHang, btnNhanVien, btnSanPham;
    private JButton activeButton;
    
    // CardLayout để quản lý các subpanel
    private CardLayout contentCardLayout;
    private JPanel contentPanel;
    
    public StatisticPanel(){
        initComponents();
    }
    
    private void initComponents(){
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 245, 245));
        
        // Top Panel - Navigation
        JPanel topPanel = createTopPanel();
        this.add(topPanel, BorderLayout.NORTH);
        
        // Center Panel - CardLayout để quản lý các subpanel
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);
        contentPanel.setBackground(new Color(245, 245, 245));
        
        // Tạo các subpanel cho mỗi loại thống kê
        RevenuePanel revenuePanel = new RevenuePanel();
        InventoryPanel inventoryPanel = new InventoryPanel();
        CustomerPanel customerPanel = new CustomerPanel();
        StaffPanel staffPanel = new StaffPanel();
        ProductPanel productPanel = new ProductPanel();
        
        // Add các subpanel vào CardLayout
        contentPanel.add(revenuePanel, "DOANHTHU");
        contentPanel.add(inventoryPanel, "TONKHO");
        contentPanel.add(customerPanel, "KHACHHANG");
        contentPanel.add(staffPanel, "NHANVIEN");
        contentPanel.add(productPanel, "SANPHAM");
        
        this.add(contentPanel, BorderLayout.CENTER);
        
        // Mặc định hiển thị panel "Doanh thu"
        contentCardLayout.show(contentPanel, "DOANHTHU");
    }
    
    private JPanel createTopPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 5, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Navigation Buttons
        btnDoanhThu = createNavButton("Doanh thu");
        btnTonKho = createNavButton("Tồn kho");
        btnKhachHang = createNavButton("Khách hàng");
        btnNhanVien = createNavButton("Nhân viên");
        btnSanPham = createNavButton("Sản phẩm");
        
        // Add action listeners - click sẽ load lại subpanel
        btnDoanhThu.addActionListener(e -> showSubPanel("DOANHTHU", btnDoanhThu));
        btnTonKho.addActionListener(e -> showSubPanel("TONKHO", btnTonKho));
        btnKhachHang.addActionListener(e -> showSubPanel("KHACHHANG", btnKhachHang));
        btnNhanVien.addActionListener(e -> showSubPanel("NHANVIEN", btnNhanVien));
        btnSanPham.addActionListener(e -> showSubPanel("SANPHAM", btnSanPham));
        
        panel.add(btnDoanhThu);
        panel.add(btnTonKho);
        panel.add(btnKhachHang);
        panel.add(btnNhanVien);
        panel.add(btnSanPham);
        
        // Set default active button
        setActiveButton(btnDoanhThu);
        
        return panel;
    }
    
    /**
     * Hiển thị subpanel tương ứng và set active button
     */
    private void showSubPanel(String panelName, JButton button){
        contentCardLayout.show(contentPanel, panelName);
        setActiveButton(button);
    }
    
    private JButton createNavButton(String text){
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(100, 100, 100));
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void setActiveButton(JButton btn){
        // Reset all buttons to normal
        btnDoanhThu.setBackground(Color.WHITE);
        btnDoanhThu.setForeground(new Color(100, 100, 100));
        
        btnTonKho.setBackground(Color.WHITE);
        btnTonKho.setForeground(new Color(100, 100, 100));
        
        btnKhachHang.setBackground(Color.WHITE);
        btnKhachHang.setForeground(new Color(100, 100, 100));
        
        btnNhanVien.setBackground(Color.WHITE);
        btnNhanVien.setForeground(new Color(100, 100, 100));
        
        btnSanPham.setBackground(Color.WHITE);
        btnSanPham.setForeground(new Color(100, 100, 100));
        
        // Set active button with highlight
        btn.setBackground(new Color(64, 64, 64)); // màu xám
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        activeButton = btn;
    }
}
