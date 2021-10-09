package com.weatherhub.app.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на получение списка запрашиваемых сервисов")
public class AddServicesStatisticsRequest {
    @Schema(description = "Авторизационный токен администратора")
    private String token;

    public AddServicesStatisticsRequest() {    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
