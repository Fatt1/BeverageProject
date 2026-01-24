package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.ICustomerService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.ICustomerDAO;
import com.fat.DAO.Repositories.CustomerDAO;
import com.fat.DTO.Customers.CreateOrUpdateCustomerDTO;
import com.fat.DTO.Customers.CustomerViewDTO;

import java.util.ArrayList;
import java.util.List;

public class CustomerService implements ICustomerService {
    private static CustomerService instance;
    private final ICustomerDAO customerDAO;
    private List<CustomerViewDTO> customersCache = new ArrayList<>();

    private CustomerService() {
        this.customerDAO = CustomerDAO.getInstance();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    @Override
    public CustomerViewDTO getCustomerById(Integer id) {
        return null;
    }

    @Override
    public List<CustomerViewDTO> getAllCustomers() {
        return List.of();
    }

    @Override
    public PagedResult<CustomerViewDTO> getAllCustomersPagination(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public PagedResult<CustomerViewDTO> filterCustomerByList(String keyword, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public void deleteCustomer(Integer id) {

    }

    @Override
    public void updateCustomer(CreateOrUpdateCustomerDTO dto) {

    }

    @Override
    public void createCustomer(CreateOrUpdateCustomerDTO dto) {

    }

    @Override
    public void refreshCache() {
        customersCache = customerDAO.getAll();
    }
}

