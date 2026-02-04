package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.ICustomerService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.ICustomerDAO;
import com.fat.DAO.Repositories.CustomerDAO;
import com.fat.DTO.Customers.CustomerDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerService implements ICustomerService {
    private static CustomerService instance;
    private final ICustomerDAO customerDAO;
    private static List<CustomerDTO> customersCache = new ArrayList<>();

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
    public CustomerDTO getCustomerById(Integer id) {
        return customersCache.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        if(customersCache.isEmpty()) {
            customersCache = customerDAO.getAll();
        }
        return customersCache;
    }

    @Override
    public PagedResult<CustomerDTO> getAllCustomersPagination(int pageIndex, int pageSize) {
        return PagedResult.create(customersCache.stream(), pageIndex, pageSize);
    }

    @Override
    public PagedResult<CustomerDTO> filterCustomerByList(String keyword, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public void deleteCustomer(Integer id) {

    }

    @Override
    public void updateCustomer(CustomerDTO dto) {

    }

    @Override
    public void createCustomer(CustomerDTO dto) {
      return;
    }
}

