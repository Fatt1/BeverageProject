package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Statistics.ProductStatisticDTO;
import com.fat.DTO.Statistics.RevenueDTO;
import com.fat.DTO.Statistics.StockStatisticDTO;

import java.time.LocalDate;
import java.util.List;

public interface IStatisticService {
    List<StockStatisticDTO> getStockStatistic(LocalDate fromDate, LocalDate toDate);
    List<RevenueDTO> getRevenueStatisticsByMonth(int year);
    List<RevenueDTO> getRevenueStatisticsByYear(int startYear, int endYear);
    List<RevenueDTO> getRevenueStatisticsByDay(int year, int month);
    List<RevenueDTO> getRevenueStatisticsByQuarter(int year, int quarter);
    List<ProductStatisticDTO> getProductStatistics(LocalDate fromDate, LocalDate toDate);

}
