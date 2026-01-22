package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;

public interface IDAO<TDTO extends CreateOrUpdateDTO<TKey>, TKey> {
    TKey add(TDTO entity);
    void update(TDTO entity);
    void delete(TKey id);

}
