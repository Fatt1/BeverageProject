package com.fat.DAO.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.ICustomerDAO;
import com.fat.DTO.Customers.CreateOrUpdateCustomerDTO;
import com.fat.DTO.Customers.CustomerViewDTO;

import java.util.List;

public class CustomerDAO implements ICustomerDAO {
    private static CustomerDAO instance;

    private CustomerDAO() {
    }

    public static CustomerDAO getInstance() {
        if (instance == null) {
            instance = new CustomerDAO();
        }
        return instance;
    }

    @Override
    public List<CustomerViewDTO> getAll() {
        return List.of();
    }

    @Override
    public CustomerViewDTO getById(Integer id) {
        return null;
    }


    @Override
    public Integer add(CreateOrUpdateCustomerDTO entity) {
        return null;
    }

    @Override
    public void update(CreateOrUpdateCustomerDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}
