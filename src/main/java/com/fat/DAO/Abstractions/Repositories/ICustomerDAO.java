package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Customers.CreateOrUpdateCustomerDTO;

import com.fat.DTO.Customers.CustomerViewDTO;

import java.util.List;

public interface ICustomerDAO extends IDAO<CreateOrUpdateCustomerDTO, Integer> {
    List<CustomerViewDTO> filterNoPagination(String keyword);
    PagedResult<CustomerViewDTO> getAllPagination(int pageIndex, int pageSize);
    PagedResult<CustomerViewDTO> filter(String keyword, int pageIndex, int pageSize);
    CustomerViewDTO getCustomerById(Integer id);
    boolean isPhoneNumberExists(String phoneNumber);
}
