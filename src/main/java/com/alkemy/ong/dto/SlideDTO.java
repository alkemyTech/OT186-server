package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SlideDTO {
    private UUID id;
    private String imageUrl;
    private String text;
    private Long order;
    private UUID organizationId;
}
