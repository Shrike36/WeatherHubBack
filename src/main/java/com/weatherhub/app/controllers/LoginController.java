package com.weatherhub.app.controllers;

import com.weatherhub.app.models.Place;
import com.weatherhub.app.models.User;
import com.weatherhub.app.requests.RegisterRequest;
import com.weatherhub.app.requests.ResetPassword;
import com.weatherhub.app.requests.ResetPasswordRequest;
import com.weatherhub.app.services.PlaceService;
import com.weatherhub.app.services.UserService;
import com.weatherhub.app.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name="Контроллер логина", description="Авторизация, регистрация, смена пароля")
public class LoginController {

    @Autowired
    private UserService userService;

    private ResetPasswordRequestsService resetPasswordRequestsService = new ResetPasswordRequestsService();

    @PostMapping(value = "/register")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {

        String email = registerRequest.getEmail();
        String hashedPassword = Encoder.encode(registerRequest.getPassword());

        String token = TokenGenerator.generateToken();
        String hashedToken = Encoder.encode(token);

        User user = new User(email, hashedPassword, hashedToken);

        if (userService.findByEmail(email) == null) {
            userService.create(user);
            User test = userService.findByEmail(email);
            return test != null
                    ? new ResponseEntity<>(token, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Авторизация пользователя",
            description = "Позволяет авторизировать пользователя"
    )
    @PostMapping(value = "/auth")
    public ResponseEntity<?> auth(@RequestBody RegisterRequest authRequest) {

        String email = authRequest.getEmail();
        String hashedPassword = Encoder.encode(authRequest.getPassword());

        String token = TokenGenerator.generateToken();
        String hashedToken = Encoder.encode(token);

        User user = userService.findByEmail(email);

        if(user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Integer userId = user.getId();
        if(Encoder.compare(authRequest.getPassword(), user.getPassword())){
            user = new User(email, hashedPassword, hashedToken);
            return userService.update(user, userId)
                    ? new ResponseEntity<>(token, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Запрос на смену пароля",
            description = "Формирует заявку на смену пароля"
    )
    @PostMapping(value = "/reset_password_request")
    public ResponseEntity<?> resetPasswordRequest(@RequestBody ResetPasswordRequest resetPasswordRequest) {

        String email = resetPasswordRequest.getEmail();
        if(userService.findByEmail(email) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        int code = CodeGenerator.generateCode();

        if(resetPasswordRequestsService.getRequests().containsKey(email))
            resetPasswordRequestsService.getRequests().replace(email, code);
        else
            resetPasswordRequestsService.getRequests().put(email, code);

        Integer test = resetPasswordRequestsService.getRequests().get(email);

        try {
            EmailSender.sendEmail(email, String.valueOf(code));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return test != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(
            summary = "Смена пароля",
            description = "Изменяет пароль пользователя"
    )
    @PostMapping(value = "/reset_password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPassword resetPassword) {

        if(!resetPasswordRequestsService.getRequests().containsValue(Integer.valueOf(resetPassword.getCode())))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Map<String,Integer> map = resetPasswordRequestsService.getRequests();
        Set<Map.Entry<String,Integer>> entrySet = map.entrySet();
        String email = null;

        Integer desiredObject = Integer.valueOf(resetPassword.getCode());
        for (Map.Entry<String,Integer> pair : entrySet) {
            if (desiredObject.equals(pair.getValue())) {
                email = pair.getKey();
            }
        }

        resetPasswordRequestsService.getRequests().remove(email);

        String hashedPassword = Encoder.encode(resetPassword.getPassword());

        String token = TokenGenerator.generateToken();
        String hashedToken = Encoder.encode(token);

        User user = userService.findByEmail(email);

        if(user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Integer userId = user.getId();
        user = new User(email, hashedPassword, hashedToken);
        return userService.update(user, userId)
                ? new ResponseEntity<>(token, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}