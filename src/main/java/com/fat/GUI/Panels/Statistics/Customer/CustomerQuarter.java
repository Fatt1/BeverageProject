package com.fat.GUI.Panels.Statistics.Customer;

import com.fat.BUS.Abstractions.Services.IStatisticService;
import com.fat.BUS.Services.StatisticService;
import com.fat.BUS.Utils.ExcelHelper;
import com.fat.DTO.Statistics.CustomerQuarterStatisticDTO;
import com.fat.GUI.Utils.FormatterUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerQuarter extends javax.swing.JPanel {

    private final IStatisticService statisticService;
    private JTable tblCustomer;
    private com.toedter.calendar.JYearChooser jYear;

    public CustomerQuarter() {
        statisticService = StatisticService.getInstance();
        initComponents();
        tblCustomer.getColumnModel().getColumn(0).setMaxWidth(70);
        tblCustomer.getColumnModel().getColumn(1).setMaxWidth(70);
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // ===== LEFT PANEL (Filter) =====
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setPreferredSize(new Dimension(280, 286));

        JLabel lblYear = new JLabel("Năm");
        jYear = new com.toedter.calendar.JYearChooser();

        JButton btnExportExcel = new JButton("Xuất File Excel");
        btnExportExcel.setBackground(new Color(46, 125, 50));
        btnExportExcel.setForeground(Color.WHITE);
        btnExportExcel.setIcon(new ImageIcon(getClass().getResource("/Icons/Microsoft Excel.png")));
        btnExportExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportExcel.addActionListener(e -> ExcelHelper.exportToExcel(tblCustomer, "Thong_ke_khach_hang_theo_quy", "Thong_ke_khach_hang_theo_quy"));

        JButton btnStatistic = new JButton("Thống kê");
        btnStatistic.setBackground(new Color(51, 51, 51));
        btnStatistic.setForeground(Color.WHITE);
        btnStatistic.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnStatistic.addActionListener(e -> btnStatisticAction());

        GroupLayout filterLayout = new GroupLayout(filterPanel);
        filterPanel.setLayout(filterLayout);
        filterLayout.setHorizontalGroup(
            filterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(filterLayout.createSequentialGroup()
                .addGap(12)
                .addGroup(filterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblYear)
                    .addComponent(jYear, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportExcel, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStatistic, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        filterLayout.setVerticalGroup(
            filterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(filterLayout.createSequentialGroup()
                .addGap(14)
                .addComponent(lblYear)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jYear, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addComponent(btnExportExcel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                .addGap(8)
                .addComponent(btnStatistic, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(filterPanel, BorderLayout.WEST);

        // ===== RIGHT PANEL (Table) =====
        tblCustomer = new JTable();
        tblCustomer.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"STT", "Mã KH", "Tên khách hàng", "QUÝ 1 (Q1)", "QUÝ 2 (Q2)", "QUÝ 3 (Q3)", "QUÝ 4 (Q4)", "TỔNG CỘNG"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblCustomer.getTableHeader().setReorderingAllowed(false);
        tblCustomer.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(tblCustomer);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void btnStatisticAction() {
        int year = jYear.getYear();
        try {
            List<CustomerQuarterStatisticDTO> list = statisticService.getCustomerQuarterStatistic(year);
            initTable(list);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi tải thống kê khách hàng theo quý: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initTable(List<CustomerQuarterStatisticDTO> list) {
        DefaultTableModel model = (DefaultTableModel) tblCustomer.getModel();
        model.setRowCount(0);
        int stt = 1;
        for (CustomerQuarterStatisticDTO dto : list) {
            Object[] row = new Object[]{
                    stt++,
                    dto.getCustomerId(),
                    dto.getCustomerName(),
                    FormatterUtil.toVND(dto.getQ1Amount()),
                    FormatterUtil.toVND(dto.getQ2Amount()),
                    FormatterUtil.toVND(dto.getQ3Amount()),
                    FormatterUtil.toVND(dto.getQ4Amount()),
                    FormatterUtil.toVND(dto.getTotalAmount())
            };
            model.addRow(row);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblCustomer.getColumnCount(); i++) {
            tblCustomer.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}
