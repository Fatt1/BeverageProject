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
    List<ProductStatisticDTO> getProductStatistics(LocalDate fromDate, LocalDate toDate, String searchKey);
    List<StaffStatisticDTO> getStaffStatistics(LocalDate fromDate, LocalDate toDate);
    List<StaffQuarterStatisticDTO> getStaffQuarterStatistic(int year);
    List<ProductQuarterStatisticDTO> getProductQuarterStatistic(int year);
    List<CustomerStatisticDTO> getCustomerStatistic(LocalDate fromDate, LocalDate toDate);
    List<CustomerQuarterStatisticDTO> getCustomerQuarterStatistic(int year);
    List<CustomerProductStatisticDTO> getCustomerProductStatistic(int year);
    List<StaffProductStatisticDTO> getStaffProductStatistic(int year);
}
