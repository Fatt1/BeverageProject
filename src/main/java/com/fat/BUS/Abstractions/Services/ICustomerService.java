package com.fat.BUS.Abstractions.Services;
import java.util.List;

import com.fat.DTO.Customers.CustomerViewDTO;
import com.fat.DTO.Customers.CreateOrUpdateCustomerDTO;
import com.fat.Contract.Shared.PagedResult;


public interface ICustomerService {
    CustomerViewDTO getCustomerById(Integer id);
    List<CustomerViewDTO> getAllCustomers();
    PagedResult<CustomerViewDTO> getAllCustomersPagination(int pageIndex, int pageSize);
    PagedResult<CustomerViewDTO> filterCustomerByList(String keyword, int pageIndex, int pageSize); // Filter từ ArrayList
    void deleteCustomer(Integer id);
    void updateCustomer(CreateOrUpdateCustomerDTO dto);
    void createCustomer(CreateOrUpdateCustomerDTO dto);
    void refreshCache();
}



