package com.fat.Contract.Exceptions.Roles;

import com.fat.Contract.Exceptions.BadRequestException;

public class DeleteAdminRoleException extends BadRequestException {
    public DeleteAdminRoleException() {
        super("Không thể xóa vai trò Admin");
    }
}
