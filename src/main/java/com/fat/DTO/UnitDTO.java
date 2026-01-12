package com.fat.DTO;

import com.fat.DTO.Abstractions.BaseEntity;

public class UnitDTO extends BaseEntity<Integer> {
    private String name;

    public UnitDTO () {
        super(null);
    }

    public UnitDTO(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
