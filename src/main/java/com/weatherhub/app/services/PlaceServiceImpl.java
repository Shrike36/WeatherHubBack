package com.weatherhub.app.services;

import com.weatherhub.app.models.Place;
import com.weatherhub.app.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public void create(Place place) {
        placeRepository.save(place);
    }

    @Override
    public List<Place>  readAll() {
        return placeRepository.findAll();
    }

    @Override
    public Place read(int id) {
        return placeRepository.getOne(id);
    }

    @Override
    public boolean update(Place place, int id) {
        if (placeRepository.existsById(id)) {
            place.setId(id);
            placeRepository.save(place);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        if (placeRepository.existsById(id)) {
            placeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Place findByUseridAndCoordinate(Integer userId, String coordinate) {
        if(placeRepository.existsByUserIdAndCoordinate(userId, coordinate))
            return placeRepository.findByUserIdAndCoordinate(userId, coordinate);
        return null;
    }

    @Override
    public List<Place> findByUserid(Integer userId) {
        if(placeRepository.existsByUserId(userId))
            return placeRepository.findByUserId(userId);
        return null;
    }


}