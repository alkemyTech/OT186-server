package com.alkemy.ong.controller;

import com.alkemy.ong.dto.LoginRequestDto;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.exception.EmailAlreadyExistException;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.FailedLoginException;
import javax.validation.Valid;
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    public UserRepository userRepository;
    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity post(@RequestBody User user){
        try {
            user = userService.save(user);
            return ResponseEntity.ok(user);
        } catch (EmailAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Email Alredy Exists, the result is: " + false);
        }
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
