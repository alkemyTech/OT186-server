package com.alkemy.ong.controller;

import com.alkemy.ong.services.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserServiceImp userServiceImp;

    @Autowired
    UserController(UserServiceImp userServiceImp){
        this.userServiceImp = userServiceImp;
    }



}
