package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class CategoriesDTO {

    private UUID id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    private String description;
    private String image;

}
