package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.ISupplierService;
import com.fat.DAO.Abstractions.Repositories.ISupplierDAO;
import com.fat.DAO.Repositories.SupplierDAO;
import com.fat.DTO.Suppliers.SupplierDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierService implements ISupplierService {
    private static SupplierService instance;
    private final ISupplierDAO supplierDAO = SupplierDAO.getInstance();
    private static List<SupplierDTO> suppliersCache = new ArrayList<>();


    private SupplierService() {
        if(suppliersCache.isEmpty()) {
            suppliersCache = supplierDAO.getAll();
        }
    }

    public static SupplierService getInstance() {
        if (instance == null) {
            instance = new SupplierService();
        }
        return instance;
    }

    @Override
    public void createSupplier(SupplierDTO dto) {
        Integer id = supplierDAO.add(dto);
        if(id != null) {
            dto.setId(id);
            suppliersCache.addFirst(dto);
        }
    }

    @Override
    public void updateSupplier(SupplierDTO dto) {
        supplierDAO.update(dto);
        suppliersCache.removeIf(s -> s.getId().equals(dto.getId()));
        suppliersCache.addFirst(dto);
    }

    @Override
    public void deleteSupplier(Integer id) {
        supplierDAO.delete(id);
        suppliersCache.removeIf(s -> s.getId().equals(id));
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return suppliersCache;
    }

    @Override
    public List<SupplierDTO> filterSupplierByList(String searchKey) {
        String lowerCaseSearchKey = searchKey.toLowerCase();
        return suppliersCache.stream()
                .filter(supplier -> supplier.getName().toLowerCase().contains(lowerCaseSearchKey))
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDTO getSupplierById(Integer id) {
        return suppliersCache.stream()
                .filter(supplier -> supplier.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
