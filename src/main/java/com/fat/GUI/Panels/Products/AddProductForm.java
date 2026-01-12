package com.fat.GUI.Panels.Products;

import com.fat.DTO.ProductDTO;
import com.fat.DTO.UnitDTO;
import com.fat.GUI.Components.FormGroup;
import com.fat.GUI.Components.ImagePicker;
import com.fat.GUI.Components.IngredientTabPanel;
import com.fat.GUI.Panels.CustomTableHeader;
import com.fat.GUI.Panels.Inventories.ProductInventoryListDialog;
import com.fat.GUI.Panels.TableRow;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class AddProductForm extends JDialog {
    private JTextField txtName;
    private JComboBox<String> cboCategory;
    private  JTextArea txtDescription;
    private  ImagePicker imagePicker;
    private JCheckBox chkManageInventory;
    private ArrayList<ProductDTO> selectedIngredients;

    private JPanel pnlUnitList;
    private JTabbedPane tabIngredients;
    private final String UNIT_COL_OPTS = "[100!]10[100!]10[grow]10[grow]10[100!]";
    private final String INGREDIENT_COL_OPTS = "[100!]10[grow]10[100!]10[100!]10[80!]10[80!]10[80!]";

    public AddProductForm(JFrame owner, String title) {
        // tham số 'true' ở cuối nghĩa là MODAL (chặn cửa sổ chính)
        super(owner, title, true);
        setSize(1000, 800);
        setLocationRelativeTo(owner); // Hiện giữa màn hình cha
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        init();

    }

    private void init() {
        selectedIngredients = new ArrayList<>();
        JPanel contentPanel = new JPanel(new MigLayout("fillx, insets 10, wrap"));
        contentPanel.add(informationProductPanel(), "growx");
        contentPanel.add(imagePanel(), "growx, gaptop 10");
        contentPanel.add(inventoryPanel(), "growx, gaptop 10");
        contentPanel.add(unitPanel(), "growx, gaptop 10");

        JScrollPane scrollPane = new JScrollPane(contentPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);;
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // Thêm scrollPane vào dialog
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel informationProductPanel() {
        JPanel panel = new JPanel(new MigLayout("fill, wrap"));
        panel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel("Thông tin chung");
        lblTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font: bold 16"
        );
        panel.add(lblTitle);

        JPanel formPanel = new JPanel(new MigLayout("fill, wrap 2", "[]40[]"));
        formPanel.setBackground(Color.white);
        txtName = new JTextField();
        formPanel.add(new FormGroup("Tên sản phẩm", txtName), "growx");

        cboCategory = new JComboBox<>(new String[]{"Nước", "Nước giải khát"});

        formPanel.add(new FormGroup("Danh mục", cboCategory), "growx");

        txtDescription = new JTextArea(100, 40);
        formPanel.add(new FormGroup("Mô tả", new JScrollPane(txtDescription)), "growx, span");

        panel.add(formPanel, "growx");
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "border: 1,1,1,1, #000000, 1");

        return panel;
    }

    private JPanel imagePanel() {
        JPanel panel = new JPanel(new MigLayout("fillx", "[][]"));
        JPanel leftPanel = new JPanel(new MigLayout("fill, wrap"));
        JLabel lblTitle = new JLabel("Ảnh sản phẩm");
        leftPanel.add(lblTitle);
        JLabel lblWarning = new JLabel("Dung lượng ảnh tối đa 5MB. Định dạng: .Svg");

        lblWarning.putClientProperty(FlatClientProperties.STYLE, "" +
            "foreground: #FF0000;" +
                "font: italic -2"
        );
        leftPanel.add(lblWarning);

        imagePicker = new ImagePicker();



        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "border: 1,1,1,1, #000000, 1");

        panel.add(leftPanel);
        panel.add(imagePicker);
        return panel;
    }

    private JPanel inventoryPanel() {
        JPanel panel = new JPanel(new MigLayout("fill, wrap"));
        panel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel("Tồn kho");
        lblTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font: bold 16"
        );
        chkManageInventory = new JCheckBox("Theo dõi tồn kho");
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "border: 1,1,1,1, #000000, 1");
        panel.add(lblTitle);
        panel.add(chkManageInventory);

        return panel;
    }

    private JPanel unitPanel() {
        tabIngredients = new JTabbedPane();
        JPanel panel = new JPanel(new MigLayout("fillx, wrap"));
        panel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel("Đon vị tính");
        lblTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font: bold 16"
        );
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "border: 1,1,1,1, #000000, 1");
        panel.add(lblTitle);


        CustomTableHeader tableHeader = new CustomTableHeader(UNIT_COL_OPTS,
                new String[]{"Đơn vị tính", "Giá trị quy đổi", "Giá bán", "Giá vốn", "Hành động"});


        pnlUnitList = new JPanel(new MigLayout("fillx, wrap, insets 0", "[grow, fill]"));
        panel.add(tableHeader, "growx"); // span 5 để chiếm 5 cột
        addUnitRow("Khong xac dinh", false); // Dòng mặc định, không cho xóa



        JButton btnAddUnit = new JButton("Thêm đơn vị tính");
        btnAddUnit.addActionListener(e -> {
            addUnitRow("Khong xac dinh", true);
        });


        panel.add(pnlUnitList, "growx");
        panel.add(btnAddUnit, "gaptop 10");


        // 5. Phần TabbedPane để cấu hình nguyên liệu cho từng đơn vị
        panel.add(new JSeparator(), "growx, gaptop 10");
        JLabel lblIngredientTitle = new JLabel("Định mức nguyên liệu (Theo đơn vị)");
        lblIngredientTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold 14");
        panel.add(lblIngredientTitle, "gaptop 10");


        tabIngredients.putClientProperty(FlatClientProperties.STYLE, "tabAreaInsets: 5,0,0,0"); // FlatLaf styling
        panel.add(tabIngredients, "growx, h 300!"); // Set chiều cao cố định hoặc để grow
        return panel;
    }

    private void addUnitRow(String defaultName, boolean allowDelete) {
        // Tao Content cho Tab
        JPanel tabContent = new IngredientTabPanel(defaultName, (JFrame) SwingUtilities.getWindowAncestor(this));

        // THÊM TAB VÀO tabIngredients
        tabIngredients.addTab(defaultName, tabContent);

        // --- B. Tạo Dòng trên bảng Đơn vị ---
        TableRow rowPanel = new TableRow(UNIT_COL_OPTS);

        JComboBox<String> cboUnit = new JComboBox<>(new String[]{"Chai", "Lon", "Thùng", "Hộp", "Lốc"});
        cboUnit.setSelectedItem(defaultName); // Set giá trị mặc định

        cboUnit.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                String newName = (String) e.getItem();
                int index = tabIngredients.indexOfComponent(tabContent);
                if (index != -1) {
                    tabIngredients.setTitleAt(index, newName);
                }
            }
        });

        JTextField txtConvert = new JTextField("1");
        JTextField txtPrice = new JTextField("0");
        JTextField txtCost = new JTextField("0");

        if(!allowDelete) {
            txtConvert.setEnabled(false);
        }
        JButton btnDelete = new JButton("Xóa");

        if(!allowDelete) {
            btnDelete.setEnabled(false);
        }

        btnDelete.addActionListener(e -> {
            pnlUnitList.remove(rowPanel);
            tabIngredients.remove(tabContent);
            pnlUnitList.revalidate();
            pnlUnitList.repaint();
        });

        // Add vào Row
        rowPanel.add(cboUnit, "growx");
        rowPanel.add(txtConvert, "growx");
        rowPanel.add(txtPrice, "growx");
        rowPanel.add(txtCost, "growx");
        rowPanel.add(btnDelete);

        // Add Row vào Panel danh sách
        pnlUnitList.add(rowPanel, "growx, wrap");
        pnlUnitList.revalidate();
        pnlUnitList.repaint();
    }

}
