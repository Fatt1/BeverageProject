/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.fat.GUI.Panels.Staffs;

import com.fat.BUS.Abstractions.Services.IRoleService;
import com.fat.BUS.Abstractions.Services.IStaffService;
import com.fat.BUS.Services.RoleService;
import com.fat.BUS.Services.StaffService;
import com.fat.BUS.Utils.ExcelHelper;
import com.fat.DTO.Roles.RoleDTO;
import com.fat.DTO.Staffs.StaffDTO;
import com.fat.GUI.Dialogs.Staffs.AddOrUpdateStaffDialog;
import com.fat.GUI.Utils.FormatterUtil;
import com.formdev.flatlaf.FlatClientProperties;
import jakarta.validation.groups.Default;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author tranc
 */
public class StaffsPanel extends javax.swing.JPanel {

    private IStaffService staffService;
    private IRoleService roleService;
    private Integer selectedRoleId = null;
    private String searchKey = null;
    boolean isFirstLoad = true;
    public StaffsPanel() {
        this.staffService = StaffService.getInstance();
        this.roleService = RoleService.getInstance();
        initComponents();
        initTable();
        setCss();
        

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateDataOnShow();
            }
        });
        
        // Lắng nghe sự kiện pagination
        paginationPanel1.addPaginationEventListener((pageIndex, pageSize) -> {
            loadData();
        });
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblStaff = new javax.swing.JTable();
        paginationPanel1 = new com.fat.GUI.Components.PaginationPanel();
        jPanel1 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnImportExcel = new javax.swing.JButton();
        btnExportExcel = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        tblStaff.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "HỌ VÀ TÊN", "NGÀY SINH", "MỨC LƯƠNG", "SỐ ĐIỆN THOẠI", "TÊN ĐĂNG NHẬP", "VAI TRÒ"
            }
        ));
        jScrollPane1.setViewportView(tblStaff);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        paginationPanel1.setBackground(new java.awt.Color(255, 255, 255));
        add(paginationPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnReset.setBackground(new java.awt.Color(141, 141, 141));
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Reset.png"))); // NOI18N
        btnReset.setText("Hoàn tác lọc");
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReset.addActionListener(this::btnResetActionPerformed);

        txtSearch.addActionListener(this::txtSearchActionPerformed);
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        jLabel1.setText("Tìm kiếm theo tên");

        btnUpdate.setBackground(new java.awt.Color(51, 51, 51));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Edit.png"))); // NOI18N
        btnUpdate.setText("Chỉnh sửa");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnImportExcel.setBackground(new java.awt.Color(46, 125, 50));
        btnImportExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnImportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Microsoft Excel.png"))); // NOI18N
        btnImportExcel.setText("Nhập Excel");
        btnImportExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImportExcel.addActionListener(this::btnImportExcelActionPerformed);

        btnExportExcel.setBackground(new java.awt.Color(46, 125, 50));
        btnExportExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Microsoft Excel.png"))); // NOI18N
        btnExportExcel.setText("Xuất Excel");
        btnExportExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportExcel.addActionListener(this::btnExportExcelActionPerformed);

        btnAdd.setBackground(new java.awt.Color(51, 51, 51));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Xbox Cross.png"))); // NOI18N
        btnAdd.setText("Thêm mới");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.addActionListener(this::btnAddActionPerformed);

        btnDelete.setBackground(new java.awt.Color(255, 0, 0));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Close.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 911, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(btnAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnImportExcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExportExcel)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );

        add(jPanel1, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {
        txtSearch.setText("");
        searchKey = null;
        loadData();

    }//GEN-LAST:event_btnResetActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            searchKey = txtSearch.getText().trim();
            if (searchKey.isEmpty()) {
                searchKey = null;
            }
            loadData();
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int selectedRow = tblStaff.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn nhân viên cần chỉnh sửa.",
                    "Chưa chọn nhân viên",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idObj = tblStaff.getValueAt(selectedRow, 0);
        int id = Integer.parseInt(idObj.toString());
        
        var staffDetailDTO = staffService.getStaffById(id);
        
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddOrUpdateStaffDialog dialog = new AddOrUpdateStaffDialog(parentFrame, true, staffDetailDTO);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);

        // Sau khi đóng dialog, reload dữ liệu
        if (dialog.isConfirmed()) {
            loadData();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnImportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportExcelActionPerformed
        var result = ExcelHelper.readFromExcel();
        if (!result.isEmpty()) {
            for (var row : result) {
                try {
                    String firstName = row.get(0).toString();
                    String lastName = row.get(1).toString();
                    String birthDateStr = row.get(2).toString();
                    LocalDate birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    BigDecimal salary = new BigDecimal(row.get(3).toString());
                    String phoneNumber = row.get(4).toString();
                    String userName = row.get(5).toString();
                    String password = row.get(6).toString();
                    int roleId = (int) Double.parseDouble(row.get(7).toString());

                    StaffDTO staff = new StaffDTO();
                    staff.setFirstName(firstName);
                    staff.setLastName(lastName);
                    staff.setBirthday(birthDate);
                    staff.setSalary(salary);
                    staff.setPhoneNumber(phoneNumber);
                    staff.setRoleId(roleId);
                    staff.setCreatedAt(java.time.LocalDateTime.now());
                    staff.setUserName(userName);
                    staff.setPassword(password);
                    
                    staffService.createStaff(staff);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Dữ liệu trong file Excel không hợp lệ. Vui lòng kiểm tra lại.\n" +
                            "Format: Tên | Họ | Ngày sinh (dd/MM/yyyy) | Lương | SĐT | Username | Password | RoleId",
                            "Lỗi dữ liệu",
                            JOptionPane.ERROR_MESSAGE);
                    break; // Dừng import nếu gặp lỗi
                }
            }
            loadData();
        }
    }//GEN-LAST:event_btnImportExcelActionPerformed

    private void btnExportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExcelActionPerformed
        JTable table = new JTable();
        List<StaffDTO> allStaffs = staffService.getAllStaffs();
        String[] columns = {"STT", "ID", "Họ và Tên", "Ngày Sinh", "Lương", "SĐT", "Tên Đăng Nhập", "Vai Trò"};
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columns);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int stt = 1;
        
        for (StaffDTO s : allStaffs) {
            Object[] row = new Object[]{
                    stt++,
                    s.getId(),
                    s.getFullName(),
                    s.getBirthday() != null ? s.getBirthday().format(formatter) : "",
                    FormatterUtil.toVND(s.getSalary()),
                    s.getPhoneNumber(),
                    s.getUserName(),
                    roleService.getRoleById(s.getRoleId()).getName()

            };
            model.addRow(row);
        }
        ExcelHelper.exportToExcel(table, "Danh_sach_nhan_vien.xlsx", "Danh sách nhân viên");
    }//GEN-LAST:event_btnExportExcelActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddOrUpdateStaffDialog dialog = new AddOrUpdateStaffDialog(parentFrame, true, null);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);

        // Sau khi đóng dialog, reload dữ liệu
        if (dialog.isConfirmed()) {
            loadData();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = tblStaff.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn nhân viên cần xóa",
                    "Chưa chọn nhân viên",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idObj = tblStaff.getValueAt(selectedRow, 0);
        Object nameObj = tblStaff.getValueAt(selectedRow, 1);
        int id = Integer.parseInt(idObj.toString());
        String name = nameObj.toString();

        int choose = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa nhân viên \"" + name + "\"?\n" +
                        "Hành động này KHÔNG THỂ HOÀN TÁC!",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        
        if (choose != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            staffService.deleteStaff(id);
            JOptionPane.showMessageDialog(this,
                    "Xóa nhân viên thành công!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Không thể xóa",
                    JOptionPane.ERROR_MESSAGE);
        }        txtSearch.setText("");
        searchKey = null;
        loadData();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void initTable(){
        DefaultTableModel model = (DefaultTableModel) tblStaff.getModel();
        model.setRowCount(0);
        String[] headers = {
                "ID",
                "HỌ VÀ TÊN",
                "NGÀY SINH",
                "MỨC LƯƠNG",
                "SỐ ĐIỆN THOẠI",
                "TÊN ĐĂNG NHẬP",
                "VAI TRÒ"
        };
        model.setColumnIdentifiers(headers);
    }
    private void updateDataOnShow() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        new Thread(() -> {
//            if(!isFirstLoad) staffService.refreshCache();
            if(isFirstLoad) {
                isFirstLoad = false;
            }
            SwingUtilities.invokeLater(() -> {

                loadData();
                setCursor(Cursor.getDefaultCursor());
            });
        }).start();
    }

    private void loadData(){
        List<StaffDTO> result = null;
        if(searchKey == null) {
            result = staffService.getAllStaffs();
        }
        else{
            result = staffService.filterStaffByList(searchKey);
        }

        // 4. Đổ dữ liệu
        fillTable(result);

    }
    private void fillTable(List<StaffDTO> staffs) {
        DefaultTableModel model = (DefaultTableModel) tblStaff.getModel();
        model.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (StaffDTO staff : staffs) {
            Object[] row = new Object[]{
                    staff.getId(),
                    staff.getLastName() + " " + staff.getFirstName(),
                    staff.getBirthday().format(formatter),
                    FormatterUtil.toVND(staff.getSalary()),
                    staff.getPhoneNumber(),
                    staff.getUserName(),
                    roleService.getRoleById(staff.getRoleId()).getName()
            };
            model.addRow(row);
        }

        // Căn giữa tất cả các cột
        for (int i = 0; i < tblStaff.getColumnCount(); i++) {
            tblStaff.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    private void setCss() {
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm theo tên nhân viên");

        TableColumnModel col = tblStaff.getColumnModel();

        // ID
        col.getColumn(0).setPreferredWidth(50);
        col.getColumn(0).setMaxWidth(60);

        // Họ và Tên (quan trọng: KHÔNG set MaxWidth)
        col.getColumn(1).setPreferredWidth(180);

        // Số Điện Thoại
        col.getColumn(2).setPreferredWidth(120);
        col.getColumn(2).setMaxWidth(150);

        // Tên Đăng Nhập
        col.getColumn(3).setPreferredWidth(120);
        col.getColumn(3).setMaxWidth(150);

        // Ngày Sinh
        col.getColumn(4).setPreferredWidth(100);
        col.getColumn(4).setMaxWidth(120);

        // Mức Lương
        col.getColumn(5).setPreferredWidth(120);
        col.getColumn(5).setMaxWidth(150);

        // Vai Trò
        col.getColumn(6).setPreferredWidth(120);
        col.getColumn(6).setMaxWidth(150);

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExportExcel;
    private javax.swing.JButton btnImportExcel;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblStaff;
    private com.fat.GUI.Components.PaginationPanel paginationPanel1;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
