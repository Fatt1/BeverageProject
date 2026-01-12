package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.UnitDTO;

import java.util.List;


public interface IUnitDAO extends IDAO<UnitDTO, Integer> {
    List<UnitDTO> getAll();
}
