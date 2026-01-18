package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Imports.CreateOrUpdateImportDTO;
import com.fat.DTO.Imports.ImportViewDTO;
import com.fat.DTO.Receipts.ReceiptViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.time.LocalDateTime;

public interface IImportDAO extends IDAO<CreateOrUpdateImportDTO, Integer> {
    PagedResult<ImportViewDTO> getAllPagination(int pageIndex, int pageSize, SortOrder sortOrder, ReceiptSort sortBy);
    PagedResult<ImportViewDTO> filter(String keyword, LocalDateTime from , LocalDateTime to,
                                      ImportStatus status, Integer staffId, Integer supplierId,
                                      int pageIndex, int pageSize);
    ReceptDetailDTO getById(Integer id);
}
