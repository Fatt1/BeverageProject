package com.fat.DAO.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IStaffDAO;
import com.fat.DAO.Abstractions.Repositories.IStatisticDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Auths.UserSessionDTO;

import com.fat.DTO.Statistics.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        return List.of();
    }

    @Override
    public List<RevenueDTO> getRevenueStatisticsByYear(int startYear, int endYear) {
        return List.of();
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
    public List<SupplierStatisticDTO> getSupplierStatistic( LocalDate fromDate, LocalDate toDate) {
        return List.of();
    }
}
