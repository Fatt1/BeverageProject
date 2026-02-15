package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Statistics.*;

import java.time.LocalDate;
import java.util.List;

public interface IStatisticService {
    List<StockStatisticDTO> getStockStatistic(LocalDate fromDate, LocalDate toDate);
    List<RevenueDTO> getRevenueStatisticsByMonth(int year);
    List<RevenueDTO> getRevenueStatisticsByYear(int startYear, int endYear);
    List<RevenueDTO> getRevenueStatisticsByDay(int year, int month);
    List<RevenueDTO> getRevenueStatisticsByQuarter(int year, int quarter);
    List<ProductStatisticDTO> getProductStatistics(LocalDate fromDate, LocalDate toDate);
    List<StaffStatisticDTO> getStaffStatistics(LocalDate fromDate, LocalDate toDate);
    List<StaffQuarterStatisticDTO> getStaffQuarterStatistic(int year);
    List<ProductQuarterStatisticDTO> getProductQuarterStatistic(int year);

}
