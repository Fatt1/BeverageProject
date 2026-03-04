package com.fat.GUI.Panels.Promotion;

import com.fat.BUS.Abstractions.Services.IProductService;
import com.fat.BUS.Abstractions.Services.IPromotionService;
import com.fat.BUS.Services.ProductService;
import com.fat.BUS.Services.PromotionService;
import com.fat.DTO.Products.ProductDTO;
import com.fat.DTO.Promotions.PromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;
import com.fat.BUS.Utils.ExcelHelper;
import com.fat.GUI.MainForm;
import com.fat.GUI.Utils.FormatterUtil;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel quản lý chương trình khuyến mãi - layout giống ReceiptPanel
 */
public class PromotionPanelChanges extends javax.swing.JPanel {

    private IPromotionService promotionService;
    private IProductService productService;
    private Integer selectedStatus = null; // null = Tất cả, 0 = Sắp diễn ra, 1 = Đang diễn ra, 2 = Kết thúc
    private String searchKey = null;
    private PromotionDTO selectedPromotion = null;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ===== UI Components =====
    // Filter bar
    private JPanel filterPanel;
    private JTextField txtSearch;
    private JComboBox<String> cboStatus;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnReset;
    private JButton btnExportExcel;
    private JLabel lblSearchLabel;
    private JLabel lblStatusLabel;

    // Main content
    private JPanel contentPanel;
    private JScrollPane scrollPaneLeft;
    private JTable tblPromotions;
    private JPanel detailPanel;

    // Detail fields
    private JTextField txtPromotionId;
    private JTextField txtCreatedDate;
    private JTextField txtPromotionName;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JLabel lblPromotionId;
    private JLabel lblCreatedDate;
    private JLabel lblPromotionName;
    private JLabel lblStartDate;
    private JLabel lblEndDate;

    // Detail table
    private JScrollPane scrollPaneRight;
    private JTable tblPromotionDetails;

