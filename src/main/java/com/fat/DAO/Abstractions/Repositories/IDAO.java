package com.fat.DAO.Abstractions.Repositories;

import java.util.List;

public interface IDAO<T> {
    Integer add(T entity);
    void update(T entity);
    void delete(Integer id);
    T getById(Integer id);
    List<T> getAll();
}
