package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IImportService;
import com.fat.BUS.Utils.ValidatorUtil;
import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.Contract.Exceptions.ValidationException;
import com.fat.DAO.Abstractions.Repositories.IImportDAO;
import com.fat.DAO.Repositories.ImportDAO;
import com.fat.DTO.Imports.ImportDTO;
import com.fat.DTO.Imports.ImportDetailDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImportService implements IImportService {
    private static ImportService instance;
    private final IImportDAO importDAO = ImportDAO.getInstance();
    private static List<ImportDTO> importsCache = new ArrayList<>();

    private ImportService() {
        if(importsCache.isEmpty())
            importsCache = importDAO.getAll();
    }

    public static ImportService getInstance() {
        if (instance == null) {
            instance = new ImportService();
        }
        return instance;
    }

    @Override
    public void createImport(ImportDTO dto) {
        ValidatorUtil.validate(dto);
        if(dto.getImportDetails().isEmpty()){
            throw new ValidationException("Phieu nhap can co it nhat 1 san pham");
        }
        BigDecimal totalPrice = dto.getImportDetails().stream().map(ImportDetailDTO::getSubTotal).reduce(BigDecimal.ZERO,BigDecimal::add);
        dto.setTotalPrice(totalPrice);
        dto.setStatus(ImportStatus.PENDING);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        Integer id = importDAO.add(dto);
        if(id != null){
            dto.setId(id);
            importsCache.addFirst(dto);
        }
    }

    @Override
    public void updateImport(ImportDTO dto) {
        ValidatorUtil.validate(dto);
        ImportDTO existingImport = importDAO.getById(dto.getId());
        if(existingImport == null){
            throw new ValidationException("Phieu nhap khong ton tai");
        }
        if(existingImport.getStatus() != ImportStatus.PENDING){
            throw new ValidationException("Chi duoc cap nhat phieu nhap co trang thai DANG XU LY");
        }
        BigDecimal total = dto.getImportDetails().stream().map(ImportDetailDTO::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalPrice(total);
        dto.setUpdatedAt(LocalDateTime.now());
        importDAO.update(dto);
        
        // Update cache
        importsCache.removeIf(i -> i.getId().equals(dto.getId()));
        importsCache.addFirst(dto);
    }

    @Override
    public void deleteImport(Integer id) {
        ImportDTO existingImport = importDAO.getById(id);
        if(existingImport == null){
            throw new ValidationException("Phieu nhap khong ton tai");
        }
        if(existingImport.getStatus() == ImportStatus.COMPLETED){
            throw new ValidationException("Khong duoc HUY phieu nhap co trang thai HOAN THANH");
        }
        importDAO.delete(id);
        importsCache.removeIf(importDTO -> importDTO.getId().equals(id));
    }

    @Override
    public List<ImportDTO> getAllImports() {
       return new ArrayList<>(importsCache);
    }

    @Override
    public ImportDTO getImportByCode(String code) {
        return importsCache.stream().filter(importDTO -> importDTO.getImportCode().equals(code)).findFirst().orElse(null);
    }


    @Override
    public List<ImportDTO> filterImportByList(String keyword, LocalDateTime from, LocalDateTime to, ImportStatus status, Integer staffId, Integer supplierId)
    {

        return importsCache.stream().filter(importDTO -> keyword == null || keyword.isBlank() || importDTO.getImportCode().toLowerCase().contains(keyword.toLowerCase()))
                .filter(importDTO -> from == null || importDTO.getCreatedAt().isAfter(from) || importDTO.getCreatedAt().isEqual(from))
                .filter(importDTO -> to == null || importDTO.getCreatedAt().isBefore(to) || importDTO.getCreatedAt().isEqual(to))
                .filter(importDTO -> status == null || importDTO.getStatus() == status)
                .filter(importDTO -> staffId == null || importDTO.getStaffId().equals(staffId))
                .filter(importDTO -> supplierId == null || importDTO.getSupplierId().equals(supplierId)).toList();
    }

    @Override
    public ImportDTO getImportById(Integer id) {
        // Load from DAO to get full details, not from cache
        return importDAO.getById(id);
    }

    @Override
    public void confirmImport(Integer id) {
        ImportDTO existingImport = importDAO.getById(id);
        if(existingImport == null){
            throw new ValidationException("Phieu nhap khong ton tai");
        }
        if(existingImport.getStatus() != ImportStatus.PENDING){
            throw new ValidationException("Chi co the XAC NHAN PHIEU NHAP co trang thai DANG XU LY");
        }
        importDAO.confirmImport(id);
        
        // Update cache: set status = COMPLETED
        existingImport.setStatus(ImportStatus.COMPLETED);
        existingImport.setUpdatedAt(LocalDateTime.now());
        importsCache.removeIf(importDTO -> importDTO.getId().equals(id));
        importsCache.addFirst(existingImport);
        // Update product cache
        for(ImportDetailDTO detail : existingImport.getImportDetails()){
            ProductService.getInstance().updateProductStock(detail.getProductId(), detail.getQuantity());
        }

    }

    @Override
    public void cancelImport(Integer id) {
        ImportDTO existingImport = importDAO.getById(id);
        if(existingImport == null){
            throw new ValidationException("Phieu nhap khong ton tai");
        }
        if(existingImport.getStatus() == ImportStatus.COMPLETED){
            throw new ValidationException("Khong the HUY phieu nhap da HOAN THANH");
        }
        if(existingImport.getStatus() == ImportStatus.CANCELLED){
            throw new ValidationException("Phieu nhap da duoc HUY truoc do");
        }
        
        // Update status to CANCELLED in database
        existingImport.setStatus(ImportStatus.CANCELLED);
        existingImport.setUpdatedAt(LocalDateTime.now());
        importDAO.update(existingImport);
        
        // Update cache
        importsCache.removeIf(importDTO -> importDTO.getId().equals(id));
        importsCache.addFirst(existingImport);
    }

}

