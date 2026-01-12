package com.fat.DTO.Abstractions;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

public abstract class AuditableBaseEntity<TKey> extends BaseEntity<TKey> {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public AuditableBaseEntity(TKey id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
