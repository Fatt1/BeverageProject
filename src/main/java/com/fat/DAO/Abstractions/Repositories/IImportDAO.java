package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Imports.ImportDTO;

public interface IImportDAO extends IDAO<ImportDTO> {
    void confirmImport(Integer importId);
}
