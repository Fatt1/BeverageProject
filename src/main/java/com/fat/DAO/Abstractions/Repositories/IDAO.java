package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;

public interface IDAO<TDTO extends CreateOrUpdateDTO<TKey>, TKey> {
    void add(TDTO entity);
    void update(TDTO entity);
    void delete(TKey id);

}
