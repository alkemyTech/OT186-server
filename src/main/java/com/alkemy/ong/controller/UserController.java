package com.alkemy.ong.controller;

import com.alkemy.ong.dto.LoginRequestDto;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.FailedLoginException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public User post(@RequestBody User user) {
        return userRepository.save(user);
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
