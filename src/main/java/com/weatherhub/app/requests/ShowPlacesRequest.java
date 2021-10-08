package com.weatherhub.app.requests;

public class ShowPlacesRequest {
    private String email;
    private String token;

    public ShowPlacesRequest() {
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
