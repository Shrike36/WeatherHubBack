package com.weatherhub.app.services;

import com.weatherhub.app.models.Place;

import java.util.List;

public interface PlaceService {

    void create(Place place);

    List<Place> readAll();
    
    Place read(int id);

    boolean update(Place place, int id);
    
    boolean delete(int id);

    Place findByUseridAndCoordinate(Integer userId, String coordinate);

    List<Place> findByUserid(Integer userId);
}
