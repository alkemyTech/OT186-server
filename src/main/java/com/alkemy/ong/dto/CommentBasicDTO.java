package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;
@Getter
@Setter
public class CommentBasicDTO {
    private UUID id;
    @NotBlank(message = "Please provide a body")
    private String body;
    private UUID newsId;
    private String newsName;
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

}
