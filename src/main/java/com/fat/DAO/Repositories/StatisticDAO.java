package com.fat.DAO.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IStaffDAO;
import com.fat.DAO.Abstractions.Repositories.IStatisticDAO;
import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;
import com.fat.DTO.Statistics.*;

import java.time.LocalDate;
import java.util.List;

public class StatisticDAO implements IStatisticDAO {

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
    public List<CustomerStatisticDTO> getCustomerStatistic(String searchKey, LocalDate fromDate, LocalDate toDate) {
        return List.of();
    }

    @Override
    public PagedResult<StockStatisticDTO> getStockStatistic(String searchKey, LocalDate fromDate, LocalDate toDate, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public List<SupplierStatisticDTO> getSupplierStatistic(String searchKey, LocalDate fromDate, LocalDate toDate) {
        return List.of();
    }
}
