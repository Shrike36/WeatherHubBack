package com.weatherhub.app.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "запрос с новым паролем")
public class ResetPassword {
    @Schema(description = "Новый пароль")
    private String password;
    @Schema(description = "Код подтверждения")
    private String code;

    public ResetPassword() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
