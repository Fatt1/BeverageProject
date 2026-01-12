package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Abstractions.BaseEntity;

public interface IDAO<TEntity extends BaseEntity<TKey>, TKey> {
    TEntity getById(TKey id);
    void add(TEntity entity);
    void update(TEntity entity);
    void delete(TKey id);

}
