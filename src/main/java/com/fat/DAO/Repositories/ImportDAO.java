package com.fat.DAO.Repositories;

import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IImportDAO;
import com.fat.DTO.Imports.ImportDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ImportDAO implements IImportDAO {
    private static ImportDAO instance;

    private ImportDAO() {
    }

    public static ImportDAO getInstance() {
        if (instance == null) {
            instance = new ImportDAO();
        }
        return instance;
    }

    @Override
    public List<ImportDTO> getAll() {
        return List.of();
    }


    @Override
    public ImportDTO getById(Integer id) {
        return null;
    }

    @Override
    public Integer add(ImportDTO entity) {
        return null;
    }

    @Override
    public void update(ImportDTO entity) {
        // Nhớ khi update trạng thái thì tăng tồn kho của sản phẩm tương ứng
        // Nhớ thêm lịch sử nhập xuất kho của từng sản pẩm
        // Sử dung transaction để đảm bảo tính toàn vẹn dữ liệu
    }

    @Override
    public void delete(Integer id) {

    }
}

