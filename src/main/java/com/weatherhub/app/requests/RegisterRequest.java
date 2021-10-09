package com.weatherhub.app.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на регистрацию пользователя")
public class RegisterRequest{
    @Schema(description = "Адрес пользователя")
    private String email;
    @Schema(description = "Пароль пользователя")
    private String password;

    public RegisterRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}