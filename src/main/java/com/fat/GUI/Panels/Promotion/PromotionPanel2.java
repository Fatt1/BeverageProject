package com.fat.GUI.Panels.Promotion;

import com.fat.BUS.Abstractions.Services.IProductService;
import com.fat.BUS.Abstractions.Services.IPromotionService;
import com.fat.BUS.Services.ProductService;
import com.fat.BUS.Services.PromotionService;
import com.fat.DTO.Products.ProductDTO;
import com.fat.DTO.Promotions.PromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;
import com.fat.GUI.Dialogs.Promotions.SelectProductDialog;
import com.fat.GUI.MainForm;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Panel thêm/sửa chương trình khuyến mãi.
 * Add mode: promotionId == null
 * Edit mode: promotionId != null
 */
public class PromotionPanel2 extends javax.swing.JPanel {

    private final IPromotionService promotionService;
    private final IProductService productService;
    private Integer promotionId = null;
    private boolean isUpdating = false;

    // Display data for each product row
    private final List<ProductRowData> productList = new ArrayList<>();

    // UI components
    private JTextField txtName;
    private JDateChooser txtStartDate;
    private JDateChooser txtEndDate;
    private JTable tblProducts;
    private JTextField txtBatchDiscount;
    private JLabel lblSelectedCount;
    private JButton btnSave;
    private JButton btnCancel;

    /**
     * Inner helper class for product row display data.
     */
    private static class ProductRowData {
        int productId;
        String productName;
        BigDecimal originalPrice;
        BigDecimal discountPercentage; // 0-100

        ProductRowData(int productId, String productName, BigDecimal originalPrice, BigDecimal discountPercentage) {
            this.productId = productId;
            this.productName = productName;
            this.originalPrice = originalPrice;
            this.discountPercentage = discountPercentage;
        }

        BigDecimal getDiscountedPrice() {
            return originalPrice.multiply(
                BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
            ).setScale(0, RoundingMode.HALF_UP);
        }
    }

    // ==================== Constructors ====================

    /**
     * Add mode constructor
     */
    public PromotionPanel2() {
        promotionService = PromotionService.getInstance();
        productService = ProductService.getInstance();
        initUI();
        setupTable();
    }

    /**
     * Edit mode constructor
     */
    public PromotionPanel2(int promotionId) {
        promotionService = PromotionService.getInstance();
        productService = ProductService.getInstance();
        this.promotionId = promotionId;
        initUI();
        setupTable();
        loadPromotionData(promotionId);
    }

    // ==================== UI Setup ====================