    public PromotionPanelChanges() {
        promotionService = PromotionService.getInstance();
        productService = ProductService.getInstance();
        initUI();
        setupTables();
        setupTableListener();
        setCss();
        loadStatusComboBox();
        loadData();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ===== TOP: Filter Panel =====
        filterPanel = new JPanel();
        filterPanel.setBackground(Color.WHITE);

        lblSearchLabel = new JLabel("Tên chương trình");
        lblStatusLabel = new JLabel("Trạng thái");

        txtSearch = new JTextField();
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    String text = txtSearch.getText().trim();
                    searchKey = text.isEmpty() ? null : text;
                    loadData();
                }
            }
        });

        cboStatus = new JComboBox<>();
        cboStatus.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        cboStatus.addActionListener(e -> {
            int index = cboStatus.getSelectedIndex();
            if (index == 0) {
                selectedStatus = null;
            } else {
                selectedStatus = index - 1;
            }
            loadData();
        });

        btnAdd = new JButton("Thêm mới");
        btnAdd.setBackground(new Color(51, 51, 51));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setIcon(new ImageIcon(getClass().getResource("/Icons/Xbox Cross.png")));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> btnAddAction());

        btnUpdate = new JButton("Chỉnh sửa");
        btnUpdate.setBackground(new Color(51, 51, 51));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setIcon(new ImageIcon(getClass().getResource("/Icons/Edit.png")));
        btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(e -> btnUpdateAction());

        btnDelete = new JButton("Xóa");
        btnDelete.setBackground(new Color(255, 0, 0));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setIcon(new ImageIcon(getClass().getResource("/Icons/Close.png")));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> btnDeleteAction());

        btnReset = new JButton("Hoàn tác lọc");
        btnReset.setBackground(new Color(141, 141, 141));
        btnReset.setForeground(Color.WHITE);
        btnReset.setIcon(new ImageIcon(getClass().getResource("/Icons/Reset.png")));
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReset.addActionListener(e -> btnResetAction());

        btnExportExcel = new JButton("Xuất File Excel");
        btnExportExcel.setBackground(new Color(46, 125, 50));
        btnExportExcel.setForeground(Color.WHITE);
        btnExportExcel.setIcon(new ImageIcon(getClass().getResource("/Icons/Microsoft Excel.png")));
        btnExportExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportExcel.addActionListener(e -> btnExportExcelAction());

        GroupLayout filterLayout = new GroupLayout(filterPanel);
        filterPanel.setLayout(filterLayout);
        filterLayout.setHorizontalGroup(
            filterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(filterLayout.createSequentialGroup()
                    .addGap(16)
                    .addGroup(filterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblSearchLabel)
                        .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(filterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblStatusLabel)
                        .addComponent(cboStatus, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, Short.MAX_VALUE)
                    .addComponent(btnAdd)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnDelete)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnUpdate)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnReset)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnExportExcel)
                    .addGap(16))
        );
        filterLayout.setVerticalGroup(
            filterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, filterLayout.createSequentialGroup()
                    .addGap(20)
                    .addGroup(filterLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSearchLabel)
                        .addComponent(lblStatusLabel))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(filterLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboStatus, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExportExcel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
                    .addGap(15))
        );

        add(filterPanel, BorderLayout.PAGE_START);

        // ===== CENTER: Split Content (Left table + Right detail) =====
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridLayout(1, 0));

        JPanel innerPanel = new JPanel();
        innerPanel.setBackground(Color.WHITE);

        // Left - promotion list table
        tblPromotions = new JTable();
        scrollPaneLeft = new JScrollPane(tblPromotions);

        // Right - detail panel
        detailPanel = new JPanel();
        detailPanel.setBackground(Color.WHITE);

        lblPromotionId = new JLabel("Mã CTKM");
        lblCreatedDate = new JLabel("Ngày tạo");
        lblPromotionName = new JLabel("Tên CTKM");
        lblStartDate = new JLabel("Ngày áp dụng");
        lblEndDate = new JLabel("Ngày hết hạn");

        txtPromotionId = new JTextField();
        txtPromotionId.setEditable(false);
        txtCreatedDate = new JTextField();
        txtCreatedDate.setEditable(false);
        txtPromotionName = new JTextField();
        txtPromotionName.setEditable(false);
        txtStartDate = new JTextField();
        txtStartDate.setEditable(false);
        txtEndDate = new JTextField();
        txtEndDate.setEditable(false);

        tblPromotionDetails = new JTable();
        scrollPaneRight = new JScrollPane(tblPromotionDetails);

        GroupLayout detailLayout = new GroupLayout(detailPanel);
        detailPanel.setLayout(detailLayout);
        detailLayout.setHorizontalGroup(
            detailLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(detailLayout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(scrollPaneRight)
                    .addGap(16))
                .addGroup(detailLayout.createSequentialGroup()
                    .addGap(16)
                    .addGroup(detailLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblPromotionId)
                        .addComponent(txtPromotionId, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                        .addComponent(lblPromotionName)
                        .addComponent(txtPromotionName, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                    .addGap(12)
                    .addGroup(detailLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblCreatedDate)
                        .addComponent(txtCreatedDate, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                        .addComponent(lblStartDate)
                        .addComponent(txtStartDate, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                    .addGap(12)
                    .addGroup(detailLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(detailLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(detailLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(lblEndDate)
                        .addComponent(txtEndDate, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                    .addGap(16))
        );
        detailLayout.setVerticalGroup(
            detailLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(detailLayout.createSequentialGroup()
                    .addGap(10)
                    .addGroup(detailLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPromotionId)
                        .addComponent(lblCreatedDate))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(detailLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPromotionId, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCreatedDate, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                    .addGap(8)
                    .addGroup(detailLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPromotionName)
                        .addComponent(lblStartDate)
                        .addComponent(lblEndDate))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(detailLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPromotionName, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtStartDate, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEndDate, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addComponent(scrollPaneRight, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addGap(6))
        );

        GroupLayout innerLayout = new GroupLayout(innerPanel);
        innerPanel.setLayout(innerLayout);
        innerLayout.setHorizontalGroup(
            innerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(innerLayout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(scrollPaneLeft, GroupLayout.PREFERRED_SIZE, 460, GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(detailPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(6))
        );
        innerLayout.setVerticalGroup(
            innerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(innerLayout.createSequentialGroup()
                    .addGap(4)
                    .addGroup(innerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPaneLeft)
                        .addComponent(detailPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(6))
        );

        contentPanel.add(innerPanel);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void setupTables() {
        // ===== Table 1: Danh sách khuyến mãi =====
        DefaultTableModel model1 = new DefaultTableModel(
            new String[]{"MÃ CTKM", "TÊN CTKM", "TRẠNG THÁI"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPromotions.setModel(model1);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblPromotions.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblPromotions.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        tblPromotions.setRowSelectionAllowed(true);
        tblPromotions.setColumnSelectionAllowed(false);
        tblPromotions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPromotions.setSelectionBackground(new Color(0, 120, 215));
        tblPromotions.setSelectionForeground(Color.WHITE);

        // ===== Table 2: Chi tiết khuyến mãi =====
        DefaultTableModel model2 = new DefaultTableModel(
            new String[]{"ID", "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ GỐC", "GIÁ SAU GIẢM", "GIẢM GIÁ"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPromotionDetails.setModel(model2);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        tblPromotionDetails.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblPromotionDetails.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblPromotionDetails.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tblPromotionDetails.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tblPromotionDetails.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        tblPromotionDetails.setRowSelectionAllowed(true);
        tblPromotionDetails.setColumnSelectionAllowed(false);
        tblPromotionDetails.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPromotionDetails.setSelectionBackground(new Color(0, 120, 215));
        tblPromotionDetails.setSelectionForeground(Color.WHITE);
    }

    private void setupTableListener() {
        tblPromotions.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblPromotions.getSelectedRow();
                if (row >= 0) {
                    int promotionId = (int) tblPromotions.getValueAt(row, 0);
                    selectedPromotion = promotionService.getPromotionById(promotionId);
                    if (selectedPromotion != null) {
                        showPromotionDetails(selectedPromotion);
                    }
                }
            }
        });
    }

    private void setCss() {
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm chương trình khuyến mãi...");

        // Clear default text
        txtPromotionId.setText("");
        txtCreatedDate.setText("");
        txtPromotionName.setText("");
        txtStartDate.setText("");
        txtEndDate.setText("");
    }

    private void loadStatusComboBox() {
        cboStatus.removeAllItems();
        cboStatus.addItem("Tất cả");
        cboStatus.addItem("Sắp diễn ra");
        cboStatus.addItem("Đang diễn ra");
        cboStatus.addItem("Kết thúc");
        cboStatus.setSelectedIndex(0);
    }

    private void loadData() {
        List<PromotionDTO> all = promotionService.getAllPromotions();
        LocalDate today = LocalDate.now();

        // Filter by status
        if (selectedStatus != null) {
            all = all.stream().filter(p -> {
                if (selectedStatus == 0) return today.isBefore(p.getStartDate());
                if (selectedStatus == 1) return !today.isBefore(p.getStartDate()) && !today.isAfter(p.getEndDate());
                if (selectedStatus == 2) return today.isAfter(p.getEndDate());
                return true;
            }).collect(java.util.stream.Collectors.toList());
        }

        // Filter by search key
        if (searchKey != null && !searchKey.isEmpty()) {
            String key = searchKey.toLowerCase();
            all = all.stream()
                .filter(p -> p.getName().toLowerCase().contains(key))
                .collect(java.util.stream.Collectors.toList());
        }

        fillTable(all);
    }

    /**
     * Refresh dữ liệu từ bên ngoài (được gọi khi quay về từ Panel 2)
     */
    public void refreshData() {
        loadData();
    }

    private void fillTable(List<PromotionDTO> promotions) {
        DefaultTableModel model = (DefaultTableModel) tblPromotions.getModel();
        model.setRowCount(0);
        LocalDate today = LocalDate.now();

        for (PromotionDTO p : promotions) {
            String status;
            if (today.isBefore(p.getStartDate())) {
                status = "Sắp diễn ra";
            } else if (today.isAfter(p.getEndDate())) {
                status = "Kết thúc";
            } else {
                status = "Đang diễn ra";
            }
            model.addRow(new Object[]{
                p.getId(),
                p.getName(),
                status
            });
        }

        // Clear detail view when data reloads
        selectedPromotion = null;
        clearDetailView();
    }

    private void showPromotionDetails(PromotionDTO promotion) {
        txtPromotionId.setText(String.valueOf(promotion.getId()));
        txtCreatedDate.setText(promotion.getStartDate().format(dateFormatter));
        txtPromotionName.setText(promotion.getName());
        txtStartDate.setText(promotion.getStartDate().format(dateFormatter));
        txtEndDate.setText(promotion.getEndDate().format(dateFormatter));

        // Load promotion details from DB
        List<PromotionDetailDTO> details = promotionService.getPromotionDetails(promotion.getId());

        DefaultTableModel model = (DefaultTableModel) tblPromotionDetails.getModel();
        model.setRowCount(0);

        int index = 1;
        for (PromotionDetailDTO detail : details) {
            String productName = "N/A";
            BigDecimal originalPrice = BigDecimal.ZERO;
            try {
                ProductDTO product = productService.getProductById(detail.getProductId());
                if (product != null) {
                    productName = product.getName();
                    originalPrice = product.getPrice();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            BigDecimal discountPercentage = detail.getDiscountPercentage();
            BigDecimal discountedPrice = originalPrice.multiply(
                BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
            ).setScale(0, RoundingMode.HALF_UP);

            model.addRow(new Object[]{
                index++,
                detail.getProductId(),
                productName,
                FormatterUtil.toVND(originalPrice),
                FormatterUtil.toVND(discountedPrice),
                discountPercentage + "%"
            });
        }
    }

    private void clearDetailView() {
        txtPromotionId.setText("");
        txtCreatedDate.setText("");
        txtPromotionName.setText("");
        txtStartDate.setText("");
        txtEndDate.setText("");

        DefaultTableModel model = (DefaultTableModel) tblPromotionDetails.getModel();
        model.setRowCount(0);
    }

    // ===== Action handlers =====

    private void btnAddAction() {
        MainForm mainForm = (MainForm) SwingUtilities.getWindowAncestor(this);
        if (mainForm != null) {
            mainForm.showPromotionForm(null);
        }
    }

    private void btnUpdateAction() {
        int selectedRow = tblPromotions.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chương trình khuyến mãi!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int promotionId = (int) tblPromotions.getValueAt(selectedRow, 0);
        MainForm mainForm = (MainForm) SwingUtilities.getWindowAncestor(this);
        if (mainForm != null) {
            mainForm.showPromotionForm(promotionId);
        }
    }

    private void btnDeleteAction() {
        int selectedRow = tblPromotions.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chương trình khuyến mãi!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int promotionId = (int) tblPromotions.getValueAt(selectedRow, 0);
        String promotionName = (String) tblPromotions.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa chương trình \"" + promotionName + "\"?",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                promotionService.deletePromotion(promotionId);
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnResetAction() {
        txtSearch.setText("");
        searchKey = null;
        selectedStatus = null;
        cboStatus.setSelectedIndex(0);
        loadData();
    }

    private void btnExportExcelAction() {
        try {
            JTable exportTable = new JTable();
            DefaultTableModel model = new DefaultTableModel();

            String[] columns = {"STT", "MÃ CTKM", "TÊN CHƯƠNG TRÌNH", "TRẠNG THÁI", "NGÀY BẮT ĐẦU", "NGÀY KẾT THÚC"};
            model.setColumnIdentifiers(columns);

            // Lấy tất cả promotion (không phân trang)
            List<PromotionDTO> allPromotions = promotionService.getAllPromotions();
            LocalDate today = LocalDate.now();

            int stt = 1;
            for (PromotionDTO p : allPromotions) {
                String status;
                if (today.isBefore(p.getStartDate())) {
                    status = "Sắp diễn ra";
                } else if (today.isAfter(p.getEndDate())) {
                    status = "Kết thúc";
                } else {
                    status = "Đang diễn ra";
                }

                model.addRow(new Object[]{
                    stt++,
                    p.getId(),
                    p.getName(),
                    status,
                    p.getStartDate().format(dateFormatter),
                    p.getEndDate().format(dateFormatter)
                });
            }

            exportTable.setModel(model);
            ExcelHelper.exportToExcel(exportTable, "Danh_sach_khuyen_mai", "Danh sách khuyến mãi");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
