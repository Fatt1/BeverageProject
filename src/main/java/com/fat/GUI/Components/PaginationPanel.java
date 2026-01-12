package com.fat.GUI.Components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class PaginationPanel extends JPanel {
    private int totalPages;
    private int pageIndex;
    private Consumer<Integer> eventPagination;

    // Cấu hình style cho nút
    private final String BUTTON_STYLE = "arc: 10; background: #FFFFFF; foreground: #333333; font: bold; focusWidth: 0; borderWidth: 1; borderColor: #E0E0E0";
    private final String ACTIVE_STYLE = "arc: 10; background: #000000; foreground: #FFFFFF; font: bold; focusWidth: 0; borderWidth: 0";

    public PaginationPanel() {
        setLayout(new MigLayout("insets 0, fillx"));
        setBackground(Color.WHITE);
    }

    public void setPagination(int pageIndex, int totalPages) {
        this.pageIndex = pageIndex;
        this.totalPages = totalPages;
        renderPagination();
    }

    private void renderPagination() {
        this.removeAll(); // xóa hết nút cũ

        if(pageIndex > 1) {
            this.add(createButton("<<", pageIndex - 1, false), "w 50!, h 25!");
        }

        int maxButtonsToShow = 5;
        int startPage = Math.max(1,pageIndex - 2);
        int endPage = Math.min(totalPages, startPage + maxButtonsToShow - 1);

        if (endPage - startPage < maxButtonsToShow - 1) {
            startPage = Math.max(1, endPage - maxButtonsToShow + 1);
        }

        if (startPage > 1) {
            this.add(createButton("1", 1, false));
            if (startPage > 2) this.add(new JLabel("..."));
        }

        // Vòng lặp vẽ các nút số
        for (int i = startPage; i <= endPage; i++) {
            boolean isActive = (i == pageIndex);
            this.add(createButton(String.valueOf(i), i, isActive), "w 40!, h 25!");
        }

        if (endPage < totalPages) {
            if (endPage < totalPages - 1) this.add(new JLabel("..."));
            this.add(createButton(String.valueOf(totalPages), totalPages, false), "w 50!, h 25!");
        }

        // 3. Nút Next (Tiến)
        if (pageIndex < totalPages) {
            this.add(createButton(">>", pageIndex + 1, false),"w 50!, h 25!");
        }

        this.revalidate();
        this.repaint();
    }

    private JButton createButton(String text, int targetPage, boolean isActive){
        JButton button = new JButton(text);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.putClientProperty(FlatClientProperties.STYLE, isActive ? ACTIVE_STYLE : BUTTON_STYLE);

        button.addActionListener(e -> {
            if(eventPagination != null && targetPage != pageIndex) {
                // Chỉ trigger khi click vào trang khác trang hiện tại
                eventPagination.accept(targetPage);
            }
        });
        return button;
    }
}
