package com.weatherhub.app.repositories;

import com.weatherhub.app.models.Place;
import com.weatherhub.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

    boolean existsByUserId(Integer userId);
    List<Place> findByUserId(Integer userId);
    boolean existsByUserIdAndCoordinate(Integer userId, String coordinate);
    Place findByUserIdAndCoordinate(Integer userId, String coordinate);
}
