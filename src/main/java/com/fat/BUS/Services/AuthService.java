package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IAuthService;
import com.fat.DTO.Auths.UserSessionDTO;

public class AuthService implements IAuthService {
    private static AuthService instance;

    private AuthService() {
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    @Override
    public UserSessionDTO login(String username, String password) {
        return null;
    }

    @Override
    public void logout(UserSessionDTO session) {

    }
}
