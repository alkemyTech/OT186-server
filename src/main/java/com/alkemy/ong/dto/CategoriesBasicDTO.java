package com.alkemy.ong.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CategoriesBasicDTO {

    @ApiModelProperty(position = 1, required = true, value = "Campañas")
    private String name;

}
