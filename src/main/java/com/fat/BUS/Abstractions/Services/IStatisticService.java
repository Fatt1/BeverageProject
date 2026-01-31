package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Statistics.StockStatisticDTO;

import java.time.LocalDate;
import java.util.List;

public interface IStatisticService {
    List<StockStatisticDTO> getStockStatistic(LocalDate fromDate, LocalDate toDate);

}
