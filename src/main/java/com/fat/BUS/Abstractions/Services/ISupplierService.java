package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Suppliers.CreateOrUpdateSupplierDTO;
import com.fat.DTO.Suppliers.SupplierViewDTO;

import java.util.List;

public interface ISupplierService {
    void createSupplier(CreateOrUpdateSupplierDTO dto);
    void updateSupplier(CreateOrUpdateSupplierDTO dto);
    void deleteSupplier(Integer id);
    PagedResult<SupplierViewDTO> getAllSuppliersPagination(int pageIndex, int pageSize);
    List<SupplierViewDTO> getAllSuppliers();
    PagedResult<SupplierViewDTO> filterSupplier(String searchKey, int pageIndex, int pageSize);
    SupplierViewDTO getSupplierById(Integer id);
}

