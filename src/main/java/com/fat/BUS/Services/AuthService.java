package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IAuthService;
import com.fat.BUS.Abstractions.Services.IStaffService;
import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;

public class AuthService implements IAuthService {
    private static AuthService instance;
    private final IStaffService staffService = StaffService.getInstance();
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
        StaffDetailDTO staff = staffService.getStaffByUserName(username);
        // TODO: Implement login logic
        return null;
    }

    @Override
    public void logout(UserSessionDTO session) {

    }
}
