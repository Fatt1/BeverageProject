package com.fat.GUI.Panels;

import com.fat.Contract.Constants.Menu;
import com.fat.GUI.Panels.Products.ViewProductsPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    private MainContentPanel mainContainer;
    public AdminPanel() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 0", "[180!]10[fill]10"));

        mainContainer = new MainContentPanel();
        mainContainer.add(new TestPanel(), "grow, push");


        // 1. Setup Sidebar
        SidebarPanel sidebarPanel = new SidebarPanel(key -> {
            // Khi Sidebar bấm nút, nó bắn cái key ra đây
            switchPage(key);
        });

        // Setup JScrollPane cho sidebar
        JScrollPane scrollPanel = new JScrollPane(sidebarPanel);
        scrollPanel.setBorder(null);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16); // Tăng tốc độ cuộn


        add(scrollPanel, "growy");
        add(mainContainer, "grow, gaptop 20, gapbottom 20");

    }
    private void switchPage(String key) {
        mainContainer.removeAll();

        JPanel newPage = null;
        switch (key) {
            case Menu.DASHBOARD:
                newPage = new TestPanel();
                break;
            case Menu.PRODUCT:
                newPage = new ViewProductsPanel();
                break;
            // Thêm các case khác cho các trang khác
        }
        if(newPage != null) {
            mainContainer.add(newPage, "grow, push");
            mainContainer.revalidate();
            mainContainer.repaint();
        }
    }


}
