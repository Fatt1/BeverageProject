package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Suppliers.CreateOrUpdateSupplierDTO;
import com.fat.DTO.Suppliers.SupplierViewDTO;

import java.util.List;

public interface ISupplierDAO extends IDAO<CreateOrUpdateSupplierDTO, Integer> {
    List<SupplierViewDTO> getAll();
    SupplierViewDTO getById(Integer id);
}
