package com.weatherhub.app.requests;

public class AddServiceRequest {
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
