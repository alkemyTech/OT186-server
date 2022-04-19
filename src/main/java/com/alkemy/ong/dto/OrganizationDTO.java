package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class OrganizationDTO {
    private UUID id;

    private String name;

    private String image;

    private String address;

    private Integer phone;

}
