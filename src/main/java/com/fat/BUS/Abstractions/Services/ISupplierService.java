package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Suppliers.SupplierDTO;

import java.util.List;

public interface ISupplierService {
    void createSupplier(SupplierDTO dto);
    void updateSupplier(SupplierDTO dto);
    void deleteSupplier(Integer id);
    List<SupplierDTO> getAllSuppliers();
    List<SupplierDTO> filterSupplierByList(String searchKey); // Filter từ ArrayList
    SupplierDTO getSupplierById(Integer id);
}

