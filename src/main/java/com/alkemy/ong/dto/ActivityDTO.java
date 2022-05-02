package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class ActivityDTO {

    private UUID id;
    @NotBlank(message = "Please provide a name")
    private String name;
    @NotBlank(message = "Please provide a content")
    private String content;
    private String image;
}
