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
import java.util.List;

public interface IImportDAO extends IDAO<CreateOrUpdateImportDTO, Integer> {
    List<ImportViewDTO> getAll();
    ReceptDetailDTO getById(Integer id);
}
