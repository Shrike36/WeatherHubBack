package com.weatherhub.app.requests;

public class ResetPassword {
    private String newPassword;
    private String code;

    public ResetPassword() {
    }

    public String getPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
