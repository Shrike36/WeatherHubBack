package com.weatherhub.app.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на добавление сервиса")
public class AddServiceRequest {
    @Schema(description = "Название сервиса")
    private String serviceName;

    public AddServiceRequest() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
