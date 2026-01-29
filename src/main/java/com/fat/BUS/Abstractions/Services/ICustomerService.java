package com.fat.BUS.Abstractions.Services;
import java.util.List;

import com.fat.DTO.Customers.CustomerDTO;
import com.fat.Contract.Shared.PagedResult;


public interface ICustomerService {
    CustomerDTO getCustomerById(Integer id);
    List<CustomerDTO> getAllCustomers();
    PagedResult<CustomerDTO> getAllCustomersPagination(int pageIndex, int pageSize);
    PagedResult<CustomerDTO> filterCustomerByList(String keyword, int pageIndex, int pageSize); // Filter từ ArrayList
    void deleteCustomer(Integer id);
    void updateCustomer(CustomerDTO dto);
    void createCustomer(CustomerDTO dto);
}



