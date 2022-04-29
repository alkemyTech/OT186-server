package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.auth.utils.JwtUtils;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    UserAuthController(UserService userService, JwtUtils jwtUtils){
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public User post(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequestDto loginRequestDto){

        UserDetails userDetails;
        try {
            userDetails = userService.login(loginRequestDto);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse("false"));
        }

        final String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
