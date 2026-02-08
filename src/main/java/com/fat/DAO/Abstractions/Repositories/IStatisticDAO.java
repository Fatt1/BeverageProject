package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Statistics.*;

import java.time.LocalDate;
import java.util.List;

public interface IStatisticDAO {
    SummaryStatisticDTO getSummaryStatistics();
    List<RevenueDTO> getRevenueStatisticsByMonth(int year);
    List<RevenueDTO> getRevenueStatisticsByYear(int startYear, int endYear);
    List<RevenueDTO> getRevenueStatisticsByDay(int year, int month);
    List<RevenueDTO> getRevenueStatisticsByQuarter(int year, int quarter);

    List<RevenueDTO> getRevenueStatisticsByDateInMonth(int month, int year);

    List<CustomerStatisticDTO> getCustomerStatistic( LocalDate fromDate, LocalDate toDate);

    List<StockStatisticDTO> getStockStatistic(LocalDate fromDate, LocalDate toDate);

    List<StaffStatisticDTO> getSupplierStatistic(LocalDate fromDate, LocalDate toDate);

    List<ProductStatisticDTO> getProductStatistic(LocalDate fromDate, LocalDate toDate);

}
