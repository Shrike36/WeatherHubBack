package com.weatherhub.app.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на получение списка местоположений пользователя")
public class GetPlacesRequest {
    @Schema(description = "Адрес пользователя")
    private String email;
    @Schema(description = "Авторизационный пользователя")
    private String token;

    public GetPlacesRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
