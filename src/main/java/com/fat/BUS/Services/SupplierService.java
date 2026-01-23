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
    private final ISupplierDAO supplierDAO;
    private  List<SupplierViewDTO> suppliersCache = new ArrayList<>();

    private SupplierService() {
        this.supplierDAO = SupplierDAO.getInstance();
    }

    public static SupplierService getInstance() {
        if (instance == null) {
            instance = new SupplierService();
        }
        return instance;
    }

    @Override
    public void createSupplier(CreateOrUpdateSupplierDTO dto) {

    }

    @Override
    public void updateSupplier(CreateOrUpdateSupplierDTO dto) {

    }

    @Override
    public void deleteSupplier(Integer id) {

    }

    @Override
    public List<SupplierViewDTO> getAllSuppliers() {
        return List.of();
    }

    @Override
    public List<SupplierViewDTO> filterSupplierByList(String searchKey) {
        // TODO: Implement filter from ArrayList
        return null;
    }

    @Override
    public SupplierViewDTO getSupplierById(Integer id) {
        return null;
    }

    @Override
    public void refreshCache() {
        suppliersCache = supplierDAO.getAll();
    }
}
