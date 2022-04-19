package com.alkemy.ong.controllers;

import com.alkemy.ong.entities.LoginRequestDto;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.FailedLoginException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        try {
            User user = userService.login(loginRequestDto);
            return ResponseEntity.ok(user);
        } catch (FailedLoginException e) {
            return ResponseEntity.ok(false);
        }
    }


}
