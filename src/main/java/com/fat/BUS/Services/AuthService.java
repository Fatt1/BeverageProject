package com.fat.BUS.Services;

import org.mindrot.jbcrypt.BCrypt;

import com.fat.BUS.Abstractions.Services.IAuthService;
import com.fat.BUS.Abstractions.Services.IRoleService;
import com.fat.BUS.Abstractions.Services.IStaffService;
import com.fat.DAO.Repositories.StaffDAO;
import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.StaffDTO;
import com.fat.GUI.MainForm;
import com.fat.GUI.Dialogs.ConfirmDialog.ConfirmDialog;
import com.fat.GUI.Forms.LoginForm;

public class AuthService implements IAuthService {
    private static AuthService instance;
    private final IStaffService staffService = StaffService.getInstance();
    private final IRoleService roleService = RoleService.getInstance();
    private AuthService() {
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    @Override
    public UserSessionDTO login(LoginForm lg,String username, String password) {
        StaffDTO staff = StaffService.getInstance().getStaffByUserName(username);
        UserSessionDTO.getInstance().setSession(staff.getId(), staff.getUserName(), roleService.getRoleById(staff.getRoleId()).getName(), staff.getRoleId());
        return null;
    }

    @Override
    public void logout(UserSessionDTO session, MainForm mainform) {
        boolean result = ConfirmDialog.show(mainform, "Đăng xuất", "Bạn có muốn đăng xuất không?", "Xác Nhận");
        if (result) {
            mainform.dispose();
            UserSessionDTO.getInstance().clear();
            new LoginForm().setVisible(true);
        }
        else{
            mainform.getCardLayout().show(mainform.getMainContentPanel(), mainform.toString());
        }
    }
}
