package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.services.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.FailedLoginException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserAuthController {


    private final UserServiceImp userServiceImp;

    @Autowired
    UserAuthController(UserServiceImp userServiceImp){
        this.userServiceImp = userServiceImp;
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        try {
            User user = userServiceImp.login(loginRequestDto);
            return ResponseEntity.ok(user);
        } catch (FailedLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("false");
        }
    }

}
