package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Statistics.*;

import java.time.LocalDate;
import java.util.List;

public interface IStatisticDAO {
    SummaryStatisticDTO getSummaryStatistics();
    List<RevenueDTO> getRevenueStatisticsByMonth(int year);
    List<RevenueDTO> getRevenueStatisticsByYear(int startYear, int endYear);

    List<RevenueDTO> getRevenueStatisticsByDateInMonth(int month, int year);

    List<CustomerStatisticDTO> getCustomerStatistic(String searchKey, LocalDate fromDate, LocalDate toDate);

    PagedResult<StockStatisticDTO> getStockStatistic(String searchKey, LocalDate fromDate, LocalDate toDate, int pageIndex, int pageSize);

    List<SupplierStatisticDTO> getSupplierStatistic(String searchKey, LocalDate fromDate, LocalDate toDate);

}
