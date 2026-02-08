package com.fat.BUS.Abstractions.Services;
import java.util.List;

import com.fat.DTO.Customers.CustomerDTO;
import com.fat.Contract.Shared.PagedResult;


public interface ICustomerService {
    CustomerDTO getCustomerById(Integer id);
    List<CustomerDTO> getAllCustomers();
    List<CustomerDTO> filterCustomerByList(String keyword); // Filter từ ArrayList
    void deleteCustomer(Integer id);
    void updateCustomer(CustomerDTO dto);
    void createCustomer(CustomerDTO dto);
}



