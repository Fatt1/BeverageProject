package com.fat.DAO.Repositories;
import java.util.List;

import com.fat.DTO.Suppliers.SupplierViewDTO;
import com.fat.DTO.Suppliers.CreateOrUpdateSupplierDTO;
import com.fat.DAO.Abstractions.Repositories.ISupplierDAO;
import com.fat.Contract.Shared.PagedResult;


public class SupplierDAO implements ISupplierDAO {


    @Override
    public PagedResult<SupplierViewDTO> getAllPagination(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public PagedResult<SupplierViewDTO> filter(String searchKey, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public SupplierViewDTO getById(Integer id) {
        return null;
    }

    @Override
    public List<SupplierViewDTO> getAll() {
        return List.of();
    }

    @Override
    public void add(CreateOrUpdateSupplierDTO entity) {

    }

    @Override
    public void update(CreateOrUpdateSupplierDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}



