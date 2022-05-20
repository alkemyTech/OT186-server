package com.alkemy.ong.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@ApiModel
public class CategoriesDTO {

    private UUID id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    private String description;

    private String image;

}
