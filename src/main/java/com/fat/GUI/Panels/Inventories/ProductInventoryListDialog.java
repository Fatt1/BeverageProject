package com.fat.GUI.Panels.Inventories;

import com.fat.DTO.CategoryDTO;
import com.fat.DTO.ProductDTO;
import com.fat.DTO.ProductUnitDTO;
import com.fat.DTO.UnitDTO;
import com.fat.GUI.Components.FormGroup;
import com.fat.GUI.Panels.CustomTableHeader;
import com.fat.GUI.Panels.TableRow;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductInventoryListDialog extends JDialog {
    private final String COLUMN_OPTS = "[30!]10[grow]10[grow]";
    private ArrayList<ProductDTO> selectedProducts;
    public ProductInventoryListDialog(JFrame parent, String title, ArrayList<ProductDTO> preSelectedProducts) {
        super(parent, title, true);

        selectedProducts = preSelectedProducts;
        setSize(800, 700);
        setLayout(new MigLayout("fillx, wrap 1", "[grow]", "[][][][][]"));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        init();
    }

    public void init() {
        JLabel titleLabel = new JLabel("Tìm kếm từ kho hàng hóa");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font: bold 16"
        );
        add(titleLabel, "gaptop 10, gapbottom 10, align left");
        add(new JSeparator(), "growx");

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm tên sản phẩm...");
        JPanel searchPanel = new JPanel(new MigLayout("insets 5, fillx", "[grow][]"));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new FormGroup("Tìm kiếm",txtSearch, true), "growx");

        JButton btnSearch = new JButton("Hiển thị sản phẩm");
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.putClientProperty(FlatClientProperties.STYLE, "" +
                "background: #ef9f62; "  +
                "foreground: #FFFFFF; " +
                "font: bold"
        );
        searchPanel.add(btnSearch, "h 30!, gaptop 12");

        add(searchPanel, "growx, aligny top");


        JPanel productListPanel = new JPanel(new MigLayout("fillx, insets 0, wrap", "[grow]"));
        productListPanel.setBackground(Color.WHITE);

        CustomTableHeader tableHeader = new CustomTableHeader(COLUMN_OPTS, new String[]{"#", "TÊN SẢN PHẨM", "DANH MỤC"});
        productListPanel.add(tableHeader, "growx");

        for (int i = 1; i <= 10; i++) {
            int temp = i;
            TableRow row = new TableRow(COLUMN_OPTS);
            JCheckBox chk = new JCheckBox();
            chk.addActionListener(e -> {
                if(chk.isSelected()) {
                    // Thêm sản phẩm vào danh sách đã chọn
                    ProductDTO prod = new ProductDTO();
                    prod.setId(temp);
                    prod.setName("Sản phẩm mẫu " + temp);
                    prod.setMovingAvgCost(BigDecimal.valueOf(100));
                    prod.setBaseUnitId(1);
                    ArrayList<ProductUnitDTO> units = new ArrayList<>();
                    ProductUnitDTO unit = new ProductUnitDTO();
                   unit.setId(1);
                   unit.setUnit(new UnitDTO(1, "Cái"));
                   unit.setProductId(1);
                   units.add(unit);
                   prod.setProductUnits(units);

                    selectedProducts.add(prod);
                } else {
                    // Bỏ chọn sản phẩm khỏi danh sách
                    selectedProducts.removeIf(p -> p.getId() == temp);
                }
            });

            // --- ĐOẠN CODE MỚI: Kiểm tra xem đã được chọn chưa ---
            boolean isExisted = selectedProducts.stream()
                    .anyMatch(p -> p.getId() == temp);
            chk.setSelected(isExisted);
            // Logic của bạn
            row.add(chk);
            row.addCheckbox(chk);

            // Tạo dữ liệu giả khác nhau chút xíu để dễ nhìn
            row.add(new JLabel("Sản phẩm mẫu " + i));
            row.add(new JLabel("Danh mục chung"));

            // QUAN TRỌNG: Phải add row vào panel chứa danh sách (ví dụ: productListPanel)
            productListPanel.add(row, "growx, wrap");
        }


        JScrollPane scrollPane = new JScrollPane(productListPanel);

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, "grow, aligny top, gaptop 5");
        JButton btnSave = new JButton("Xác nhận chọn sản phẩm");
        btnSave.setFocusPainted(false);

        btnSave.putClientProperty(FlatClientProperties.STYLE, "" +
                "background: #ef9f62; "  +
                "foreground: #FFFFFF; " +
                "font: bold"
        );
        btnSave.setFocusPainted(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> {
            // Đóng dialog khi nhấn nút Lưu
            this.dispose();
        });
        add(btnSave, "gaptop 10, alignx right, h 30!");



    }
    public ArrayList<ProductDTO> getSelectedProducts() {
        return selectedProducts;
    }
}
