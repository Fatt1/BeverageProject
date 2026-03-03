/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.fat.GUI.Panels.Statistics;

import com.fat.GUI.Panels.Statistics.Customer.CustomerYearPanel;
import com.fat.GUI.Panels.Statistics.Customer.CustomerMonthOfYear;
import com.fat.GUI.Panels.Statistics.Customer.CustomerDayToDay;
import com.fat.GUI.Panels.Statistics.Customer.CustomerQuarter;

/**
 *
 * @author nghia
 */
public class CustomerPanel extends javax.swing.JPanel {

    /**
     * Creates new form CustomerPanel
     */
    public CustomerPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new java.awt.BorderLayout());

        javax.swing.JTabbedPane jTabbedPane1 = new javax.swing.JTabbedPane();

        CustomerYearPanel customerYearPanel = new CustomerYearPanel();
        CustomerMonthOfYear customerMonthOfYear = new CustomerMonthOfYear();
        CustomerDayToDay customerDayToDay = new CustomerDayToDay();
        CustomerQuarter customerQuarter = new CustomerQuarter();

        jTabbedPane1.addTab("Năm", customerYearPanel);
        jTabbedPane1.addTab("Tháng trong năm", customerMonthOfYear);
        jTabbedPane1.addTab("Ngày tới ngày", customerDayToDay);
        jTabbedPane1.addTab("Quý", customerQuarter);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }
}
