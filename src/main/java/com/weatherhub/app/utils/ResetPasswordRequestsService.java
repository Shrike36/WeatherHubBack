package com.weatherhub.app.utils;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordRequestsService {
    private Map<String, Integer> requests = new HashMap<>();

    public Map<String, Integer> getRequests() {
        return requests;
    }

    public void setRequests(Map<String, Integer> requests) {
        this.requests = requests;
    }
}
