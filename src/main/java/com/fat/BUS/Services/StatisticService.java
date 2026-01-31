package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IStatisticService;
import com.fat.DAO.Abstractions.Repositories.IStatisticDAO;
import com.fat.DAO.Repositories.StatisticDAO;
import com.fat.DTO.Statistics.StockStatisticDTO;

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
        stockStatistics = statisticDAO.getStockStatistic(LocalDate.of(2025,12,1), LocalDate.of(2025,12,31));
        return stockStatistics;
    }
}
