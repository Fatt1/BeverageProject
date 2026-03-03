package com.fat.GUI.Panels.Statistics.Customer;

import com.fat.BUS.Abstractions.Services.IStatisticService;
import com.fat.BUS.Services.StatisticService;
import com.fat.BUS.Utils.ExcelHelper;
import com.fat.DTO.Statistics.CustomerStatisticDTO;
import com.fat.GUI.Utils.FormatterUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class CustomerMonthOfYear extends javax.swing.JPanel {

    private final IStatisticService statisticService;
    private JTable tblCustomer;
    private com.toedter.calendar.JYearChooser jYear;
    private com.toedter.calendar.JMonthChooser monthFrom;
    private com.toedter.calendar.JMonthChooser monthTo;

    public CustomerMonthOfYear() {
        statisticService = StatisticService.getInstance();
        initComponents();
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

        JLabel lblFromMonth = new JLabel("Từ tháng");
        monthFrom = new com.toedter.calendar.JMonthChooser();
        monthFrom.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel lblToMonth = new JLabel("Đến tháng");
        monthTo = new com.toedter.calendar.JMonthChooser();
        monthTo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton btnExportExcel = new JButton("Xuất File Excel");
        btnExportExcel.setBackground(new Color(46, 125, 50));
        btnExportExcel.setForeground(Color.WHITE);
        btnExportExcel.setIcon(new ImageIcon(getClass().getResource("/Icons/Microsoft Excel.png")));
        btnExportExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportExcel.addActionListener(e -> ExcelHelper.exportToExcel(tblCustomer, "Thong_ke_khach_hang_theo_thang", "Thong_ke_khach_hang_theo_thang"));

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
                    .addComponent(lblFromMonth)
                    .addComponent(monthFrom, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblToMonth)
                    .addComponent(monthTo, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
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
                .addGap(12)
                .addComponent(lblFromMonth)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthFrom, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                .addGap(12)
                .addComponent(lblToMonth)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthTo, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                .addGap(12)
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
            new String[]{"STT", "Mã khách hàng", "Tên khách hàng", "Số lượng phiếu", "Tổng số tiền"}
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
        int monthStart = monthFrom.getMonth() + 1;
        int monthEnd = monthTo.getMonth() + 1;
        int year = jYear.getYear();
        LocalDate fromDate = LocalDate.of(year, monthStart, 1);
        LocalDate toDate = LocalDate.of(year, monthEnd, 1).with(TemporalAdjusters.lastDayOfMonth());

        if (fromDate.isAfter(toDate)) {
            JOptionPane.showMessageDialog(this, "Tháng bắt đầu không được sau tháng kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            List<CustomerStatisticDTO> list = statisticService.getCustomerStatistic(fromDate, toDate);
            initTable(list);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi tải thống kê khách hàng theo tháng: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initTable(List<CustomerStatisticDTO> list) {
        DefaultTableModel model = (DefaultTableModel) tblCustomer.getModel();
        model.setRowCount(0);
        int stt = 1;
        for (CustomerStatisticDTO dto : list) {
            Object[] row = new Object[]{
                    stt++,
                    dto.getCustomerId(),
                    dto.getCustomerName(),
                    dto.getTotalReceipts(),
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
