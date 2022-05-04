package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class MemberDTO {

    private UUID id;

    @NotBlank(message = "Please, provide a name.")
    private String name;

    private String facebookUrl;

    private String instagramUrl;

    private String linkedinUrl;

    private String image;

    private String description;
}