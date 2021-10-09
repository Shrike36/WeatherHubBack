package com.weatherhub.app.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на удаление из базы местоположения пользователя")
public class DeletePlaceRequest {
    @Schema(description = "Адрес пользователя")
    private String email;
    @Schema(description = "Авторизационный токен пользователя")
    private String token;
    @Schema(description = "Местоположение пользователя")
    private String coordinate;

    public DeletePlaceRequest() {
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

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }
}
