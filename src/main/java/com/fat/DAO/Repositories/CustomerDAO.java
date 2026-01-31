package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.ICustomerDAO;
import com.fat.DTO.Customers.CustomerDTO;

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
    public List<CustomerDTO> getAll() {
        return List.of();
    }

    @Override
    public CustomerDTO getById(Integer id) {
        return null;
    }


    @Override
    public Integer add(CustomerDTO entity) {
        return null;
    }

    @Override
    public void update(CustomerDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}