    private void initUI() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(0, 0));

        // ===== TOP: Form fields =====
        JPanel topPanel = createFormPanel();
        add(topPanel, BorderLayout.NORTH);

        // ===== CENTER: Product table + toolbar =====
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        // ===== BOTTOM: Save / Cancel buttons =====
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        // Title
        JLabel lblTitle = new JLabel(promotionId == null ? "THÊM CHƯƠNG TRÌNH KHUYẾN MÃI" : "SỬA CHƯƠNG TRÌNH KHUYẾN MÃI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Name field
        JLabel lblName = new JLabel("Tên chương trình:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtName.setPreferredSize(new Dimension(400, 35));

        // Start date field
        JLabel lblStart = new JLabel("Ngày áp dụng:");
        lblStart.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtStartDate = new JDateChooser();
        txtStartDate.setDateFormatString("dd/MM/yyyy");
        txtStartDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtStartDate.setPreferredSize(new Dimension(220, 35));
        txtStartDate.setDate(new Date());

        // End date field
        JLabel lblEnd = new JLabel("Ngày hết hạn:");
        lblEnd.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEndDate = new JDateChooser();
        txtEndDate.setDateFormatString("dd/MM/yyyy");
        txtEndDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEndDate.setPreferredSize(new Dimension(220, 35));
        txtEndDate.setDate(new Date());

        // Layout using GroupLayout
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(txtName, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))
            )
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblStart)
                    .addComponent(txtStartDate, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblEnd)
                    .addComponent(txtEndDate, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
            )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(lblTitle)
            .addGap(15)
            .addComponent(lblName)
            .addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addGap(10)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblStart)
                .addComponent(lblEnd))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(txtStartDate, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addComponent(txtEndDate, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
        );

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 40, 0, 40));

        // Toolbar
        JPanel toolbar = createToolbar();
        panel.add(toolbar, BorderLayout.NORTH);

        // Product table
        tblProducts = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblProducts);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Left: action buttons
        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftButtons.setBackground(Color.WHITE);

        JButton btnAddProduct = createButton("Thêm hàng hóa", new Color(51, 51, 51), Color.WHITE);
        btnAddProduct.addActionListener(e -> addProductAction());

        JButton btnImportExcel = createButton("Nhập Excel", new Color(46, 125, 50), Color.WHITE);
        btnImportExcel.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Chức năng đang phát triển!", "Thông báo", JOptionPane.INFORMATION_MESSAGE)
        );

        JButton btnDeleteProduct = createButton("Xóa", new Color(255, 0, 0), Color.WHITE);
        btnDeleteProduct.addActionListener(e -> deleteSelectedProducts());

        leftButtons.add(btnAddProduct);
        leftButtons.add(btnImportExcel);
        leftButtons.add(btnDeleteProduct);

        // Right: batch update
        JPanel rightTools = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightTools.setBackground(Color.WHITE);

        lblSelectedCount = new JLabel("0 sản phẩm");
        lblSelectedCount.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel lblBatchLabel = new JLabel("Khuyến mãi (%):");
        lblBatchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        txtBatchDiscount = new JTextField(5);
        txtBatchDiscount.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBatchDiscount.setPreferredSize(new Dimension(70, 30));

        JButton btnBatchUpdate = new JButton("Cập nhật hàng loạt");
        btnBatchUpdate.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBatchUpdate.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        btnBatchUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBatchUpdate.addActionListener(e -> batchUpdateDiscount());

        rightTools.add(lblSelectedCount);
        rightTools.add(lblBatchLabel);
        rightTools.add(txtBatchDiscount);
        rightTools.add(btnBatchUpdate);

        toolbar.add(leftButtons, BorderLayout.WEST);
        toolbar.add(rightTools, BorderLayout.EAST);

        return toolbar;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 40, 15, 40));

        btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCancel.setPreferredSize(new Dimension(100, 38));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> cancelAction());

        btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(Color.BLACK);
        btnSave.setForeground(Color.WHITE);
        btnSave.setPreferredSize(new Dimension(100, 38));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> saveAction());

        panel.add(btnCancel);
        panel.add(btnSave);

        return panel;
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 20, 38));
        return btn;
    }

    // ==================== Table Setup ====================

    private void setupTable() {
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"#", "Mã SP", "Tên SP", "Giá gốc", "Giảm giá (%)", "Giá sau giảm"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only discount % is editable
            }
        };
        tblProducts.setModel(model);

        tblProducts.setRowHeight(30);
        tblProducts.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblProducts.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblProducts.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblProducts.setRowSelectionAllowed(true);
        tblProducts.setColumnSelectionAllowed(false);
        tblProducts.setSelectionBackground(new Color(0, 120, 215));
        tblProducts.setSelectionForeground(Color.WHITE);

        // Column widths
        tblProducts.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblProducts.getColumnModel().getColumn(1).setPreferredWidth(80);
        tblProducts.getColumnModel().getColumn(2).setPreferredWidth(250);
        tblProducts.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblProducts.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblProducts.getColumnModel().getColumn(5).setPreferredWidth(120);

        // Listen for edits on discount column
        model.addTableModelListener(e -> {
            if (isUpdating) return;
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int col = e.getColumn();
                if (col == 4) {
                    validateAndUpdateDiscount(row);
                }
            }
        });

        // Update selected count on selection change
        tblProducts.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int count = tblProducts.getSelectedRowCount();
                lblSelectedCount.setText(count > 0 ? count + " sản phẩm được chọn" : productList.size() + " sản phẩm");
            }
        });
    }

    // ==================== Data Loading ====================

    private void loadPromotionData(int promotionId) {
        try {
            PromotionDTO promotion = promotionService.getPromotionById(promotionId);
            if (promotion == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                navigateBack();
                return;
            }

            txtName.setText(promotion.getName());
            txtStartDate.setDate(Date.from(promotion.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            txtEndDate.setDate(Date.from(promotion.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            // Load product details
            List<PromotionDetailDTO> details = promotionService.getPromotionDetails(promotionId);
            if (details != null) {
                for (PromotionDetailDTO detail : details) {
                    ProductDTO product = productService.getProductById(detail.getProductId());
                    if (product != null) {
                        productList.add(new ProductRowData(
                            detail.getProductId(),
                            product.getName(),
                            product.getPrice(),
                            detail.getDiscountPercentage()
                        ));
                    }
                }
            }
            fillTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillTable() {
        isUpdating = true;
        try {
            DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
            model.setRowCount(0);

            int index = 1;
            for (ProductRowData item : productList) {
                model.addRow(new Object[]{
                    index++,
                    item.productId,
                    item.productName,
                    formatCurrency(item.originalPrice),
                    item.discountPercentage + "%",
                    formatCurrency(item.getDiscountedPrice())
                });
            }
            lblSelectedCount.setText(productList.size() + " sản phẩm");
        } finally {
            isUpdating = false;
        }
    }

    // ==================== Actions ====================

    private void addProductAction() {
        // Collect existing product IDs to exclude
        List<Integer> existingIds = productList.stream()
            .map(p -> p.productId)
            .collect(Collectors.toList());

        // Open select product dialog
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        SelectProductDialog dialog = new SelectProductDialog(parentFrame, true, existingIds);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        // Get selected products
        List<PromotionDetailDTO> selected = dialog.getSelectedProducts();
        if (selected != null && !selected.isEmpty()) {
            for (PromotionDetailDTO detail : selected) {
                boolean exists = productList.stream().anyMatch(p -> p.productId == detail.getProductId());
                if (!exists) {
                    ProductDTO product = productService.getProductById(detail.getProductId());
                    if (product != null) {
                        productList.add(new ProductRowData(
                            detail.getProductId(),
                            product.getName(),
                            product.getPrice(),
                            detail.getDiscountPercentage() != null ? detail.getDiscountPercentage() : BigDecimal.ZERO
                        ));
                    }
                }
            }
            fillTable();
        }
    }

    private void deleteSelectedProducts() {
        int[] selectedRows = tblProducts.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int i = selectedRows.length - 1; i >= 0; i--) {
            if (selectedRows[i] < productList.size()) {
                productList.remove(selectedRows[i]);
            }
        }
        fillTable();
    }

    private void batchUpdateDiscount() {
        String text = txtBatchDiscount.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập % khuyến mãi!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            BigDecimal discountPercent = new BigDecimal(text);
            if (discountPercent.compareTo(BigDecimal.ZERO) < 0 || discountPercent.compareTo(BigDecimal.valueOf(100)) > 0) {
                JOptionPane.showMessageDialog(this, "% khuyến mãi phải từ 0 đến 100!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int[] selectedRows = tblProducts.getSelectedRows();
            if (selectedRows.length == 0) {
                for (ProductRowData item : productList) {
                    item.discountPercentage = discountPercent;
                }
            } else {
                for (int row : selectedRows) {
                    if (row < productList.size()) {
                        productList.get(row).discountPercentage = discountPercent;
                    }
                }
            }
            fillTable();
            JOptionPane.showMessageDialog(this, "Đã cập nhật % khuyến mãi!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "% khuyến mãi không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validateAndUpdateDiscount(int row) {
        isUpdating = true;
        try {
            DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
            Object value = model.getValueAt(row, 4);
            String valueStr = value.toString().replaceAll("[^0-9.]", "");

            if (valueStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Phần trăm giảm giá không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                fillTable();
                return;
            }

            BigDecimal discountPercent = new BigDecimal(valueStr);
            if (discountPercent.compareTo(BigDecimal.ZERO) < 0 || discountPercent.compareTo(BigDecimal.valueOf(100)) > 0) {
                JOptionPane.showMessageDialog(this, "Phần trăm giảm giá phải từ 0 đến 100!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                fillTable();
                return;
            }

            if (row < productList.size()) {
                productList.get(row).discountPercentage = discountPercent;
            }

            fillTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            fillTable();
        } finally {
            isUpdating = false;
        }
    }

    private void saveAction() {
        if (!validateInput()) {
            return;
        }

        try {
            String name = txtName.getText().trim();
            LocalDate startDate = txtStartDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = txtEndDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (endDate.isBefore(startDate)) {
                JOptionPane.showMessageDialog(this, "Ngày hết hạn phải sau ngày áp dụng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convert ProductRowData to PromotionDetailDTO list
            List<PromotionDetailDTO> details = new ArrayList<>();
            for (ProductRowData item : productList) {
                details.add(new PromotionDetailDTO(
                    promotionId,
                    item.productId,
                    item.discountPercentage
                ));
            }

            // Build PromotionDTO
            PromotionDTO dto = new PromotionDTO();
            dto.setName(name);
            dto.setStartDate(startDate);
            dto.setEndDate(endDate);
            dto.setPromotionDetails(details);

            if (promotionId == null) {
                // Add mode
                promotionService.createPromotion(dto);
                JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Update mode
                dto.setId(promotionId);
                promotionService.updatePromotion(dto);
                JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }

            navigateBack();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void cancelAction() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn hủy? Dữ liệu chưa lưu sẽ bị mất.",
            "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            navigateBack();
        }
    }

    // ==================== Validation ====================

    private boolean validateInput() {
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên chương trình!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtName.requestFocus();
            return false;
        }
        if (txtStartDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày áp dụng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtStartDate.requestFocus();
            return false;
        }
        if (txtEndDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày hết hạn!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtEndDate.requestFocus();
            return false;
        }
        if (productList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một sản phẩm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // ==================== Navigation ====================

    private void navigateBack() {
        MainForm mainForm = (MainForm) SwingUtilities.getWindowAncestor(this);
        if (mainForm != null) {
            mainForm.showPanel("PROMOTIONS");
        }
    }

    // ==================== Utility ====================

    private static String formatCurrency(BigDecimal value) {
        if (value == null) return "0";
        return String.format("%,.0f", value);
    }
}
