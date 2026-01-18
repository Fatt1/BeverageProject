package com.fat.DTO.Auths;

public class UserSessionDTO {
    private Integer staffId;
    private String staffName;
    private String roleName;
    private Integer roleId;

    private static UserSessionDTO instance = null;
    // Singleton pattern

    private UserSessionDTO() {
    }

    public static UserSessionDTO getInstance() {
        if (instance == null) {
            instance = new UserSessionDTO();
        }
        return instance;
    }

    // 5. Hàm nạp dữ liệu (Gọi 1 lần duy nhất khi Login thành công)
    public void setSession(Integer staffId, String staffName, String roleName, Integer roleId) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.roleName = roleName;
        this.roleId = roleId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getStaffName() {
        return staffName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    // 6. Hàm đăng xuất (Xóa sạch dữ liệu)
    public void clear() {
         instance = null;
    }

}
