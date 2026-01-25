/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.fat.GUI.Forms;


import com.fat.DAO.Repositories.StaffDAO;
import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.GUI.MainForm;
import com.fat.GUI.Dialogs.ConfirmDialog.ConfirmDialog;
import com.fat.GUI.Utils.ImageHelper;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 *
 * @author User
 */
public class LoginForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginForm.class.getName());

    /**
     * Creates new form LoginForm
     */
    public LoginForm(){
        setTitle("Đăng Nhập Vào Hệ thống");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 760);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        JPanel Login = new JPanel(new GridLayout(1, 2));

        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/BackgroundLogin.png"));
        Image image = icon.getImage();

        JPanel rightPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JPanel leftPanel = new JPanel();

        // right Panel
        rightPanel.setBackground(Color.WHITE);
        
        JLabel containerImgRPanel = new JLabel();
        containerImgRPanel.setIcon(new ImageIcon(image));

        rightPanel.add(containerImgRPanel);

        // left Panel
        leftPanel.setBackground(Color.WHITE );
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel leftWrapper = new JPanel();
        leftWrapper.setBackground(Color.WHITE);
        leftWrapper.setLayout(new BoxLayout(leftWrapper, BoxLayout.Y_AXIS));
        leftWrapper.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));
        leftWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("ĐĂNG NHẬP");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblUser = new JLabel("Tên đăng nhập: ");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtUser = new JTextField();
        txtUser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtUser.setAlignmentX(Component.LEFT_ALIGNMENT); 

        JLabel lblPass = new JLabel("Mật Khẩu: ");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPass.setAlignmentX(Component.LEFT_ALIGNMENT); 
      
        JButton btnLogin = new JButton("Đăng Nhập");
        btnLogin.setBackground(new Color(255, 204, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT); 

        btnLogin.addActionListener(e -> {
            String userName = txtUser.getText();
            String password = String.valueOf(txtPass.getPassword());

            if (userName.trim().isEmpty() || password.trim().isEmpty()){
                ConfirmDialog.show(this, "Error", "Không được để trống username hoặc password", "OK");
                return;
            }

            if(!StaffDAO.getInstance().isLoginSuccessful(userName, password)){
                ConfirmDialog.show(this, "Error", "Sai username hoặc password", "OK");
                return;
            }
            
            String idStaff = StaffDAO.getInstance().getIdStaffOfLoginSuccessful(userName, password);
            // JOptionPane.showMessageDialog(null, 
            //             "Đăng nhập thành công! ID của bạn là: " + idStaff, 
            //             "Thông báo", 
            //             JOptionPane.INFORMATION_MESSAGE);
            StaffDetailDTO staff = StaffDAO.getInstance().getById(Integer.parseInt(idStaff));
            UserSessionDTO.getInstance().setSession(staff.getId(), userName, password, staff.getRoleId());
            new MainForm().init();
        });



        leftWrapper.add(title);

        leftWrapper.add(Box.createRigidArea(new Dimension(0, 50)));
        leftWrapper.add(lblUser);
        leftWrapper.add(txtUser);

        leftWrapper.add(Box.createRigidArea(new Dimension(0, 30)));
        leftWrapper.add(lblPass);
        leftWrapper.add(txtPass);

        leftWrapper.add(Box.createRigidArea(new Dimension(0, 50)));
        leftWrapper.add(btnLogin);
        
        leftPanel.add(leftWrapper);
        // leftPanel.setBackground(Color.PINK);
        
        // them vao main panel
        Login.add(leftPanel);
        Login.add(rightPanel);
        mainPanel.add(Login);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(100, 20, 200, 0));

        // Login.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCC00"), 2));
        // leftPanel.putClientProperty("FlatLaf.style", "border: 2,2,2,2,#FFCC00,,50");
        // leftWrapper.putClientProperty("FlatLaf.style", "border: 2,2,2,2,#FFCC00,,50");
       
        Login.putClientProperty("FlatLaf.style", "border: 2,2,2,2,#FFCC00,,50");

        add(mainPanel);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        com.formdev.flatlaf.FlatLightLaf.setup(); // Kích hoạt FlatLaf Light
        new LoginForm().setVisible(true);
        // try {
        //     for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        //         if ("Nimbus".equals(info.getName())) {
        //             javax.swing.UIManager.setLookAndFeel(info.getClassName());
        //             break;
        //         }
        //     }
        // } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
        //     logger.log(java.util.logging.Level.SEVERE, null, ex);
        // }
        //</editor-fold>

        /* Create and display the form */
        // java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
