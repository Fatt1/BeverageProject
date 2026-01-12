package com.fat.DTO.Abstractions;

import java.time.OffsetDateTime;

public abstract class BaseEntity<TKey> {
    protected TKey id;
    public BaseEntity(TKey id) {
        this.id = id;
    }

    public TKey getId() {
        return id;
    }

    public void setId(TKey id) {
        this.id = id;
    }
}
