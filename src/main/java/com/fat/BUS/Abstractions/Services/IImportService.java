package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Imports.ImportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IImportService {
    void createImport(ImportDTO dto);
    void updateImport(ImportDTO dto);
    void deleteImport(Integer id);
    List<ImportDTO> getAllImports();
    ImportDTO getImportByCode(String code);
    List<ImportDTO> filterImportByList(String keyword, LocalDateTime from, LocalDateTime to,
                                            ImportStatus status, Integer staffId, Integer supplierId
                                            ); // Filter từ ArrayList
    ImportDTO getImportById(Integer id);
}

