package com.alkemy.ong.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PageFormatter<T> {

    @ApiModelProperty(position = 1)
    private List<T> pageContent;

    @ApiModelProperty(position = 2, value = "api/entity?page=2")
    private String previousPageUrl;

    @ApiModelProperty(position = 1, value = "api/entity?page=4")
    private String nextPageUrl;
}
