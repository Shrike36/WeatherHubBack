package com.weatherhub.app.requests;

public class ResetPasswordRequest {
    private String email;

    public ResetPasswordRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
