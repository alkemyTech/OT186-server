package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok().body(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id, HttpServletRequest req) {
        if(userService.validateRole(id, req)){
            try {
                userService.delete(id);
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable UUID id, @RequestBody UserDTO userDTO, HttpServletRequest req) {
        if(userService.validateRole(id, req)){
            try {
                return ResponseEntity.ok(userService.update(id, userDTO));
            } catch (ParamNotFound e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
