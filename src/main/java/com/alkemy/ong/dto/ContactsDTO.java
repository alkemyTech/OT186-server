package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ContactsDTO {
    private UUID id;

    private String name;

    private Integer phone;

    private String email;

    private String message;

}
