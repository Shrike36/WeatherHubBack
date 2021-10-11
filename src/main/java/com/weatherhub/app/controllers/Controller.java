package com.weatherhub.app.controllers;

import com.weatherhub.app.models.NewServiceRequest;
import com.weatherhub.app.models.Place;
import com.weatherhub.app.models.User;
import com.weatherhub.app.requests.*;
import com.weatherhub.app.services.NewServiceRequestService;
import com.weatherhub.app.services.PlaceService;
import com.weatherhub.app.services.UserService;
import com.weatherhub.app.utils.Encoder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="Основной контроллер", description="Действия над контентом")
public class Controller {

    @Autowired
    private NewServiceRequestService newServiceRequestService;
    @Autowired
    private UserService userService;
    @Autowired
    private PlaceService placeService;

    @Operation(
            summary = "Сохранение местоположений",
            description = "Позволяет сохранить в базу список местоположений пользователя"
    )
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
    @Operation(
            summary = "Получение местоположений",
            description = "Позволяет получить из базы список местоположений пользователя"
    )
    @GetMapping(value = "/user/get_places")
    public ResponseEntity<List<Place>> getPlacesByUser(@RequestBody GetPlacesRequest getPlacesRequest) {
        List<Place> places;
        User user;
        if (userService.findByEmail(getPlacesRequest.getEmail()) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String userToken = userService.findByEmail(getPlacesRequest.getEmail()).getToken();
        if (Encoder.compare(getPlacesRequest.getToken(), userToken))
            user = userService.findByEmail(getPlacesRequest.getEmail());
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
    @Operation(
            summary = "Удаление местоположения",
            description = "Позволяет удалить из базы местоположение пользователя"
    )
    @DeleteMapping(value = "/users/delete_place")
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

    @Operation(
            summary = "Получение списка пользователей",
            description = "Позволяет получить список зарегистрированных пользователей"
    )
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> read() {
        final List<User> users = userService.readAll();

        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Заявка на добавление сервиса",
            description = "Позволяет сохранить в базу заявку на добавление сервиса"
    )
    @PostMapping(value = "/users/add_new_service_request")
    public ResponseEntity<?> addNewService(@RequestBody AddServiceRequest addServiceRequest) {

        newServiceRequestService.create(new NewServiceRequest(addServiceRequest.getServiceName()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Получение списка заявок на добавление сервисов",
            description = "Позволяет посмотреть список заявок на добавление сервисов"
    )
    @GetMapping(value = "/get_new_service_requests")
    public ResponseEntity<List<Object[]>> getNewService(@RequestBody AddServicesStatisticsRequest addServicesStatisticsRequest) {

        String adminToken = userService.findByEmail("va123ma@gmail.com").getToken();
        if (!Encoder.compare(addServicesStatisticsRequest.getToken(), adminToken))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        List<Object[]> stats = newServiceRequestService.getStats();

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}