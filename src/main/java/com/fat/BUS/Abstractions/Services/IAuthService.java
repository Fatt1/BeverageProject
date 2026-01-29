package com.fat.BUS.Abstractions.Services;

import com.fat.GUI.MainForm;
import com.fat.GUI.Forms.LoginForm;
import com.fat.DTO.Auths.UserSessionDTO;

public interface IAuthService {
    UserSessionDTO login(LoginForm lg,String username, String password);
    void logout(UserSessionDTO session, MainForm mainform);
    boolean isDetectdStaff(String username, String password);
}
