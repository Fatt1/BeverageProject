package com.fat.GUI.Panels;

import com.fat.Contract.Constants.Menu;
import com.fat.GUI.Components.MenuItem;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.function.Consumer;

public class SidebarPanel extends JPanel {
    // Tao 1 bien de chua cac hanh dong khi chon menu
    private Consumer<String> onMenuSelect;

    public SidebarPanel(Consumer<String> onMenuSelect) {
        this.onMenuSelect = onMenuSelect;
        setLayout(new MigLayout("wrap, fillx, insets 0", "fill"));
        this.setBackground(new Color(217, 217, 217));
        init();

    }


    private void init() {
        // title label
        JLabel titleLabel = new JLabel("Appolo");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "" +
            "font: bold +10"
        );
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);


        // username label
        JLabel userNameLabel = new JLabel("Fat");
        userNameLabel.putClientProperty(FlatClientProperties.STYLE, "" +
            "font: bold +6;"
        );
        userNameLabel.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Color.WHITE));
        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);



        add(titleLabel, "gaptop 10");
        add(userNameLabel, "gaptop 10, gapbottom 10, h 40!");

        // Menu buttons
        String[][] menuItems = {
                {"Order",Menu.DASHBOARD},
                {"Category",Menu.CATEGORY},
                {"Product",Menu.PRODUCT},
                {"Option",Menu.OPTION},
                {"Warehouse",Menu.INVENTORY},
                {"Order",Menu.ORDER},
                {"Import",Menu.IMPORT},
                {"StockTake",Menu.STOCKTAKE},
                {"Supplier",Menu.SUPPLIER},
                {"Discount",Menu.DISCOUNT},
                {"User",Menu.USER},
                {"Role",Menu.ROLE}
        };
        ButtonGroup menuGroup = new ButtonGroup();
        boolean first = true;
        for(int i = 0; i < menuItems.length; i++) {
            String menuKey = menuItems[i][1]; // Đây là cái KEY quan trọng để chuyển trang
            URL path = getClass().getResource("/Icons/" + menuItems[i][0] + ".png");
            var mi = new MenuItem(menuKey, new ImageIcon(path));
            add(mi, "h 40!");
            menuGroup.add(mi);
            if(first) {
                mi.setSelected(true);
                first = false;
            }
            mi.addActionListener(e -> {
                if(onMenuSelect != null){
                    onMenuSelect.accept(menuKey);
                }
            });
        }


    }
}
