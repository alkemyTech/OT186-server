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

    @ApiModelProperty(position = 1, value = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @ApiModelProperty(position = 2, required = true, value = "Campañas")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @ApiModelProperty(position = 3, required = true, value = "Difundir el comienzo o progreso de las campañas.")
    private String description;

    @ApiModelProperty(position = 3, required = true, value = "image.jpg")
    private String image;

}
