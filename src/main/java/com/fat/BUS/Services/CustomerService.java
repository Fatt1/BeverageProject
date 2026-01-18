package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.ICustomerService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.ICustomerDAO;
import com.fat.DTO.Customers.CreateOrUpdateCustomerDTO;
import com.fat.DTO.Customers.CustomerViewDTO;

import java.util.List;

public class CustomerService implements ICustomerService {
    private final ICustomerDAO customerDAO;

    public CustomerService(ICustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public boolean isPhoneNumberExists(String phoneNumber) {
        return customerDAO.isPhoneNumberExists(phoneNumber);
    }

    @Override
    public CustomerViewDTO getCustomerById(Integer id) {
        return customerDAO.getCustomerById(id);
    }

    @Override
    public List<CustomerViewDTO> filterCustomerNoPagination(String keyword) {
        return customerDAO.filterNoPagination(keyword);
    }

    @Override
    public PagedResult<CustomerViewDTO> filterCustomer(String keyword, int pageIndex, int pageSize) {
        return customerDAO.filter(keyword, pageIndex, pageSize);
    }

    @Override
    public PagedResult<CustomerViewDTO> getAllCustomersPagination(int pageIndex, int pageSize) {
        return customerDAO.getAllPagination(pageIndex, pageSize);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerDAO.delete(id);
    }

    @Override
    public void updateCustomer(CreateOrUpdateCustomerDTO dto) {
        customerDAO.update(dto);
    }

    @Override
    public void createCustomer(CreateOrUpdateCustomerDTO dto) {
        customerDAO.add(dto);
    }
}

