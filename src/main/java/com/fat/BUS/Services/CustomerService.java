package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.ICustomerService;
import com.fat.BUS.Utils.ValidatorUtil;
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
        customersCache = this.customerDAO.getAll();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    @Override
    public CustomerDTO getCustomerById(Integer id) {
        if (id == null) return null;
        // Đảm bảo cache đã được populate
        if (customersCache.isEmpty()) {
            getAllCustomers();
        }
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
    public List<CustomerDTO> filterCustomerByList(String keyword) {
        if(!keyword.isEmpty() && keyword != null) {
            String lowerKeyword = keyword.toLowerCase().trim();
            return customersCache.stream()
                    .filter(cus -> cus.getFirstName().toLowerCase().contains(lowerKeyword) ||
                            cus.getLastName().toLowerCase().contains(lowerKeyword) ||
                            cus.getPhoneNumber().toLowerCase().contains(lowerKeyword)).toList();
        }
        return customersCache;
    }

    @Override
    public void deleteCustomer(Integer id) {
        boolean isHasTransaction = customerDAO.isHasTransaction(id);
        if(isHasTransaction) {
            throw new RuntimeException("Không thể xóa khách hàng này vì đã có giao dịch.");
        }
        customerDAO.delete(id);
        customersCache.removeIf(c -> c.getId().equals(id));
    }

    @Override
    public void updateCustomer(CustomerDTO dto) {
        ValidatorUtil.validate(dto);
        boolean isExistPhoneNumber = customersCache.stream()
                .anyMatch(cus -> !cus.getId().equals(dto.getId()) && cus.getPhoneNumber().equals(dto.getPhoneNumber()));
        if(isExistPhoneNumber) {
            throw new RuntimeException("Số điện thoại đã tồn tại.");
        }
        customerDAO.update(dto);
        for (int i = 0; i < customersCache.size(); i++) {
            if (customersCache.get(i).getId().equals(dto.getId())) {
                customersCache.set(i, dto);
                break;
            }
        }
    }

    @Override
    public void createCustomer(CustomerDTO dto) {
        ValidatorUtil.validate(dto);
        boolean isExistPhoneNumber = customersCache.stream()
                        .anyMatch(cus -> cus.getPhoneNumber().equals(dto.getPhoneNumber()));
        if(isExistPhoneNumber) {
            throw new RuntimeException("Số điện thoại đã tồn tại.");
        }
        dto.setCreatedAt(LocalDateTime.now());
        Integer newId = customerDAO.add(dto);
        dto.setId(newId);
        customersCache.addFirst(dto);
    }
}

