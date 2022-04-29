package com.alkemy.ong.dto;

import com.alkemy.ong.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class UserDTO {

    private  UUID id;
    private Role roles;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String password;
    private  String photo;
}
