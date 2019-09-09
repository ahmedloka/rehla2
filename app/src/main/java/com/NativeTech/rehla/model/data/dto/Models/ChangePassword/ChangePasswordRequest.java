package com.NativeTech.rehla.model.data.dto.Models.ChangePassword;

public class ChangePasswordRequest {
    private String NewPassword;
    private String OldPassword;

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }
}
