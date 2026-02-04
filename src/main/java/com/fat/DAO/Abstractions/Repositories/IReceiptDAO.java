package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Receipts.ReceiptDTO;

import java.util.List;

public interface IReceiptDAO extends IDAO<ReceiptDTO> {
    List<ReceiptDTO> getAll();
    ReceiptDTO getById(Integer id);
}
