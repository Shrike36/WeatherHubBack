package com.weatherhub.app.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос со списком местоположений пользователя")
public class AddPlacesRequest {
    @Schema(description = "Адрес пользователя")
    private String email;
    @Schema(description = "Авторизационный токен пользователя")
    private String token;
    @Schema(description = "Список местоположений")
    private String[] coordinates;

    public AddPlacesRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
