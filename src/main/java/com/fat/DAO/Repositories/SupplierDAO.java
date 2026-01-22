package com.fat.DAO.Repositories;
import java.util.List;

import com.fat.DTO.Suppliers.SupplierViewDTO;
import com.fat.DTO.Suppliers.CreateOrUpdateSupplierDTO;
import com.fat.DAO.Abstractions.Repositories.ISupplierDAO;


public class SupplierDAO implements ISupplierDAO {
    private static SupplierDAO instance;

    private SupplierDAO() {
    }

    public static SupplierDAO getInstance() {
        if (instance == null) {
            instance = new SupplierDAO();
        }
        return instance;
    }

    @Override
    public List<SupplierViewDTO> getAll() {
        return List.of();
    }


    @Override
    public SupplierViewDTO getById(Integer id) {
        return null;
    }

    @Override
    public Integer add(CreateOrUpdateSupplierDTO entity) {
        return null;
    }

    @Override
    public void update(CreateOrUpdateSupplierDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}



