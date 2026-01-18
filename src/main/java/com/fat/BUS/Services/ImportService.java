package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IImportService;
import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IImportDAO;
import com.fat.DTO.Imports.CreateOrUpdateImportDTO;
import com.fat.DTO.Imports.ImportViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.time.LocalDateTime;

public class ImportService implements IImportService {
    private final IImportDAO importDAO;

    public ImportService(IImportDAO importDAO) {
        this.importDAO = importDAO;
    }

    @Override
    public void createImport(CreateOrUpdateImportDTO dto) {
        importDAO.add(dto);
    }

    @Override
    public void updateImport(CreateOrUpdateImportDTO dto) {
        importDAO.update(dto);
    }

    @Override
    public void deleteImport(Integer id) {
        importDAO.delete(id);
    }

    @Override
    public PagedResult<ImportViewDTO> getAllImportsPagination(int pageIndex, int pageSize, SortOrder sortOrder, ReceiptSort sortBy) {
        return importDAO.getAllPagination(pageIndex, pageSize, sortOrder, sortBy);
    }

    @Override
    public PagedResult<ImportViewDTO> filterImport(String keyword, LocalDateTime from, LocalDateTime to,
                                                   ImportStatus status, Integer staffId, Integer supplierId,
                                                   int pageIndex, int pageSize) {
        return importDAO.filter(keyword, from, to, status, staffId, supplierId, pageIndex, pageSize);
    }

    @Override
    public ReceptDetailDTO getImportById(Integer id) {
        return importDAO.getById(id);
    }
}

