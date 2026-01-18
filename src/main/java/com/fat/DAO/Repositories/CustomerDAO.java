package com.fat.DAO.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.ICustomerDAO;
import com.fat.DTO.Customers.CreateOrUpdateCustomerDTO;
import com.fat.DTO.Customers.CustomerViewDTO;

import java.util.List;

public class CustomerDAO implements ICustomerDAO {
    @Override
    public List<CustomerViewDTO> filterNoPagination(String keyword) {
        return List.of();
    }

    @Override
    public PagedResult<CustomerViewDTO> getAllPagination(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public PagedResult<CustomerViewDTO> filter(String keyword, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public CustomerViewDTO getCustomerById(Integer id) {
        return null;
    }

    @Override
    public boolean isPhoneNumberExists(String phoneNumber) {
        return false;
    }

    @Override
    public void add(CreateOrUpdateCustomerDTO entity) {

    }

    @Override
    public void update(CreateOrUpdateCustomerDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }


}
