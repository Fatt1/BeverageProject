/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.fat.GUI.Panels.Promotion;

import com.fat.BUS.Abstractions.Services.IPromotionService;
import com.fat.BUS.Services.PromotionService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Promotions.CreateOrUpdatePromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;
import com.fat.DTO.Promotions.PromotionItemDTO;
import com.fat.DTO.Promotions.PromotionItemDetailDTO;
import com.fat.GUI.Dialogs.Promotions.SelectProductDialog;
import com.fat.GUI.MainForm;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author koro1
 */
public class PromotionPanel2 extends javax.swing.JPanel {

    private IPromotionService promotionService;
    private Integer promotionId = null; // null = Add mode, có giá trị = Update mode
    private List<PromotionItemDetailDTO> productList = new ArrayList<>();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private boolean isUpdating = false; // Flag để ngăn đệ quy khi update table
    
    /**
     * Creates new form AddOrUpdatePromotionPanel
     */
    public PromotionPanel2() {
        initComponents();
        promotionService = PromotionService.getInstance();
        setupTable();
    }
    
    /**
     * Constructor cho chế độ Update
     */
    public PromotionPanel2(int promotionId) {
        initComponents();
        promotionService = PromotionService.getInstance();
        this.promotionId = promotionId;
        setupTable();
        loadPromotionData(promotionId);
    }
    
