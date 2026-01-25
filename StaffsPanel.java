// ===================================================================
// CODE HOÀN CHỈNH CHO StaffsPanel.java
// Copy toàn bộ code này vào file StaffsPanel.java của bạn
// (Thay thế phần code từ dòng 1 đến dòng 214)
// ===================================================================

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
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;
import com.fat.GUI.Dialogs.Staffs.AddOrUpdateStaffDialog;
import com.fat.GUI.Utils.FormatterUtil;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author tranc
 */
public class StaffsPanel extends javax.swing.JPanel {

    private IStaffService staffService;
    private IRoleService roleService;
    private String searchKey = null;
    private boolean isFirstLoad = true;

    /**
     * Creates new form StaffsPanel
     */
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

        paginationPanel1.addPaginationEventListener((pageIndex, pageSize) -> {
            loadData(pageIndex, pageSize);
        });
    }

    private void updateDataOnShow() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        new Thread(() -> {
            if (!isFirstLoad) {
                staffService.refreshCache();
                roleService.refreshCache();
            }
            if (isFirstLoad) {
                isFirstLoad = false;
            }
            SwingUtilities.invokeLater(() -> {
                loadData(1, 10);
                setCursor(Cursor.getDefaultCursor());
            });
        }).start();
    }

    private void initTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
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

    private void setCss() {
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên nhân viên");

        TableColumnModel col = jTable1.getColumnModel();
        // ID
        col.getColumn(0).setPreferredWidth(50);
        col.getColumn(0).setMaxWidth(60);
        // Họ và tên
        col.getColumn(1).setPreferredWidth(200);
        // Ngày sinh
        col.getColumn(2).setPreferredWidth(120);
        col.getColumn(2).setMaxWidth(150);
        // Mức lương
        col.getColumn(3).setPreferredWidth(150);
        col.getColumn(3).setMaxWidth(180);
        // SĐT
        col.getColumn(4).setPreferredWidth(130);
        col.getColumn(4).setMaxWidth(150);
        // Tên đăng nhập
        col.getColumn(5).setPreferredWidth(150);
        col.getColumn(5).setMaxWidth(180);
        // Vai trò
        col.getColumn(6).setPreferredWidth(120);
        col.getColumn(6).setMaxWidth(150);
        
        jTable1.setRowHeight(40);
    }

    private void loadData(int pageIndex, int pageSize) {
        List<StaffViewDTO> allStaffs;
        
        if (searchKey == null || searchKey.trim().isEmpty()) {
            allStaffs = staffService.getAllStaffs();
        } else {
            allStaffs = staffService.filterStaffByList(searchKey);
        }

        PagedResult<StaffViewDTO> result = PagedResult.create(
                allStaffs.stream(),
                allStaffs.size(),
                pageIndex,
                pageSize
        );

        fillTable(result.getItems());
        paginationPanel1.setPagedResult(result);
    }

    private void fillTable(List<StaffViewDTO> staffs) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (StaffViewDTO s : staffs) {
            Object[] row = new Object[]{
                    s.getId(),
                    s.getFullName(),
                    s.getBirthDate() != null ? s.getBirthDate().format(formatter) : "",
                    FormatterUtil.toVND(s.getSalary()),
                    s.getPhoneNumber(),
                    s.getUserName(),
                    s.getRoleName()
            };
            model.addRow(row);
        }

        // Căn giữa tất cả các cột
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: DO NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // CODE GENERATED BY NETBEANS - GIỮ NGUYÊN PHẦN NÀY
        // KHÔNG SỬA PHẦN GIỮA //GEN-BEGIN và //GEN-END
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSearch.setText("");
        searchKey = null;
        loadData(1, 10);
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            searchKey = txtSearch.getText().trim();
            loadData(1, 10);
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn nhân viên cần chỉnh sửa.",
                    "Chưa chọn nhân viên",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idObj = jTable1.getValueAt(selectedRow, 0);
        int id = Integer.parseInt(idObj.toString());
        
        StaffDetailDTO staffDetailDTO = staffService.getStaffById(id);
        
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddOrUpdateStaffDialog dialog = new AddOrUpdateStaffDialog(parentFrame, true, staffDetailDTO);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);

        // Sau khi đóng dialog, reload dữ liệu
        loadData(1, 10);
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

                    CreateOrUpdateStaffDTO staff = new CreateOrUpdateStaffDTO(
                            firstName, lastName, birthDate, salary, phoneNumber,
                            userName, password, roleId);
                    
                    staffService.createStaff(staff);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Dữ liệu trong file Excel không hợp lệ. Vui lòng kiểm tra lại.",
                            "Lỗi dữ liệu",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            loadData(1, 10);
        }
    }//GEN-LAST:event_btnImportExcelActionPerformed

    private void btnExportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExcelActionPerformed
        JTable table = new JTable();
        List<StaffViewDTO> allStaffs = staffService.getAllStaffs();
        String[] columns = {"STT", "ID", "Họ và Tên", "Ngày Sinh", "Lương", "SĐT", "Tên Đăng Nhập", "Vai Trò"};
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columns);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int stt = 1;
        
        for (StaffViewDTO s : allStaffs) {
            Object[] row = new Object[]{
                    stt++,
                    s.getId(),
                    s.getFullName(),
                    s.getBirthDate() != null ? s.getBirthDate().format(formatter) : "",
                    FormatterUtil.toVND(s.getSalary()),
                    s.getPhoneNumber(),
                    s.getUserName(),
                    s.getRoleName()
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
        loadData(1, 10);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn nhân viên cần xóa",
                    "Chưa chọn nhân viên",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idObj = jTable1.getValueAt(selectedRow, 0);
        Object nameObj = jTable1.getValueAt(selectedRow, 1);
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
            loadData(1, 10);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Không thể xóa",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExportExcel;
    private javax.swing.JButton btnImportExcel;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private com.fat.GUI.Components.PaginationPanel paginationPanel1;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
