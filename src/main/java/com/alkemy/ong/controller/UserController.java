package com.alkemy.ong.controller;

<<<<<<< HEAD
import com.alkemy.ong.dto.LoginRequestDto;
import com.alkemy.ong.entity.User;
=======
import com.alkemy.ong.dto.UserDTO;
>>>>>>> origin/main
import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.FailedLoginException;
import javax.validation.Valid;

=======
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

>>>>>>> origin/main
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

<<<<<<< HEAD

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDto loginRequestDto){
=======
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok().body(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
>>>>>>> origin/main
        try {
            userService.delete(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
