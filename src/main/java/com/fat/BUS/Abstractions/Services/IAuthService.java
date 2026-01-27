package com.fat.BUS.Abstractions.Services;

import com.fat.GUI.MainForm;
import com.fat.DTO.Auths.UserSessionDTO;

public interface IAuthService {
    UserSessionDTO login(String username, String password);
    void logout(UserSessionDTO session, MainForm mainform);
}
