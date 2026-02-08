package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.IStatisticDAO;
import com.fat.DAO.Utils.DbContext;

import com.fat.DTO.Statistics.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StatisticDAO implements IStatisticDAO {
    private static StatisticDAO instance;

    private StatisticDAO() {
    }

    public static StatisticDAO getInstance() {
        if (instance == null) {
            instance = new StatisticDAO();
        }
        return instance;
    }

    @Override
    public SummaryStatisticDTO getSummaryStatistics() {
        return null;
    }

    @Override
    public List<RevenueDTO> getRevenueStatisticsByMonth(int year) {
        String sql = """
            WITH MonthRange AS (
                SELECT 1 AS month
                UNION ALL
                SELECT month + 1 FROM MonthRange WHERE month < 12
            )
            SELECT 
                CONCAT(DATENAME(MONTH, DATEFROMPARTS(?, mr.month, 1)), ' ', ?) AS timeLabel,
                ISNULL(SUM(r.TotalAmount), 0) AS revenue,
                ISNULL(SUM(i.TotalPrice), 0) AS cost,
                ISNULL(SUM(r.TotalAmount), 0) - ISNULL(SUM(i.TotalPrice), 0) AS profit
            FROM MonthRange mr
            LEFT JOIN Receipt r ON YEAR(r.CreatedAt) = ? AND MONTH(r.CreatedAt) = mr.month
            LEFT JOIN Import i ON YEAR(i.CreatedAt) = ? AND MONTH(i.CreatedAt) = mr.month
            GROUP BY mr.month
            ORDER BY mr.month
        """;
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1, year);
            ps.setInt(2, year);
            ps.setInt(3, year);
            ps.setInt(4, year);
            var rs = ps.executeQuery();
            List<RevenueDTO> revenues = new java.util.ArrayList<>();
            while (rs.next()){
                RevenueDTO dto = new RevenueDTO(
                        rs.getString("timeLabel"),
                        new java.math.BigDecimal(rs.getLong("revenue")),
                        new java.math.BigDecimal(rs.getLong("profit")),
                        new java.math.BigDecimal(rs.getLong("cost"))
                );
                revenues.add(dto);
            }
            return revenues;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RevenueDTO> getRevenueStatisticsByYear(int startYear, int endYear) {
        String sql = """
            WITH YearRange AS (
                SELECT ? AS year
                UNION ALL
                SELECT year + 1 FROM YearRange WHERE year < ?
            )
            SELECT 
                CAST(yr.year AS VARCHAR(4)) AS timeLabel,
                ISNULL(SUM(r.TotalAmount), 0) AS revenue,
                ISNULL(SUM(i.TotalPrice), 0) AS cost,
                ISNULL(SUM(r.TotalAmount), 0) - ISNULL(SUM(i.TotalPrice), 0) AS profit
            FROM YearRange yr
            LEFT JOIN Receipt r ON YEAR(r.CreatedAt) = yr.year
            LEFT JOIN Import i ON YEAR(i.CreatedAt) = yr.year
            GROUP BY yr.year
            ORDER BY yr.year
        """;
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1, startYear);
            ps.setInt(2, endYear);
            var rs = ps.executeQuery();
            List<RevenueDTO> revenues = new java.util.ArrayList<>();
            while (rs.next()){
                RevenueDTO dto = new RevenueDTO(
                        rs.getString("timeLabel"),
                        new java.math.BigDecimal(rs.getLong("revenue")),
                        new java.math.BigDecimal(rs.getLong("profit")),
                        new java.math.BigDecimal(rs.getLong("cost"))
                );
                revenues.add(dto);
            }
            return revenues;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RevenueDTO> getRevenueStatisticsByDay(int year, int month) {
        String sql = """
            SELECT 
                CAST(r.CreatedAt AS DATE) AS receiptDate,
                ISNULL(SUM(r.TotalAmount), 0) AS totalSales,
                0 AS totalImport,
                ISNULL(SUM(r.TotalAmount), 0) AS revenue,
                0 AS cost,
                ISNULL(SUM(r.TotalAmount), 0) AS profit
            FROM Receipt r
            WHERE YEAR(r.CreatedAt) = ? AND MONTH(r.CreatedAt) = ?
            GROUP BY CAST(r.CreatedAt AS DATE)
            ORDER BY CAST(r.CreatedAt AS DATE)
        """;
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1, year);
            ps.setInt(2, month);
            var rs = ps.executeQuery();
            List<RevenueDTO> revenues = new java.util.ArrayList<>();
            while (rs.next()){
                RevenueDTO dto = new RevenueDTO(
                        rs.getDate("receiptDate").toLocalDate(),
                        new java.math.BigDecimal(rs.getLong("revenue")),
                        new java.math.BigDecimal(rs.getLong("profit")),
                        new java.math.BigDecimal(rs.getLong("cost"))
                );
                revenues.add(dto);
            }
            return revenues;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RevenueDTO> getRevenueStatisticsByDateInMonth(int month, int year) {
        return List.of();
    }

    @Override
    public List<CustomerStatisticDTO> getCustomerStatistic( LocalDate fromDate, LocalDate toDate) {
        return List.of();
    }

    @Override
    public List<StockStatisticDTO> getStockStatistic(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime start = fromDate.atStartOfDay();
        LocalDateTime end = toDate.atTime(23, 59, 59);

        String sql = """
        WITH OpeningStock AS (
            SELECT productId, stockAfter,
            ROW_NUMBER() OVER (PARTITION BY productId ORDER BY createdAt DESC) AS rn
            FROM InventoryHistory
            WHERE createdAt < ?
        ),
        PeriodSum AS (
            SELECT productId, 
            SUM(CASE WHEN type = 0 THEN quantity ELSE 0 END) AS totalImport,
            SUM(CASE WHEN type = 1 THEN quantity ELSE 0 END) AS totalExport
            FROM InventoryHistory
            WHERE createdAt BETWEEN ? AND ?
            GROUP BY productId
        )
        SELECT 
            p.id As productId,
            p.name AS productName,
            ISNULL(os.stockAfter, 0) AS initialStock,
            ISNULL(ps.totalImport, 0) AS importedQuantity, -- Đã thêm ,0
            ISNULL(ps.totalExport, 0) AS exportedQuantity, -- Đã thêm ,0
            ISNULL(os.stockAfter, 0) + ISNULL(ps.totalImport, 0) + ISNULL(ps.totalExport, 0) AS finalStock
        FROM OpeningStock os 
        FULL OUTER JOIN PeriodSum ps ON os.productId = ps.productId AND os.rn = 1
        RIGHT JOIN Product p ON p.id = COALESCE(os.productId, ps.productId)
        WHERE (os.rn = 1 OR os.rn IS NULL)
        ORDER BY p.id DESC
        """;
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setObject(1, start);
            ps.setObject(2, start);
            ps.setObject(3, end);
            var rs = ps.executeQuery();
            List<StockStatisticDTO> stockStatistics = new java.util.ArrayList<>();
            while (rs.next()){
                StockStatisticDTO dto = new StockStatisticDTO(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getInt("finalStock"),
                        rs.getInt("initialStock"),
                        rs.getInt("importedQuantity"),
                        rs.getInt("exportedQuantity")
                );
                stockStatistics.add(dto);
            }
            return stockStatistics;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }

    @Override
    public List<StaffStatisticDTO> getStaffStatistic(LocalDate fromDate, LocalDate toDate) {
        return List.of();
    }

    @Override
    public List<RevenueDTO> getRevenueStatisticsByQuarter(int year, int quarter) {
        String sql = """
            WITH QuarterRange AS (
                SELECT 1 AS qtr
                UNION ALL
                SELECT qtr + 1 FROM QuarterRange WHERE qtr < 4
            )
            SELECT 
                'Q' + CAST(qr.qtr AS VARCHAR(1)) + ' ' + CAST(? AS VARCHAR(4)) AS timeLabel,
                ISNULL(SUM(r.TotalAmount), 0) AS revenue,
                ISNULL(SUM(i.TotalPrice), 0) AS cost,
                ISNULL(SUM(r.TotalAmount), 0) - ISNULL(SUM(i.TotalPrice), 0) AS profit
            FROM QuarterRange qr
            LEFT JOIN Receipt r ON YEAR(r.CreatedAt) = ? 
                AND CEILING(CAST(MONTH(r.CreatedAt) AS FLOAT) / 3) = qr.qtr
            LEFT JOIN Import i ON YEAR(i.CreatedAt) = ? 
                AND CEILING(CAST(MONTH(i.CreatedAt) AS FLOAT) / 3) = qr.qtr
            GROUP BY qr.qtr
            ORDER BY qr.qtr
        """;
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1, year);
            ps.setInt(2, year);
            ps.setInt(3, year);
            var rs = ps.executeQuery();
            List<RevenueDTO> revenues = new java.util.ArrayList<>();
            while (rs.next()){
                RevenueDTO dto = new RevenueDTO(
                        rs.getString("timeLabel"),
                        new java.math.BigDecimal(rs.getLong("revenue")),
                        new java.math.BigDecimal(rs.getLong("profit")),
                        new java.math.BigDecimal(rs.getLong("cost"))
                );
                revenues.add(dto);
            }
            return revenues;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }


    @Override
    public List<ProductStatisticDTO> getProductStatistic(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime start = fromDate.atStartOfDay();
        LocalDateTime end = toDate.atTime(23, 59, 59);
        String sql = """
                SELECT  
                p.id As productId,
                p.name AS productName,
                ISNULL(SUM(rd.quantity), 0) AS totalSoldQuantity,
                ISNULL(SUM(rd.subTotalAmount - rd.DiscountAmount), 0) AS totalSalesAmount
                FROM Product p
                LEFT JOIN ReceiptDetail rd ON p.id = rd.productId
                LEFT JOIN Receipt r ON rd.receiptId = r.id
                AND r.CreatedAt BETWEEN ? AND ?
                GROUP BY p.id, p.name
                ORDER BY totalSalesAmount DESC
            """;
        try(Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setObject(1, start);
            ps.setObject(2, end);
            var rs = ps.executeQuery();
            List<ProductStatisticDTO> productStatistics = new ArrayList<>();
            while(rs.next()){
                ProductStatisticDTO dto = new ProductStatisticDTO(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getInt("totalSoldQuantity"),
                        rs.getBigDecimal("totalSalesAmount")
                );
                productStatistics.add(dto);
            }
            return productStatistics;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException("Lỗi khi truy xuất thống kê sản phẩm");
        }

    }
}
