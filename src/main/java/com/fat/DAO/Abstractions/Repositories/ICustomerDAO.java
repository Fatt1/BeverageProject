package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Customers.CreateOrUpdateCustomerDTO;

import com.fat.DTO.Customers.CustomerViewDTO;

import java.util.List;

public interface ICustomerDAO extends IDAO<CreateOrUpdateCustomerDTO, Integer> {
    List<CustomerViewDTO> getAll();
    CustomerViewDTO getById(Integer id);
}
