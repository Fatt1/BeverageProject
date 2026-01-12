package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.UserDTO;

public interface IAuthService {
    UserDTO authenticate(String username, String password);
}
