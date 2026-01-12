package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.OptionDTO;

public interface IOptionDAO extends IDAO<OptionDTO, Integer> {
    PagedResult<OptionDTO> getAll(int pageIndex, int pageSize);
}
