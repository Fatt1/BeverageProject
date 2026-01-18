package com.fat.DTO.Abstractions;

public abstract class CreateOrUpdateDTO<TKey> {
    protected TKey id;
    public TKey getId() {
        return id;
    }

}
