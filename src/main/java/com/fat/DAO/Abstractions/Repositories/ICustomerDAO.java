package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Customers.CustomerDTO;

public interface ICustomerDAO extends IDAO<CustomerDTO> {
    boolean isHasTransaction(Integer customerId);
}
