package com.fat.DAO.Repositories;

import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IImportDAO;
import com.fat.DTO.Imports.CreateOrUpdateImportDTO;
import com.fat.DTO.Imports.ImportViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.time.LocalDateTime;

public class ImportDAO implements IImportDAO {
    @Override
    public PagedResult<ImportViewDTO> getAllPagination(int pageIndex, int pageSize, SortOrder sortOrder, ReceiptSort sortBy) {
        return null;
    }

    @Override
    public PagedResult<ImportViewDTO> filter(String keyword, LocalDateTime from, LocalDateTime to,
                                             ImportStatus status, Integer staffId, Integer supplierId,
                                             int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public ReceptDetailDTO getById(Integer id) {
        return null;
    }

    @Override
    public void add(CreateOrUpdateImportDTO entity) {

    }

    @Override
    public void update(CreateOrUpdateImportDTO entity) {
        // Nhớ khi update trạng thái thì tăng tồn kho của sản phẩm tương ứng
        // Nhớ thêm lịch sử nhập xuất kho của từng sản pẩm
        // Sử dung transaction để đảm bảo tính toàn vẹn dữ liệu
    }

    @Override
    public void delete(Integer id) {

    }
}

