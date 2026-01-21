package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IImportService;
import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IImportDAO;
import com.fat.DAO.Repositories.ImportDAO;
import com.fat.DTO.Imports.CreateOrUpdateImportDTO;
import com.fat.DTO.Imports.ImportViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImportService implements IImportService {
    private static ImportService instance;
    private final IImportDAO importDAO;
    private final ArrayList<ImportViewDTO> importsCache = new ArrayList<>();

    private ImportService() {
        this.importDAO = ImportDAO.getInstance();
    }

    public static ImportService getInstance() {
        if (instance == null) {
            instance = new ImportService();
        }
        return instance;
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
    public List<ImportViewDTO> getAllImports() {
        // TODO: Implement getAll
        return null;
    }


    @Override
    public PagedResult<ImportViewDTO> filterImportByList(String keyword, LocalDateTime from, LocalDateTime to,
                                                   ImportStatus status, Integer staffId, Integer supplierId,
                                                   int pageIndex, int pageSize) {
        // TODO: Implement filter from ArrayList
        return null;
    }

    @Override
    public ReceptDetailDTO getImportById(Integer id) {
        return importDAO.getById(id);
    }
}

