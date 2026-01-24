package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.ISupplierService;
import com.fat.DAO.Abstractions.Repositories.ISupplierDAO;
import com.fat.DAO.Repositories.SupplierDAO;
import com.fat.DTO.Suppliers.CreateOrUpdateSupplierDTO;
import com.fat.DTO.Suppliers.SupplierViewDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierService implements ISupplierService {
    private static SupplierService instance;
    private final ISupplierDAO supplierDAO = SupplierDAO.getInstance();
    private final List<SupplierViewDTO> suppliersCache ;

    private SupplierService() {
        suppliersCache = supplierDAO.getAll();
    }

    public static SupplierService getInstance() {
        if (instance == null) {
            instance = new SupplierService();
        }
        return instance;
    }

    @Override
    public void createSupplier(CreateOrUpdateSupplierDTO dto) {
        supplierDAO.add(dto);
    }

    @Override
    public void updateSupplier(CreateOrUpdateSupplierDTO dto) {
        supplierDAO.update(dto);
    }

    @Override
    public void deleteSupplier(Integer id) {
        supplierDAO.delete(id);
    }

    @Override
    public List<SupplierViewDTO> getAllSuppliers() {
        return suppliersCache;
    }

    @Override
    public List<SupplierViewDTO> filterSupplierByList(String searchKey) {
        // TODO: Implement filter from ArrayList
        String lowerCaseSearchKey = searchKey.toLowerCase();
        return suppliersCache.stream()
                .filter(supplier -> supplier.getName().toLowerCase().contains(lowerCaseSearchKey))
                .collect(Collectors.toList());
    }

    @Override
    public SupplierViewDTO getSupplierById(Integer id) {
        for (SupplierViewDTO supplier : suppliersCache) {
            if (supplier.getId().equals(id)) {
                return supplier;
            }
        }
        return null;
    }
}
