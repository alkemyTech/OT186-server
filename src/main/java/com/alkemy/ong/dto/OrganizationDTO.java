package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class OrganizationDTO {
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    private String address;

    private Integer phone;

    private String facebook;

    private String linkedin;

    private String instagram;

    private List<SlideDTOBasic> slides;

}
