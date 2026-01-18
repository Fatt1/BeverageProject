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

public class ReceiptDAO implements IReceiptDAO {
    @Override
    public PagedResult<ReceiptViewDTO> getAllPagination(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public PagedResult<ReceiptViewDTO> filter(String keyword, LocalDateTime from, LocalDateTime to,
                                              Integer staffId, BigDecimal totalAmount,
                                              int pageIndex, int pageSize, SortOrder sortOrder, ReceiptSort sortBy) {
        return null;
    }

    @Override
    public ReceptDetailDTO getById(Integer id) {
        return null;
    }

    @Override
    public void add(CreateOrUpdateReceiptDTO entity) {
        // TODO Auto-generated method stub
        // Nhớ thêm đơn hàng thì trừ tồn kho của sản phẩm tương ứng
        // Nhớ thêm lịch sử nhập xuất kho của từng sản pẩm
        // Sử dung transaction để đảm bảo tính toàn vẹn dữ liệu
    }

    @Override
    public void update(CreateOrUpdateReceiptDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}

