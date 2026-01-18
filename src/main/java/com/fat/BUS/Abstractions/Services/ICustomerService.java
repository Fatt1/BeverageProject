package com.fat.BUS.Abstractions.Services;
import java.util.List;

import com.fat.DTO.Customers.CustomerViewDTO;
import com.fat.DTO.Customers.CreateOrUpdateCustomerDTO;
import com.fat.Contract.Shared.PagedResult;


public interface ICustomerService {
    boolean isPhoneNumberExists(String phoneNumber);
    CustomerViewDTO getCustomerById(Integer id);
    List<CustomerViewDTO> filterCustomerNoPagination(String keyword);
    PagedResult<CustomerViewDTO> filterCustomer(String keyword, int pageIndex, int pageSize);
    PagedResult<CustomerViewDTO> getAllCustomersPagination(int pageIndex, int pageSize);
    void deleteCustomer(Integer id);
    void updateCustomer(CreateOrUpdateCustomerDTO dto);
    void createCustomer(CreateOrUpdateCustomerDTO dto);
}



