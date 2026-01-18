package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.ISupplierService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Suppliers.CreateOrUpdateSupplierDTO;
import com.fat.DTO.Suppliers.SupplierViewDTO;

import java.util.List;

public class SupplierService implements ISupplierService {
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
    public PagedResult<SupplierViewDTO> getAllSuppliersPagination(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public List<SupplierViewDTO> getAllSuppliers() {
        return List.of();
    }

    @Override
    public PagedResult<SupplierViewDTO> filterSupplier(String searchKey, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public SupplierViewDTO getSupplierById(Integer id) {
        return null;
    }
}
