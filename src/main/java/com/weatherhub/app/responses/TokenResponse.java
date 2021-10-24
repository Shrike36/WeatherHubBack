package com.weatherhub.app.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с токеном")
public class TokenResponse {
    @Schema(description = "Токен")
    String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
