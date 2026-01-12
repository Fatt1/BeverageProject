package com.fat.GUI.Panels.Products;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.ProductDTO;
import com.fat.GUI.Components.FormGroup;
import com.fat.GUI.Components.PaginationPanel;
import com.fat.GUI.Panels.CustomTableHeader;
import com.fat.GUI.Panels.MainContentPanel;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ViewProductsPanel extends JPanel {
    private PagedResult<ProductDTO> pagedResult;
    private PaginationPanel paginationPanel;
    private final String COLUMN_OPTS = "[15!]10[50!]30[grow]10[150!]10[100!]10[100!]10[80!][90!]";
    private final String[] HEADERS = {"#","ẢNH", "TÊN SẢN PHẨM", "NHÓM SẢN PHẨM" ,"GIÁ BÁN", "GIÁ VỐN" ,"TRẠNG THÁI", "ACTION"};
    public ViewProductsPanel() {
        init();
    }

    public void init(){
        setLayout(new MigLayout("fill, insets 10, wrap", "[fill]", "[][grow]"));
        JScrollPane scrollPane = new JScrollPane(this,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Gia lap lay dc du lieu
        paginationPanel = new PaginationPanel();
        paginationPanel.setPagination(1,10);



        setBackground(Color.WHITE);
        add(topLayoutPanel());
        add(bottomLayoutPanel(), "gaptop 10, grow");
    }

    private JPanel topLayoutPanel(){
        JPanel panel = new JPanel(new MigLayout("fillx, insets 0", "[grow][]"));
        panel.setBackground(Color.WHITE);
        JPanel filterPanel = new JPanel(new MigLayout("insets 0, wrap 2","[]10[]"));

        JTextField txtSearchName = new JTextField(18);


        JTextField txtSearchId = new JTextField(18);


        filterPanel.add(new FormGroup("Tên sản phẩm", txtSearchName));
        filterPanel.add(new FormGroup("Mã sản phẩm", txtSearchId));


        JComboBox<String> cboStatus = new JComboBox<>(new String[]{"Tất cả", "Còn hàng", "Hết hàng"});

        filterPanel.add(new FormGroup("Trạng Thái", cboStatus));
        filterPanel.setBackground(Color.WHITE);

        JPanel actionPanel = new JPanel(new MigLayout("insets 0", "[]10[]10[]"));
        actionPanel.setBackground(Color.WHITE);
        JButton btnAdd = new JButton("Thêm Mới");
        JButton btnReset = new JButton("Hoàn tác lọc");
        JButton btnExcel = new JButton("Nhập/Xuất Excel");

        btnAdd.putClientProperty(FlatClientProperties.STYLE, "" +
                "background: #000000; "  +
                "foreground: #FFFFFF; " +
                "font: bold"
                );
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAdd.addActionListener(e -> {
            JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

            AddProductForm addProductForm = new AddProductForm(owner, "Thêm Sản Phẩm Mới");
            addProductForm.setVisible(true);

            System.out.println("Đã đóng form, giờ load lại dữ liệu...");
        });

        btnReset.putClientProperty(FlatClientProperties.STYLE, "" +
                "background: #8D8D8D; "  +
                "foreground: #FFFFFF; " +
                "font: bold"
        );

        btnReset.setFocusPainted(false);
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnExcel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background: #2E7D32; "  +
                "foreground: #FFFFFF; " +
                "font: bold"
        );

        btnExcel.setFocusPainted(false);
        btnExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        actionPanel.add(btnAdd, "h 30!");
        actionPanel.add(btnReset, "h 30!");
        actionPanel.add(btnExcel, "h 30!");

        panel.add(filterPanel);
        panel.add(actionPanel, "top");
        return panel;

    }

    private JPanel bottomLayoutPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("insets 0, fill,wrap", "[]", "[][grow]"));

        JPanel tableContainer = new JPanel(new MigLayout("fill, insets 0, wrap", "[grow]", "[][grow]"));
        tableContainer.setBackground(Color.white);


        JPanel tableBody = new JPanel(new MigLayout("wrap 1, insets 0", "[grow]"));
        tableBody.add(new ProductRow(COLUMN_OPTS), "grow");
        tableBody.add(new ProductRow(COLUMN_OPTS), "grow");
        tableBody.add(new ProductRow(COLUMN_OPTS), "grow");
        tableBody.add(new ProductRow(COLUMN_OPTS), "grow");
        tableBody.add(new ProductRow(COLUMN_OPTS), "grow");
        tableBody.add(new ProductRow(COLUMN_OPTS), "grow");
        tableBody.add(new ProductRow(COLUMN_OPTS), "grow");
        tableBody.add(new ProductRow(COLUMN_OPTS), "grow");
        tableBody.setBackground(Color.WHITE);

        tableContainer.add(new CustomTableHeader(COLUMN_OPTS, HEADERS), "wrap, growx");
        tableContainer.add(tableBody, "grow");


        JScrollPane tableScrollPanel = new JScrollPane(tableContainer);
        tableScrollPanel.setBorder(null);
        tableScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        tableScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        tableScrollPanel.getVerticalScrollBar().setUnitIncrement(16);


        panel.add(tableScrollPanel, "grow");
        panel.add(paginationPanel, "gaptop 10, align center");

        return panel;
    }
}