    private void setupTable() {
        // Thiết lập model cho bảng
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"#", "Mã SP", "Tên SP", "Giá gốc", "Giá sau giảm", "Giảm giá (%)"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép sửa cột Giá sau giảm (4) và Giảm giá (5)
                return column == 4 || column == 5;
            }
        };
        jTable1.setModel(model);
        
        // Highlight cả dòng khi chọn với màu xanh
        jTable1.setRowSelectionAllowed(true);
        jTable1.setColumnSelectionAllowed(false);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setSelectionBackground(new java.awt.Color(0, 120, 215));
        jTable1.setSelectionForeground(java.awt.Color.WHITE);
        
        // Xóa focus border của cell
        jTable1.setFocusable(false);
        
        // Thêm listener để validate khi sửa giá
        model.addTableModelListener(e -> {
            if (isUpdating) return; // Ngăn đệ quy
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int col = e.getColumn();
                if (col == 4 || col == 5) {
                    validateAndUpdatePrice(row, col);
                }
            }
        });
        
        // Thêm placeholder % cho textfield khừng khi
        jTextField4.setText("%");
        jTextField4.setForeground(java.awt.Color.GRAY);
        jTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField4.getText().equals("%")) {
                    jTextField4.setText("");
                    jTextField4.setForeground(java.awt.Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField4.getText().isEmpty()) {
                    jTextField4.setText("%");
                    jTextField4.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }
    
    private void loadPromotionData(int promotionId) {
        try {
            PagedResult<PromotionDetailDTO> result = promotionService.getPromotionById(promotionId);
            if (result != null && !result.getItems().isEmpty()) {
                PromotionDetailDTO promotion = result.getItems().get(0);
                jTextField2.setText(promotion.getName()); // Tên chương trình
                jTextField3.setText(promotion.getStartDate().format(dateFormatter)); // Ngày áp dụng
                jTextField1.setText(promotion.getEndDate().format(dateFormatter)); // Ngày hết hạn
                
                // Load danh sách sản phẩm trong promotion
                productList = new ArrayList<>(promotion.getPromotionItems());
                fillTable();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        int index = 1;
        for (PromotionItemDetailDTO detail : productList) {
            BigDecimal originalPrice = detail.getOriginalPrice();
            double discountPercent = detail.getDiscountPercentage();
            BigDecimal discountedPrice = originalPrice.multiply(
                BigDecimal.ONE.subtract(BigDecimal.valueOf(discountPercent / 100))
            ).setScale(0, RoundingMode.HALF_UP);
            
            model.addRow(new Object[]{
                index++,
                detail.getProductId(),
                detail.getProductName(),
                originalPrice,
                discountedPrice,
                discountPercent + "%"
            });
        }
        
        updateSelectedCount();
    }
    
    private void updateSelectedCount() {
        jLabel4.setText(String.valueOf(productList.size()));
    }
    
    private void validateAndUpdatePrice(int row, int column) {
        isUpdating = true; // Bật flag để ngăn đệ quy
        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Object value = model.getValueAt(row, column);
            BigDecimal originalPrice = (BigDecimal) model.getValueAt(row, 3);
            
            if (column == 4) {
                // Sửa Giá sau giảm
                String valueStr = value.toString().replaceAll("[^0-9]", "");
                if (valueStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Giá sau giảm không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    fillTable();
                    return;
                }
                BigDecimal newPrice = new BigDecimal(valueStr);
                if (newPrice.compareTo(BigDecimal.ZERO) < 0 || newPrice.compareTo(originalPrice) > 0) {
                    JOptionPane.showMessageDialog(this, "Giá sau giảm phải từ 0 đến giá gốc!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    fillTable();
                    return;
                }
                // Tính lại % giảm giá
                double discountPercent = 100 - (newPrice.doubleValue() / originalPrice.doubleValue() * 100);
                discountPercent = Math.round(discountPercent * 100.0) / 100.0;
                
                // Cập nhật productList
                int productId = (int) model.getValueAt(row, 1);
                for (PromotionItemDetailDTO item : productList) {
                    if (item.getProductId() == productId) {
                        item.setDiscountPercentage(discountPercent);
                        break;
                    }
                }
                // Cập nhật cột % giảm giá
                model.setValueAt(discountPercent + "%", row, 5);
                model.setValueAt(newPrice.setScale(0, RoundingMode.HALF_UP), row, 4);
                
            } else if (column == 5) {
                // Sửa Giảm giá (%)
                String valueStr = value.toString().replaceAll("[^0-9]", "");
                if (valueStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Phần trăm giảm giá không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    fillTable();
                    return;
                }
                double discountPercent = Double.parseDouble(valueStr);
                if (discountPercent < 0 || discountPercent > 100) {
                    JOptionPane.showMessageDialog(this, "Phần trăm giảm giá phải từ 0 đến 100!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    fillTable();
                    return;
                }
                // Tính lại giá sau giảm
                BigDecimal newPrice = originalPrice.multiply(
                    BigDecimal.ONE.subtract(BigDecimal.valueOf(discountPercent / 100))
                ).setScale(0, RoundingMode.HALF_UP);
                
                // Cập nhật productList
                int productId = (int) model.getValueAt(row, 1);
                for (PromotionItemDetailDTO item : productList) {
                    if (item.getProductId() == productId) {
                        item.setDiscountPercentage(discountPercent);
                        break;
                    }
                }
                // Cập nhật cột giá sau giảm
                model.setValueAt(newPrice, row, 4);
                model.setValueAt(discountPercent + "%", row, 5);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            fillTable();
        } finally {
            isUpdating = false; // Tắt flag
        }
    }
    
    public void resetForm() {
        promotionId = null;
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField1.setText("");
        jTextField4.setText("");
        productList.clear();
        fillTable();
    }
    
    private boolean validateInput() {
        if (jTextField2.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên chương trình!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (jTextField3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày áp dụng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (jTextField1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày hết hạn!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (productList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một sản phẩm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void navigateBackToList() {
        // Quay về Panel 1
        MainForm mainForm = (MainForm) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (mainForm != null) {
            mainForm.showPanel("PROMOTIONS");
        }
    }
    
    /**
     * Thêm sản phẩm vào danh sách (được gọi từ dialog chọn sản phẩm)
     */
    public void addProduct(PromotionItemDetailDTO product) {
        // Kiểm tra sản phẩm đã tồn tại chưa
        for (PromotionItemDetailDTO item : productList) {
            if (item.getProductId() == product.getProductId()) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã có trong danh sách!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        productList.add(product);
        fillTable();
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
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnImportExcel = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ngày áp dụng"));
        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên chương trình giảm giá"));

        jTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ngày áp dụng"));
        jTextField3.addActionListener(this::jTextField3ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(218, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(45, 45, 45)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(39, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setText("Hủy");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton4.setBackground(new java.awt.Color(0, 0, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Save.png"))); // NOI18N
        jButton4.setText("Lưu");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(680, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addGap(3, 3, 3))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnAdd.setBackground(new java.awt.Color(51, 51, 51));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Xbox Cross.png"))); // NOI18N
        btnAdd.setText("Thêm hàng hóa vào danh sách");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.addActionListener(this::btnAddActionPerformed);

        btnImportExcel.setBackground(new java.awt.Color(46, 125, 50));
        btnImportExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnImportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Microsoft Excel.png"))); // NOI18N
        btnImportExcel.setText("Nhập Excel");
        btnImportExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImportExcel.addActionListener(this::btnImportExcelActionPerformed);

        btnDelete.setBackground(new java.awt.Color(255, 0, 0));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Close.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(346, 346, 346)
                .addComponent(btnDelete)
                .addGap(30, 30, 30)
                .addComponent(btnImportExcel)
                .addGap(25, 25, 25)
                .addComponent(btnAdd))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Cập nhật hàng loạt");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 0, 0));
        jButton3.setText("Xóa");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Khuyến mãi");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText(" sản phẩm được chọn");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Thiết lập hàng loạt");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addComponent(jLabel3)
                    .addContainerGap(666, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(22, 22, 22))))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jLabel3)
                    .addContainerGap(52, Short.MAX_VALUE)))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã sản phẩm", "Tên sản phẩm", "Giá gốc", "Giá sau giảm", "Giảm giá"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel4, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Cập nhật hàng loạt - áp dụng % giảm giá cho tất cả sản phẩm đã chọn
        String discountText = jTextField4.getText().trim();
        if (discountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập % khuyến mãi!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            double discountPercent = Double.parseDouble(discountText);
            if (discountPercent < 0 || discountPercent > 100) {
                JOptionPane.showMessageDialog(this, "% khuyến mãi phải từ 0 đến 100!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int[] selectedRows = jTable1.getSelectedRows();
            if (selectedRows.length == 0) {
                // Nếu không chọn row nào thì áp dụng cho tất cả
                for (int i = 0; i < productList.size(); i++) {
                    PromotionItemDetailDTO old = productList.get(i);
                    productList.set(i, new PromotionItemDetailDTO(
                        old.getPromotionId(), old.getProductName(), old.getOriginalPrice(), discountPercent, old.getProductId()
                    ));
                }
            } else {
                // Áp dụng cho các row được chọn
                for (int row : selectedRows) {
                    PromotionItemDetailDTO old = productList.get(row);
                    productList.set(row, new PromotionItemDetailDTO(
                        old.getPromotionId(), old.getProductName(), old.getOriginalPrice(), discountPercent, old.getProductId()
                    ));
                }
            }
            fillTable();
            JOptionPane.showMessageDialog(this, "Đã cập nhật % khuyến mãi!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "% khuyến mãi không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Hủy - quay về Panel 1
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn hủy? Dữ liệu chưa lưu sẽ bị mất.", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            navigateBackToList();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Lưu promotion
        if (!validateInput()) {
            return;
        }
        
        try {
            String name = jTextField2.getText().trim();
            LocalDate startDate = LocalDate.parse(jTextField3.getText().trim(), dateFormatter);
            LocalDate endDate = LocalDate.parse(jTextField1.getText().trim(), dateFormatter);
            
            if (endDate.isBefore(startDate)) {
                JOptionPane.showMessageDialog(this, "Ngày hết hạn phải sau ngày áp dụng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Chuyển đổi từ PromotionItemDetailDTO sang PromotionItemDTO
            List<PromotionItemDTO> items = new ArrayList<>();
            for (PromotionItemDetailDTO detail : productList) {
                items.add(new PromotionItemDTO(detail.getProductId(), detail.getDiscountPercentage()));
            }
            
            if (promotionId == null) {
                // Add mode
                CreateOrUpdatePromotionDTO dto = new CreateOrUpdatePromotionDTO(name, startDate, endDate, items);
                promotionService.createPromotion(dto);
                JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Update mode
                CreateOrUpdatePromotionDTO dto = new CreateOrUpdatePromotionDTO(promotionId, name, startDate, endDate, items);
                promotionService.updatePromotion(dto);
                JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
            
            navigateBackToList();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ! (dd/MM/yyyy)", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // Xóa các sản phẩm được chọn khỏi danh sách
        int[] selectedRows = jTable1.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xóa từ cuối lên để không bị lệch index
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            productList.remove(selectedRows[i]);
        }
        fillTable();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnImportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportExcelActionPerformed
        // TODO: Implement import từ Excel
        JOptionPane.showMessageDialog(this, "Chức năng đang phát triển!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnImportExcelActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Lấy danh sách productId đã có trong bảng
        List<Integer> existingIds = new ArrayList<>();
        for (PromotionItemDetailDTO item : productList) {
            existingIds.add(item.getProductId());
        }
        
        // Mở dialog chọn sản phẩm
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        SelectProductDialog dialog = new SelectProductDialog(parentFrame, true, existingIds);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
        // Sau khi dialog đóng, lấy danh sách sản phẩm đã chọn
        List<PromotionItemDetailDTO> selectedProducts = dialog.getSelectedProducts();
        if (!selectedProducts.isEmpty()) {
            for (PromotionItemDetailDTO product : selectedProducts) {
                addProduct(product);
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnImportExcel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
