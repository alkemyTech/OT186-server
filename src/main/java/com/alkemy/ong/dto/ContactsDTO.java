package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
public class ContactsDTO {
    private UUID id;

    @NotBlank(message = "Please provide a name")
    private String name;

    private Integer phone;

    @NotBlank(message = "Please provide a email")
    private String email;

    private String message;

}