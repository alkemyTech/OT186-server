package com.alkemy.ong.dto;

import com.alkemy.ong.entity.Categories;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class NewsDTO {

    private UUID id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotBlank(message = "Content must not be blank")
    private String content;
    @NotBlank(message = "Image must not be blank")
    private String image;
    private Categories categories;

}
