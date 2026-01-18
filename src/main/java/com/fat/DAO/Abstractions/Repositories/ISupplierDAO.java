package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Suppliers.CreateOrUpdateSupplierDTO;
import com.fat.DTO.Suppliers.SupplierViewDTO;

import java.util.List;

public interface ISupplierDAO extends IDAO<CreateOrUpdateSupplierDTO, Integer> {
   PagedResult<SupplierViewDTO> getAllPagination(int pageIndex, int pageSize);
    PagedResult<SupplierViewDTO> filter(String searchKey, int pageIndex, int pageSize);
    SupplierViewDTO getById(Integer id);
    List<SupplierViewDTO> getAll();
}
