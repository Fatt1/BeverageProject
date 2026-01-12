package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.UnitDTO;

import java.util.List;

public interface IUnitService {
    void addUnit(UnitDTO unit);
    void updateUnit(UnitDTO unit);
    void deleteUnit(int unitId);
    List<UnitDTO> getAllUnits();
}
