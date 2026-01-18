package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Imports.CreateOrUpdateImportDTO;
import com.fat.DTO.Imports.ImportViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.time.LocalDateTime;

public interface IImportService {
    void createImport(CreateOrUpdateImportDTO dto);
    void updateImport(CreateOrUpdateImportDTO dto);
    void deleteImport(Integer id);
    PagedResult<ImportViewDTO> getAllImportsPagination(int pageIndex, int pageSize, SortOrder sortOrder, ReceiptSort sortBy);
    PagedResult<ImportViewDTO> filterImport(String keyword, LocalDateTime from, LocalDateTime to,
                                            ImportStatus status, Integer staffId, Integer supplierId,
                                            int pageIndex, int pageSize);
    ReceptDetailDTO getImportById(Integer id);
}

