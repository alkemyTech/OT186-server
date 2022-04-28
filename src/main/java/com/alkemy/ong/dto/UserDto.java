package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String photo;
}