package com.weatherhub.app.controllers;

import com.weatherhub.app.models.NewServiceRequest;
import com.weatherhub.app.models.Place;
import com.weatherhub.app.models.User;
import com.weatherhub.app.requests.*;
import com.weatherhub.app.services.NewServiceRequestService;
import com.weatherhub.app.services.PlaceService;
import com.weatherhub.app.services.UserService;
import com.weatherhub.app.utils.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private NewServiceRequestService newServiceRequestService;
    @Autowired
    private UserService userService;
    @Autowired
    private PlaceService placeService;

    @PostMapping(value = "/user/add_places")
    public ResponseEntity<?> add_place(@RequestBody AddPlacesRequest addPlacesRequest) {
        User user;
        if (userService.findByEmail(addPlacesRequest.getEmail()) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String userToken = userService.findByEmail(addPlacesRequest.getEmail()).getToken();
        if (Encoder.compare(addPlacesRequest.getToken(), userToken))
            user = userService.findByEmail(addPlacesRequest.getEmail());
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        for (String coordinate : addPlacesRequest.getCoordinates()) {
            if (placeService.findByUseridAndCoordinate(user.getId(), coordinate) == null) {
                Integer id = userService.findByEmail(addPlacesRequest.getEmail()).getId();
                Place place = new Place(id, coordinate);
                placeService.create(place);
            }
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //Получение местоположений по пользователю
    //Проверка по токену!!
    @GetMapping(value = "/user/get_places")
    public ResponseEntity<?> getPlacesByUser(@RequestBody ShowPlacesRequest showPlacesRequest) {
        List<Place> places;
        User user;
        if (userService.findByEmail(showPlacesRequest.getEmail()) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String userToken = userService.findByEmail(showPlacesRequest.getEmail()).getToken();
        if (Encoder.compare(showPlacesRequest.getToken(), userToken))
            user = userService.findByEmail(showPlacesRequest.getEmail());
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(user != null) {
            places = placeService.findByUserid(user.getId());
            return places != null && !places.isEmpty()
                    ? new ResponseEntity<>(places, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //удаление местоположения по пользователю
    @PostMapping(value = "/users/delete_place")
    public ResponseEntity<?> delete(@RequestBody DeletePlaceRequest deletePlaceRequest) {

        User user;
        if (userService.findByEmail(deletePlaceRequest.getEmail()) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String userToken = userService.findByEmail(deletePlaceRequest.getEmail()).getToken();
        if (Encoder.compare(deletePlaceRequest.getToken(), userToken))
            user = userService.findByEmail(deletePlaceRequest.getEmail());
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String coordinate = deletePlaceRequest.getCoordinate();
        Place place = placeService.findByUseridAndCoordinate(user.getId(), coordinate);

        if (place == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return placeService.delete(place.getId())
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> read() {
        final List<User> users = userService.readAll();

        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/users/add_new_service_request")
    public ResponseEntity<?> addNewService(@RequestBody AddServiceRequest addServiceRequest) {

        newServiceRequestService.create(new NewServiceRequest(addServiceRequest.getServiceName()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/get_new_service_requests")
    public ResponseEntity<?> getNewService(@RequestBody AddServicesStatisticsRequest addServicesStatisticsRequest) {

        String adminToken = userService.findByEmail("va123ma@gmail.com").getToken();
        if (!Encoder.compare(addServicesStatisticsRequest.getToken(), adminToken))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Object[]> stats = newServiceRequestService.getStats();

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}