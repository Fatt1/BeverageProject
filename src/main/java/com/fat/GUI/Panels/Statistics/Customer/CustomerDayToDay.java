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
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CustomerDayToDay extends javax.swing.JPanel {

    private final IStatisticService statisticService;
    private JTable tblCustomer;
    private JTextField txtSearch;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private List<CustomerStatisticDTO> fullList = new java.util.ArrayList<>();

    public CustomerDayToDay() {
        statisticService = StatisticService.getInstance();
        initComponents();
        dateFrom.setDate(new Date());
        dateTo.setDate(new Date());
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // ===== LEFT PANEL (Filter) =====
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setPreferredSize(new Dimension(280, 286));

        JLabel lblSearch = new JLabel("Tìm kiếm khách hàng");
        txtSearch = new JTextField();
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterTable();
            }
        });

        JLabel lblFromDate = new JLabel("Từ ngày");
        dateFrom = new com.toedter.calendar.JDateChooser();

        JLabel lblToDate = new JLabel("Đến ngày");
        dateTo = new com.toedter.calendar.JDateChooser();

        JButton btnExportExcel = new JButton("Xuất File Excel");
        btnExportExcel.setBackground(new Color(46, 125, 50));
        btnExportExcel.setForeground(Color.WHITE);
        btnExportExcel.setIcon(new ImageIcon(getClass().getResource("/Icons/Microsoft Excel.png")));
        btnExportExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportExcel.addActionListener(e -> ExcelHelper.exportToExcel(tblCustomer, "Thong_ke_khach_hang_theo_ngay", "Thong_ke_khach_hang_theo_ngay"));

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
                    .addComponent(lblSearch)
                    .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFromDate)
                    .addComponent(dateFrom, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblToDate)
                    .addComponent(dateTo, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportExcel, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStatistic, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        filterLayout.setVerticalGroup(
            filterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(filterLayout.createSequentialGroup()
                .addGap(14)
                .addComponent(lblSearch)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                .addGap(12)
                .addComponent(lblFromDate)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateFrom, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                .addGap(12)
                .addComponent(lblToDate)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateTo, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
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

        JScrollPane scrollPane = new JScrollPane(tblCustomer);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void btnStatisticAction() {
        if (dateFrom.getDate() == null || dateTo.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        LocalDate startDate = dateFrom.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = dateTo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (startDate.isAfter(endDate)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được sau ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            fullList = statisticService.getCustomerStatistic(startDate, endDate);
            filterTable();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi tải thống kê khách hàng theo ngày: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterTable() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        List<CustomerStatisticDTO> filtered = fullList.stream()
            .filter(dto -> keyword.isEmpty() || dto.getCustomerName().toLowerCase().contains(keyword))
            .collect(java.util.stream.Collectors.toList());
        initTable(filtered);
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
