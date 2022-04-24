package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;


@Getter
@Setter
public class CategoriesDTO {

    private UUID id;
    private String name;
    private String description;
    private String image;

}
