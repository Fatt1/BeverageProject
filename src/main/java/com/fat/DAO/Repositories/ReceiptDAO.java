package com.fat.DAO.Repositories;

import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IReceiptDAO;
import com.fat.DTO.Receipts.CreateOrUpdateReceiptDTO;
import com.fat.DTO.Receipts.ReceiptViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReceiptDAO implements IReceiptDAO {
    private static ReceiptDAO instance;

    private ReceiptDAO() {
    }

    public static ReceiptDAO getInstance() {
        if (instance == null) {
            instance = new ReceiptDAO();
        }
        return instance;
    }

    @Override
    public List<ReceiptViewDTO> getAll() {
        return List.of();
    }


    @Override
    public ReceptDetailDTO getById(Integer id) {
        return null;
    }

    @Override
    public Integer add(CreateOrUpdateReceiptDTO entity) {
        // TODO Auto-generated method stub
        // Nhớ thêm đơn hàng thì trừ tồn kho của sản phẩm tương ứng
        // Nhớ thêm lịch sử nhập xuất kho của từng sản pẩm
        // Sử dung transaction để đảm bảo tính toàn vẹn dữ liệu
        return null;
    }

    @Override
    public void update(CreateOrUpdateReceiptDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}

