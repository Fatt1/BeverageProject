package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IImportService;
import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IImportDAO;
import com.fat.DAO.Repositories.ImportDAO;
import com.fat.DTO.Imports.ImportDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImportService implements IImportService {
    private static ImportService instance;
    private final IImportDAO importDAO;
    private static List<ImportDTO> importsCache = new ArrayList<>();

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
    public void createImport(ImportDTO dto) {

    }

    @Override
    public void updateImport(ImportDTO dto) {

    }

    @Override
    public void deleteImport(Integer id) {

    }

    @Override
    public List<ImportDTO> getAllImports() {
       return null;
    }

    @Override
    public ImportDTO getImportByCode(String code) {
        return null;
    }


    @Override
    public List<ImportDTO> filterImportByList(String keyword, LocalDateTime from, LocalDateTime to,
                                                   ImportStatus status, Integer staffId, Integer supplierId
                                                   ) {
        return null;
    }

    @Override
    public ImportDTO getImportById(Integer id) {
        return importsCache.stream()
            .filter(i -> i.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}

