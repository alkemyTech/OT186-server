package com.alkemy.ong.dto;

import com.alkemy.ong.entity.News;
import com.alkemy.ong.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class CommentDTO {

    private UUID id;
    @NotBlank(message = "Please provide a body")
    private String body;
    @NotNull(message = "User must not be null")
    private User users;
    @NotNull(message = "News must not be null")
    private News news;
}
