package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Suppliers.CreateOrUpdateSupplierDTO;
import com.fat.DTO.Suppliers.SupplierViewDTO;

import java.util.List;

public interface ISupplierService {
    void createSupplier(CreateOrUpdateSupplierDTO dto);
    void updateSupplier(CreateOrUpdateSupplierDTO dto);
    void deleteSupplier(Integer id);
    List<SupplierViewDTO> getAllSuppliers();
    List<SupplierViewDTO> filterSupplierByList(String searchKey); // Filter từ ArrayList
    SupplierViewDTO getSupplierById(Integer id);
    void refreshCache();
}

