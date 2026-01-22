/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.fat.GUI.Panels.Products;

import com.fat.BUS.Abstractions.Services.ICategoryService;
import com.fat.BUS.Abstractions.Services.IProductService;
import com.fat.BUS.Abstractions.Services.IUploadImageService;
import com.fat.BUS.Services.CategoryService;
import com.fat.BUS.Services.ProductService;
import com.fat.BUS.Services.UploadImageService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Categories.CategoryViewDTO;
import com.fat.DTO.Products.CreateOrUpdateProductDTO;
import com.fat.DTO.Products.ProductDetailDTO;
import com.fat.DTO.Products.ProductViewDTO;
import com.fat.GUI.Dialogs.Products.AddOrUpdateProductDialog;
import com.fat.BUS.Utils.ExcelHelper;
import com.fat.GUI.Utils.FormatterUtil;
import com.fat.GUI.Utils.ImageHelper;
import com.fat.GUI.Utils.ImageRenderer;
import com.formdev.flatlaf.FlatClientProperties;
import com.google.inject.Inject;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public class ProductsPanel extends javax.swing.JPanel {

    /**
     * Creates new form ProductsPanel
     */
    private IProductService productService;
    private ICategoryService categoryService;
    private Integer selectedCategoryId = null;
    private String searchKey = null;


    @Inject
    public ProductsPanel() {
        this.categoryService = CategoryService.getInstance();
        this.productService = ProductService.getInstance();
        initComponents();
        initalTable();
        setCss();
        loadCategories();

        // 1. Gọi DAO lấy dữ liệu phân trang

        paginationPanel1.addPaginationEventListener((pageIndex, pageSize) -> {
            loadData(pageIndex, pageSize);
        });

        // Load dữ liệu trang đầu tiên
        loadData(1, 10);
    }

    private void loadCategories() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCategory.getModel();

        CategoryViewDTO allCategory = new CategoryViewDTO(0, "Tất cả");
        model.addElement(allCategory);
        var categories = categoryService.getAllCategories();

        for (var c : categories) {
            model.addElement(c);
        }
        // Chọn mặc định tất cả
        cboCategory.setSelectedIndex(0);

    }

    private void fillTable(List<ProductViewDTO> products) {

        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (ProductViewDTO p : products) {
            Object[] row = new Object[]{
                    p.getId(),
                    ImageHelper.resizeImage(new ImageIcon(ImageHelper.getImagePath(p.getImage())), 60, 60),
                    p.getName(),
                    FormatterUtil.toVND(p.getPrice()),
                    p.getUnit(),
                    p.getStock(),
                    p.getCategoryName(),
            };
            model.addRow(row);

        }

        // Căn giữa tất cả các cột ngoại trừ cột hình ảnh
        for (int i = 0; i < tblProduct.getColumnCount(); i++) {
            if (i == 1) continue;
            tblProduct.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblProduct.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());

    }

    private void initalTable() {
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        // Dùng mảng String cho tiêu đề
        String[] headers = {
                "ID",
                "HÌNH SẢN PHẨM",
                "TÊN SẢN PHẨM",
                "GIÁ BÁN",
                "ĐƠN VỊ TÍNH",
                "TỒN KHO",
                "DANH MỤC"
        };
        model.setColumnIdentifiers(headers);

    }

    private void setCss() {
        String styleBtn = "" +
                "borderWidth: 0;";
        btnAdd.putClientProperty(FlatClientProperties.STYLE, styleBtn
        );
        btnDelete.putClientProperty(FlatClientProperties.STYLE, styleBtn
        );
        btnUpdate.putClientProperty(FlatClientProperties.STYLE, styleBtn
        );
        btnImportExcel.putClientProperty(FlatClientProperties.STYLE, styleBtn
        );
        btnExportExcel.putClientProperty(FlatClientProperties.STYLE, styleBtn
        );
        btnReset.putClientProperty(FlatClientProperties.STYLE, styleBtn
        );


        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên sản phẩm");



        TableColumnModel col = tblProduct.getColumnModel();
        col.getColumn(1).setCellRenderer(new ImageRenderer());
        tblProduct.setRowHeight(70);


        col.getColumn(0).setPreferredWidth(50);
        col.getColumn(0).setMaxWidth(60);
        // Hình
        col.getColumn(1).setPreferredWidth(170);
        col.getColumn(1).setMaxWidth(200);

        // Tên Sản Phẩm (Quan trọng: set số to và KHÔNG set MaxWidth)
        col.getColumn(2).setPreferredWidth(200);


        // Giá Bán
        col.getColumn(3).setPreferredWidth(150);
        col.getColumn(3).setMaxWidth(180);

        // Đơn Vị Tính
        col.getColumn(4).setPreferredWidth(100);
        col.getColumn(4).setMaxWidth(150);

        // Tồn Kho
        col.getColumn(5).setPreferredWidth(120);
        col.getColumn(5).setMaxWidth(150);


    }

    private void loadData(int pageIndex, int pageSize) {

        PagedResult<ProductViewDTO> result = null;

        if(searchKey == null && selectedCategoryId == null) {
            result = productService.getAllProductPagination(pageIndex, pageSize);
        }
        else{
            result = productService.filterProductByList(searchKey, selectedCategoryId, pageIndex, pageSize);
        }

        // 4. Đổ dữ liệu
        fillTable(result.getItems());

        // 5. Cập nhật thanh phân trang
        paginationPanel1.setPagedResult(result);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        cboCategory = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnImportExcel = new javax.swing.JButton();
        btnExportExcel = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        paginationPanel1 = new com.fat.GUI.Components.PaginationPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(985, 723));
        setLayout(new java.awt.BorderLayout());

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "HÌNH SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ BÁN", "ĐƠN VI TÍNH", "TỒN KHO", "DANH MỤC"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProduct.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblProduct);
        if (tblProduct.getColumnModel().getColumnCount() > 0) {
            tblProduct.getColumnModel().getColumn(0).setResizable(false);
            tblProduct.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblProduct.getColumnModel().getColumn(2).setMinWidth(100);
            tblProduct.getColumnModel().getColumn(4).setResizable(false);
            tblProduct.getColumnModel().getColumn(4).setPreferredWidth(10);
            tblProduct.getColumnModel().getColumn(5).setResizable(false);
            tblProduct.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

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

        cboCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cboCategory.addActionListener(this::cboCategoryActionPerformed);

        jLabel1.setText("Tên sản phẩm");

        jLabel2.setText("Nhóm sản phẩm");

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSearch)
                            .addComponent(cboCategory, 0, 224, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        add(jPanel1, java.awt.BorderLayout.NORTH);

        paginationPanel1.setBackground(new java.awt.Color(255, 255, 255));
        add(paginationPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int selectedRow = tblProduct.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn sản phẩm để chỉnh sửa.", "Chưa chọn sản phẩm", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object idObj = tblProduct.getValueAt(selectedRow, 0); // Cột ID
        int id = Integer.parseInt(idObj.toString());
        ProductDetailDTO productDetailDTO = productService.getProductById(id);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddOrUpdateProductDialog updateProductDialog = new AddOrUpdateProductDialog(parentFrame, true, productService, categoryService, productDetailDTO);
        updateProductDialog.setLocationRelativeTo(parentFrame);
        updateProductDialog.setVisible(true);

        // Sau khi đóng dialog, tải lại dữ liệu
        loadData(1, 10);


    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnExportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExcelActionPerformed
        JTable table = new JTable();
        List<ProductViewDTO> allProducts = productService.getAllProducts();
        String[] columns = {"STT","ID", "Tên Sản Phẩm", "Giá Bán", "Đơn Vị Tính", "Tồn Kho", "Danh Mục"};
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columns);
        for (ProductViewDTO p : allProducts) {
            Object[] row = new Object[]{
                    p.getId(),
                    p.getName(),
                    FormatterUtil.toVND(p.getPrice()),
                    p.getUnit(),
                    p.getStock(),
                    p.getCategoryName()
            };
            model.addRow(row);
        }
        ExcelHelper.exportToExcel(table, "Danh_sach_san_pham.xlsx", "Danh sách sản phẩm");
    }//GEN-LAST:event_btnExportExcelActionPerformed

    private void btnImportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportExcelActionPerformed
        var result =  ExcelHelper.readFromExcel();
        if(!result.isEmpty()) {
            String defaultImage = "no_image.jpg";
            IUploadImageService uploadImageService = new UploadImageService();
            for(var row: result) {
                try{

                    String name = row.get(0).toString();
                    BigDecimal price = new BigDecimal(row.get(1).toString());
                    String unit = row.get(2).toString();
                    int categoryId = (int)Double.parseDouble(row.get(3).toString());
                    String image = row.get(4).toString();
                    File sourceImage = new File(image);

                    if(sourceImage.exists()) {
                        String uploadedImageName = uploadImageService.uploadImage(sourceImage.getName(), sourceImage.toPath());
                        // Sử dụng tên file sau khi upload
                        image = uploadedImageName;
                    }
                    else {
                        image = defaultImage;
                    }

                    CreateOrUpdateProductDTO product = new CreateOrUpdateProductDTO(name, image, unit, price, categoryId);
                    productService.createProduct(product);
                }

                catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Dữ liệu trong file Excel không hợp lệ. Vui lòng kiểm tra lại.",
                            "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                }

            }

            loadData(1, 10);
        }
    }//GEN-LAST:event_btnImportExcelActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSearch.setText("");
        cboCategory.setSelectedIndex(0);
        selectedCategoryId = null;
        searchKey = null;
        loadData(1, 10);
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddOrUpdateProductDialog addOrUpdateProductDialog = new AddOrUpdateProductDialog(parentFrame, true, productService, categoryService, null);
        addOrUpdateProductDialog.setLocationRelativeTo(parentFrame);

        addOrUpdateProductDialog.setVisible(true);

        // Sau khi đóng dialog, tải lại dữ liệu
        loadData(1, 10);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed


       int selectedRow = tblProduct.getSelectedRow();
       if(selectedRow == -1) {
           JOptionPane.showConfirmDialog(this, "Vui lòng chọn sản phẩm để xóa", "Chưa chọn sản phẩm", JOptionPane.WARNING_MESSAGE);
           return;
       }

        int choose = JOptionPane.showConfirmDialog(this, "Bạn có chăc muốn xóa sản phẩm này", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if(choose != JOptionPane.YES_OPTION) {
            return;
        }
       Object idObj = tblProduct.getValueAt(selectedRow, 0);
       int id = Integer.parseInt(idObj.toString());
       productService.deleteProduct(id);
       loadData(1, 10);

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cboCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoryActionPerformed
        CategoryViewDTO selectedCategory = (CategoryViewDTO) cboCategory.getSelectedItem();
        if(selectedCategory != null && selectedCategory.getId() != 0) {
            selectedCategoryId = selectedCategory.getId();
        }
        else if(selectedCategory != null && selectedCategory.getId() == 0){
            selectedCategoryId = null;
        }
        loadData(1, 10);
    }//GEN-LAST:event_cboCategoryActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if(evt.getKeyChar() == KeyEvent.VK_ENTER) {
            searchKey = txtSearch.getText().trim();
            loadData(1, 10);
        }

    }//GEN-LAST:event_txtSearchKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExportExcel;
    private javax.swing.JButton btnImportExcel;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.fat.GUI.Components.PaginationPanel paginationPanel1;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
