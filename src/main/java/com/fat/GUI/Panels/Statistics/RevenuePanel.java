package com.fat.GUI.Panels.Statistics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class RevenuePanel extends javax.swing.JPanel {
    
    public RevenuePanel() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 245, 245));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Filter Panel
        JPanel filterPanel = createIndependentFilterPanel();
        this.add(filterPanel, BorderLayout.NORTH);
        
        // Chart and Table Panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.6);
        
        // Chart
        splitPane.setTopComponent(createActualChartPanel());
        
        // Table
        splitPane.setBottomComponent(createTablePanel());
        
        this.add(splitPane, BorderLayout.CENTER);
    }
    
    /**
     * Tạo filter panel độc lập (với ComboBox riêng cho mỗi subpanel)
     */
    private JPanel createIndependentFilterPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        
        // ===== FILTER TYPE SELECTION PANEL =====
        JPanel filterTypePanel = new JPanel();
        filterTypePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterTypePanel.setBackground(Color.WHITE);
        filterTypePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        
        filterTypePanel.add(new JLabel("Chọn loại thống kê:"));
        
        JComboBox<String> localCbFilterType = new JComboBox<>(new String[]{"Năm", "Tháng trong năm", "Ngày tới ngày", "Quý"});
        localCbFilterType.setPreferredSize(new Dimension(180, 30));
        filterTypePanel.add(localCbFilterType);
        
        panel.add(filterTypePanel, BorderLayout.NORTH);
        
        // ===== FILTER OPTIONS PANEL =====
        JPanel localFilterOptionsPanel = new JPanel();
        localFilterOptionsPanel.setLayout(new CardLayout());
        localFilterOptionsPanel.setBackground(Color.WHITE);
        localFilterOptionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add filter options cho mỗi loại
        localFilterOptionsPanel.add(createIndependentYearFilterOptions(), "year");
        localFilterOptionsPanel.add(createIndependentMonthFilterOptions(), "month");
        localFilterOptionsPanel.add(createIndependentDayFilterOptions(), "day");
        localFilterOptionsPanel.add(createIndependentQuarterFilterOptions(), "quarter");
        
        panel.add(localFilterOptionsPanel, BorderLayout.CENTER);
        
        // ===== THÊM ACTION LISTENER ĐỦ ĐẦY ĐỦ =====
        localCbFilterType.addActionListener(e -> {
            CardLayout layout = (CardLayout) localFilterOptionsPanel.getLayout();
            String selected = (String) localCbFilterType.getSelectedItem();
            
            switch(selected){
                case "Năm":
                    layout.show(localFilterOptionsPanel, "year");
                    break;
                case "Tháng trong năm":
                    layout.show(localFilterOptionsPanel, "month");
                    break;
                case "Ngày tới ngày":
                    layout.show(localFilterOptionsPanel, "day");
                    break;
                case "Quý":
                    layout.show(localFilterOptionsPanel, "quarter");
                    break;
            }
        });
        
        // Set default
        localCbFilterType.setSelectedIndex(0);
        
        return panel;
    }

    // ===== HELPER METHODS =====
    private void initYearComboBox(JComboBox<Integer> comboBox, int fromYear, int toYear){
        for(int i = fromYear; i <= toYear; i++){
            comboBox.addItem(i);
        }
    }
    
    // ===== FILTER OPTIONS ĐỘC LẬP (cho mỗi subpanel) =====
    
    private JPanel createIndependentYearFilterOptions(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);
        
        panel.add(new JLabel("Từ năm:"));
        JComboBox<Integer> cbFrom = new JComboBox<>();
        initYearComboBox(cbFrom, 2018, 2025);
        cbFrom.setSelectedItem(2018);
        cbFrom.setPreferredSize(new Dimension(100, 30));
        panel.add(cbFrom);
        
        panel.add(new JLabel("Đến năm:"));
        JComboBox<Integer> cbTo = new JComboBox<>();
        initYearComboBox(cbTo, 2018, 2025);
        cbTo.setSelectedItem(2025);
        cbTo.setPreferredSize(new Dimension(100, 30));
        panel.add(cbTo);
        
        panel.add(Box.createHorizontalGlue());
        
        JButton btnStat = new JButton("Thống kê");
        btnStat.setPreferredSize(new Dimension(100, 30));
        btnStat.setBackground(new Color(76, 175, 80));
        btnStat.setForeground(Color.WHITE);
        btnStat.setBorderPainted(false);
        panel.add(btnStat);
        
        JButton btnExcel = new JButton("Xuất Excel");
        btnExcel.setPreferredSize(new Dimension(110, 30));
        btnExcel.setBackground(new Color(76, 175, 80));
        btnExcel.setForeground(Color.WHITE);
        btnExcel.setBorderPainted(false);
        panel.add(btnExcel);
        
        return panel;
    }
    
    private JPanel createIndependentMonthFilterOptions(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);
        
        panel.add(new JLabel("Chọn năm:"));
        JComboBox<Integer> cbYear = new JComboBox<>();
        initYearComboBox(cbYear, 2018, 2025);
        cbYear.setSelectedItem(2025);
        cbYear.setPreferredSize(new Dimension(100, 30));
        panel.add(cbYear);
        
        panel.add(Box.createHorizontalGlue());
        
        JButton btnExcel = new JButton("Xuất Excel");
        btnExcel.setPreferredSize(new Dimension(110, 30));
        btnExcel.setBackground(new Color(76, 175, 80));
        btnExcel.setForeground(Color.WHITE);
        btnExcel.setBorderPainted(false);
        panel.add(btnExcel);
        
        return panel;
    }
    
    private JPanel createIndependentDayFilterOptions(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);
        
        panel.add(new JLabel("Chọn năm:"));
        JComboBox<Integer> cbYear = new JComboBox<>();
        initYearComboBox(cbYear, 2018, 2025);
        cbYear.setSelectedItem(2025);
        cbYear.setPreferredSize(new Dimension(100, 30));
        panel.add(cbYear);
        
        panel.add(new JLabel("Chọn tháng:"));
        JComboBox<Integer> cbMonth = new JComboBox<>();
        for(int i = 1; i <= 12; i++){
            cbMonth.addItem(i);
        }
        cbMonth.setSelectedItem(1);
        cbMonth.setPreferredSize(new Dimension(100, 30));
        panel.add(cbMonth);
        
        panel.add(Box.createHorizontalGlue());
        
        JButton btnStat = new JButton("Thống kê");
        btnStat.setPreferredSize(new Dimension(100, 30));
        btnStat.setBackground(new Color(76, 175, 80));
        btnStat.setForeground(Color.WHITE);
        btnStat.setBorderPainted(false);
        panel.add(btnStat);
        
        JButton btnExcel = new JButton("Xuất Excel");
        btnExcel.setPreferredSize(new Dimension(110, 30));
        btnExcel.setBackground(new Color(76, 175, 80));
        btnExcel.setForeground(Color.WHITE);
        btnExcel.setBorderPainted(false);
        panel.add(btnExcel);
        
        return panel;
    }
    
    private JPanel createIndependentQuarterFilterOptions(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);
        
        panel.add(new JLabel("Chọn năm:"));
        JComboBox<Integer> cbYear = new JComboBox<>();
        initYearComboBox(cbYear, 2018, 2025);
        cbYear.setSelectedItem(2025);
        cbYear.setPreferredSize(new Dimension(100, 30));
        panel.add(cbYear);
        
        panel.add(new JLabel("Chọn quý:"));
        JComboBox<String> cbQuarterLocal = new JComboBox<>(new String[]{"Quý 1", "Quý 2", "Quý 3", "Quý 4"});
        cbQuarterLocal.setPreferredSize(new Dimension(100, 30));
        panel.add(cbQuarterLocal);
        
        panel.add(Box.createHorizontalGlue());
        
        JButton btnStat = new JButton("Thống kê");
        btnStat.setPreferredSize(new Dimension(100, 30));
        btnStat.setBackground(new Color(76, 175, 80));
        btnStat.setForeground(Color.WHITE);
        btnStat.setBorderPainted(false);
        panel.add(btnStat);
        
        JButton btnExcel = new JButton("Xuất Excel");
        btnExcel.setPreferredSize(new Dimension(110, 30));
        btnExcel.setBackground(new Color(76, 175, 80));
        btnExcel.setForeground(Color.WHITE);
        btnExcel.setBorderPainted(false);
        panel.add(btnExcel);
        
        return panel;
    }
    
    private JPanel createActualChartPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        // Create sample chart data
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Sample data
        dataset.addValue(0, "Vốn", "Năm 2018");
        dataset.addValue(0, "Doanh thu", "Năm 2018");
        dataset.addValue(0, "Lợi nhuận", "Năm 2018");
        
        dataset.addValue(0, "Vốn", "Năm 2019");
        dataset.addValue(0, "Doanh thu", "Năm 2019");
        dataset.addValue(0, "Lợi nhuận", "Năm 2019");
        
        dataset.addValue(0, "Vốn", "Năm 2020");
        dataset.addValue(0, "Doanh thu", "Năm 2020");
        dataset.addValue(0, "Lợi nhuận", "Năm 2020");
        
        dataset.addValue(0, "Vốn", "Năm 2021");
        dataset.addValue(0, "Doanh thu", "Năm 2021");
        dataset.addValue(0, "Lợi nhuận", "Năm 2021");
        
        dataset.addValue(0, "Vốn", "Năm 2022");
        dataset.addValue(0, "Doanh thu", "Năm 2022");
        dataset.addValue(0, "Lợi nhuận", "Năm 2022");
        
        dataset.addValue(604200000, "Vốn", "Năm 2023");
        dataset.addValue(683560000, "Doanh thu", "Năm 2023");
        dataset.addValue(79360000, "Lợi nhuận", "Năm 2023");
        
        // Create chart
        JFreeChart chart = ChartFactory.createBarChart(
            "",
            "Năm",
            "Giá trị",
            dataset,
            org.jfree.chart.plot.PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        // Set bright colors
        chart.setBackgroundPaint(Color.WHITE);
        
        // Customize plot
        org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(250, 250, 250));
        plot.setOutlinePaint(new Color(200, 200, 200));
        plot.setRangeGridlinePaint(new Color(220, 220, 220));
        plot.setRangeGridlinesVisible(true);
        
        // Set bright colors for bars
        org.jfree.chart.renderer.category.BarRenderer renderer = 
            (org.jfree.chart.renderer.category.BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 107, 107));  // Red - Vốn
        renderer.setSeriesPaint(1, new Color(66, 165, 245));   // Blue - Doanh thu
        renderer.setSeriesPaint(2, new Color(76, 175, 80));    // Green - Lợi nhuận
        
        // Make bars brighter with shadow
        renderer.setShadowVisible(false);
        
        // Format axis
        plot.getDomainAxis().setLabelPaint(new Color(80, 80, 80));
        plot.getRangeAxis().setLabelPaint(new Color(80, 80, 80));
        plot.getDomainAxis().setTickLabelPaint(new Color(100, 100, 100));
        plot.getRangeAxis().setTickLabelPaint(new Color(100, 100, 100));
        
        // Format Y-axis to show numbers properly (not scientific notation)
        org.jfree.chart.axis.NumberAxis rangeAxis = (org.jfree.chart.axis.NumberAxis) plot.getRangeAxis();
        rangeAxis.setNumberFormatOverride(new java.text.DecimalFormat("#,##0"));
        
        // Format legend
        chart.getLegend().setBackgroundPaint(new Color(255, 255, 255));
        chart.getLegend().setItemPaint(new Color(80, 80, 80));
        
        ChartPanel chartPanelComponent = new ChartPanel(chart);
        chartPanelComponent.setPreferredSize(new Dimension(800, 300));
        panel.add(chartPanelComponent, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTablePanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        
        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Năm", "Vốn", "Doanh thu", "Lợi nhuận"},
            0
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Add sample data
        tableModel.addRow(new Object[]{"2018", "0đ", "0đ", "0đ"});
        tableModel.addRow(new Object[]{"2019", "0đ", "0đ", "0đ"});
        tableModel.addRow(new Object[]{"2020", "0đ", "0đ", "0đ"});
        tableModel.addRow(new Object[]{"2021", "0đ", "0đ", "0đ"});
        tableModel.addRow(new Object[]{"2022", "0đ", "0đ", "0đ"});
        tableModel.addRow(new Object[]{"2023", "604,200,000đ", "683,560,000đ", "79,360,000đ"});
        
        JTable tableData = new JTable(tableModel);
        tableData.setRowHeight(25);
        tableData.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(tableData);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
}
