package com.fat.GUI.Components;

import com.fat.DTO.ProductDTO;
import com.fat.DTO.UnitDTO;
import com.fat.GUI.Panels.CustomTableHeader;
import com.fat.GUI.Panels.Inventories.ProductInventoryListDialog;
import com.fat.GUI.Panels.TableRow;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IngredientTabPanel extends JPanel {
    private ArrayList<ProductDTO> currentIngredients = new ArrayList<>();
    private JFrame parentFrame;
    private JPanel ingredientListPanel;
    private final String INGREDIENT_COL_OPTS = "[100!]10[grow]10[100!]10[100!]10[80!]10[80!]10[80!]";
    public IngredientTabPanel (String unitName, JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout((new MigLayout("fill, insets 3", "[grow]", "[][][grow]")));
        setBackground(Color.decode("#F9F9F9"));
        ingredientListPanel = new JPanel(new MigLayout("fillx, wrap", "[grow]"));

        CustomTableHeader tableHeader = new CustomTableHeader(INGREDIENT_COL_OPTS,
                new String[]{"Mã sản phẩm", "Tên thành phần", "Số lượng", "Đơn vị tính", "Giá vốn", "Thành tiền", "Hành động"});


        JButton btnSearch = new JButton("Tìm kiếm nguyên vật liệu");
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.putClientProperty(FlatClientProperties.STYLE, "" +
                "background: #ef9f62; "  +
                "foreground: #FFFFFF; " +
                "font: bold"
        );

        btnSearch.addActionListener(e -> openSearchDialog());

        add(btnSearch, "wrap, alignx right, aligny top, h 30!");
        add(tableHeader, "growx, wrap");
        add(ingredientListPanel, "growx, gaptop 10, aligny top");
    }
    private void openSearchDialog() {

        ProductInventoryListDialog searchDialog = new ProductInventoryListDialog(parentFrame, "Tìm kiếm nguyên vật liệu", currentIngredients);
        searchDialog.setVisible(true);

        // Xóa nguyên liệu nếu mà người dùng bỏ chọn trong dialog
        for(Component comp : ingredientListPanel.getComponents()) {
            boolean found = false;
            for(var ingredient : currentIngredients) {
                String rowName = "product_row_" + ingredient.getId();
                if (comp.getName() != null && comp.getName().equals(rowName)) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                ingredientListPanel.remove(comp);
            }
        }

        for(var ingredient : currentIngredients) {
            String rowName = "product_row_" + ingredient.getId();
            System.out.println("Đã chọn nguyên liệu: " + ingredient.getName());
            boolean isRendered = false;

            for (Component comp : ingredientListPanel.getComponents()) {
                if (comp.getName() != null && comp.getName().equals(rowName)) {
                    isRendered = true;
                    break;
                }
            }

            if (isRendered) {
                continue; // Bỏ qua nếu đã hiển thị
            }

            addIngredientRow(ingredient, rowName);
        }

        ingredientListPanel.revalidate();
        ingredientListPanel.repaint();
    }

    private void addIngredientRow(ProductDTO ingredient, String rowName) {
        TableRow row = new TableRow(INGREDIENT_COL_OPTS);
        // Tạo các component nhập liệu
        JLabel lblId = new JLabel(String.valueOf(ingredient.getId()));
        JLabel lblName = new JLabel(ingredient.getName());
        JTextField txtQuantity = new JTextField("1"); // Người dùng sẽ nhập số lượng vào đây

        JComboBox<UnitDTO> cboUnits = new JComboBox<>();
        if (ingredient.getProductUnits() != null) {
            for (var unit : ingredient.getProductUnits()) {
                cboUnits.addItem(unit.getUnit()); // Giả sử getUnit trả về String tên đơn vị
            }
        }

        JLabel lblCost = new JLabel(String.valueOf(ingredient.getMovingAvgCost()));
        JLabel lblTotal = new JLabel("..."); // Tính toán sau
        JButton btnDelete = new JButton("Xóa");

        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> {
            ingredientListPanel.remove(row);
            ingredientListPanel.revalidate();
            ingredientListPanel.repaint();
            currentIngredients.removeIf(prod -> prod.getId() == ingredient.getId());
        });
        row.setName(rowName);
        row.add(lblId);
        row.add(lblName);
        row.add(txtQuantity);
        row.add(cboUnits);
        row.add(lblCost);
        row.add(lblTotal);
        row.add(btnDelete);

        ingredientListPanel.add(row, "growx, wrap");
    }
}
