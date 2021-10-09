package com.weatherhub.app.services;


import com.weatherhub.app.models.NewServiceRequest;

import java.util.List;

public interface NewServiceRequestService {
    void create(NewServiceRequest newServiceRequest);

    List<NewServiceRequest> readAll();

    NewServiceRequest read(int id);

    boolean update(NewServiceRequest newServiceRequest, int id);

    boolean delete(int id);

    List<Object[]> getStats();
}
