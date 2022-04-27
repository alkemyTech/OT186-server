package com.alkemy.ong.dto;

import com.alkemy.ong.entity.Categories;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NewsDTO {

    private UUID id;
    private String name;
    private String image;
    private Categories categories;

}