/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.fat.GUI.Dialogs.Promotions;

import com.fat.BUS.Abstractions.Services.ICategoryService;
import com.fat.BUS.Abstractions.Services.IProductService;
import com.fat.BUS.Services.CategoryService;
import com.fat.BUS.Services.ProductService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Categories.CategoryDTO;
import com.fat.DTO.Products.ProductDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;

import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author koro1
 */
public class SelectProductDialog extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SelectProductDialog.class.getName());
    
    private IProductService productService;
    private ICategoryService categoryService;
    private List<PromotionDetailDTO> selectedProducts = new ArrayList<>();
    private List<Integer> existingProductIds = new ArrayList<>();
    
    private String searchKey = null;
    private Integer selectedCategoryId = null;
    private int currentPage = 1;
    private int pageSize = 10;

    /**
     * Creates new form SelectProductDialog
     */
    public SelectProductDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        productService = ProductService.getInstance();
        categoryService = CategoryService.getInstance();
        setupTable();
        loadCategoryComboBox();
        loadData();
        setupListeners();
    }
    
    /**
     * Constructor với danh sách sản phẩm đã tồn tại
     */
    public SelectProductDialog(java.awt.Frame parent, boolean modal, List<Integer> existingProductIds) {
        this(parent, modal);
        this.existingProductIds = existingProductIds != null ? existingProductIds : new ArrayList<>();
        loadData();
    }
    
    private void setupTable() {
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Chọn", "ID", "Tên sản phẩm", "Đơn vị", "Giá", "Danh mục"},
            0
        ) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                return String.class;
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Chỉ cho phép edit checkbox
            }
        };
        jTable1.setModel(model);
        
        // Highlight cả dòng khi chọn với màu xanh
        jTable1.setRowSelectionAllowed(true);
        jTable1.setColumnSelectionAllowed(false);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTable1.setSelectionBackground(new java.awt.Color(0, 120, 215));
        jTable1.setSelectionForeground(java.awt.Color.WHITE);
        
        // Xóa focus border của cell
        jTable1.setFocusable(false);
        
        // Ẩn cột ID
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setWidth(0);
        
        // Thiết lập độ rộng cột
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
    }
    
    private void setupListeners() {
        // Tìm kiếm realtime
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKey = txtName.getText().trim();
                if (searchKey.isEmpty()) searchKey = null;
                currentPage = 1;
                loadData();
            }
        });
        
        // Nút Hủy
        jButton1.addActionListener(e -> {
            selectedProducts.clear();
            dispose();
        });
        
        // Nút Xác nhận
        jButton2.addActionListener(e -> {
            collectSelectedProducts();
            if (selectedProducts.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dispose();
        });
        
        // Cập nhật số lượng đã chọn
        jTable1.getModel().addTableModelListener(e -> updateSelectedCount());
    }
    
    private void loadCategoryComboBox() {
        cboCategory.removeAllItems();
        cboCategory.addItem("Tất cả");
        
        List<CategoryDTO> categories = categoryService.getAllCategories();
        for (CategoryDTO cat : categories) {
            cboCategory.addItem(cat.getName());
        }
    }
    
    private void loadData() {
        PagedResult<ProductDTO> result = productService.filterProductByList(searchKey, selectedCategoryId, currentPage, pageSize);
        fillTable(result);
    }
    
    private void fillTable(PagedResult<ProductDTO> result) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        for (ProductDTO p : result.getItems()) {
            if (existingProductIds.contains(p.getId())) {
                continue;
            }
            
            model.addRow(new Object[]{
                false,
                p.getId(),
                p.getName(),
                p.getUnit(),
                p.getPrice(), categoryService.getCategoryById(p.getCategoryId()).getName()
            });
        }
        
        int totalItems = result.getTotalItems();
        if (totalItems == 0) {
            lblPage.setText("0 trên 0");
        } else {
            int from = (currentPage - 1) * pageSize + 1;
            int to = Math.min(currentPage * pageSize, totalItems);
            lblPage.setText(from + " - " + to + " trên " + totalItems);
        }
    }
    
    private void updateSelectedCount() {
        int count = 0;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Boolean) model.getValueAt(i, 0)) {
                count++;
            }
        }
        jLabel2.setText("Đã chọn: " + count);
    }
    
    public List<PromotionDetailDTO> getSelectedProducts() {
        return selectedProducts;
    }
    
    private void collectSelectedProducts() {
        selectedProducts.clear();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Boolean) model.getValueAt(i, 0)) {
                int productId = (int) model.getValueAt(i, 1);
                String productName = (String) model.getValueAt(i, 2);
                BigDecimal price = (BigDecimal) model.getValueAt(i, 4);

                PromotionDetailDTO item = new PromotionDetailDTO(
                    null, productId, BigDecimal.ZERO
                );
                selectedProducts.add(item);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        cboCategory = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cboPageSize = new javax.swing.JComboBox<>();
        lblPage = new javax.swing.JLabel();
        firstBtn = new javax.swing.JButton();
        prevBtn = new javax.swing.JButton();
        nextBtn = new javax.swing.JButton();
        lastBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtName.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        cboCategory.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh mục"));
        cboCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cboCategory.addActionListener(this::cboCategoryActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(428, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(205, 205, 205));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Hủy");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Xác nhận thêm");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(567, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Kết quả tìm kiếm");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Đã chọn");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addContainerGap(557, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã sản phẩm", "Tên sản phẩm", "Đơn vị tính", "Danh mục"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Số mục mỗi trang");

        cboPageSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10 Trang", "25 Trang", "50 Trang", "100 Trang" }));
        cboPageSize.addActionListener(this::cboPageSizeActionPerformed);

        lblPage.setText("1 - 10 trên 11");

        firstBtn.setText("<<");
        firstBtn.addActionListener(this::firstBtnActionPerformed);

        prevBtn.setText("<");
        prevBtn.addActionListener(this::prevBtnActionPerformed);

        nextBtn.setText(">");
        nextBtn.addActionListener(this::nextBtnActionPerformed);

        lastBtn.setText(">>");
        lastBtn.addActionListener(this::lastBtnActionPerformed);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(218, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboPageSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPage)
                .addGap(26, 26, 26)
                .addComponent(firstBtn)
                .addGap(18, 18, 18)
                .addComponent(prevBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nextBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lastBtn)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastBtn)
                    .addComponent(firstBtn)
                    .addComponent(cboPageSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPage)
                    .addComponent(prevBtn)
                    .addComponent(nextBtn)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 763, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel3.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoryActionPerformed
        int index = cboCategory.getSelectedIndex();
        if (index <= 0) {
            selectedCategoryId = null;
        } else {
            List<CategoryDTO> categories = categoryService.getAllCategories();
            if (index - 1 < categories.size()) {
                selectedCategoryId = categories.get(index - 1).getId();
            }
        }
        currentPage = 1;
        loadData();
    }//GEN-LAST:event_cboCategoryActionPerformed

    private void cboPageSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPageSizeActionPerformed
        String selected = (String) cboPageSize.getSelectedItem();
        pageSize = Integer.parseInt(selected.replace(" Trang", ""));
        currentPage = 1;
        loadData();
    }//GEN-LAST:event_cboPageSizeActionPerformed

    private void firstBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstBtnActionPerformed
        if (currentPage > 1) {
            currentPage = 1;
            loadData();
        }
    }//GEN-LAST:event_firstBtnActionPerformed

    private void prevBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevBtnActionPerformed
        if (currentPage > 1) {
            currentPage--;
            loadData();
        }
    }//GEN-LAST:event_prevBtnActionPerformed

    private void nextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBtnActionPerformed
        PagedResult<ProductDTO> result = productService.filterProductByList(searchKey, selectedCategoryId, currentPage, pageSize);
        int totalPages = (int) Math.ceil((double) result.getTotalItems() / pageSize);
        if (currentPage < totalPages) {
            currentPage++;
            loadData();
        }
    }//GEN-LAST:event_nextBtnActionPerformed

    private void lastBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastBtnActionPerformed
        PagedResult<ProductDTO> result = productService.filterProductByList(searchKey, selectedCategoryId, currentPage, pageSize);
        int totalPages = (int) Math.ceil((double) result.getTotalItems() / pageSize);
        if (currentPage < totalPages) {
            currentPage = totalPages;
            loadData();
        }
    }//GEN-LAST:event_lastBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SelectProductDialog dialog = new SelectProductDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboCategory;
    private javax.swing.JComboBox<String> cboPageSize;
    private javax.swing.JButton firstBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton lastBtn;
    private javax.swing.JLabel lblPage;
    private javax.swing.JButton nextBtn;
    private javax.swing.JButton prevBtn;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
