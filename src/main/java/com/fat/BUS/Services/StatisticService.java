package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IStatisticService;
import com.fat.DAO.Abstractions.Repositories.IStatisticDAO;
import com.fat.DAO.Repositories.StatisticDAO;
import com.fat.DTO.Statistics.*;

import java.time.LocalDate;
import java.util.List;

public class StatisticService implements IStatisticService{
    public static StatisticService instance;
    private List<StockStatisticDTO> stockStatistics;
    private IStatisticDAO statisticDAO = StatisticDAO.getInstance();
    private StatisticService() {
    }
    public static StatisticService getInstance() {
        if (instance == null) {
            instance = new StatisticService();
        }
        return instance;
    }

    @Override
    public List<StockStatisticDTO> getStockStatistic(LocalDate fromDate, LocalDate toDate) {
        stockStatistics = statisticDAO.getStockStatistic(fromDate, toDate);
        return stockStatistics;
    }
    @Override
    public List<RevenueDTO> getRevenueStatisticsByMonth(int year) {
        return statisticDAO.getRevenueStatisticsByMonth(year);
    }
    @Override
    public List<RevenueDTO> getRevenueStatisticsByYear(int startYear, int endYear) {
        return statisticDAO.getRevenueStatisticsByYear(startYear, endYear);
    }
    @Override
    public List<RevenueDTO> getRevenueStatisticsByDay(int year, int month) {
        return statisticDAO.getRevenueStatisticsByDay(year, month);
    }
    @Override
    public List<RevenueDTO> getRevenueStatisticsByQuarter(int year, int quarter) {
        return statisticDAO.getRevenueStatisticsByQuarter(year, quarter);
    }

    @Override
    public List<ProductStatisticDTO> getProductStatistics(LocalDate fromDate, LocalDate toDate) {
        return statisticDAO.getProductStatistic(fromDate, toDate);
    }

    @Override
    public List<StaffStatisticDTO> getStaffStatistics(LocalDate fromDate, LocalDate toDate) {
        return List.of();
    }

    @Override
    public List<ProductQuarterStatisticDTO> getProductQuarterStatistic(int year) {
        return statisticDAO.getProductQuarterStatistic(year);
    }

}
