package com.alkemy.ong.dto;

import com.alkemy.ong.entity.News;
import com.alkemy.ong.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class CommentDTO {

    private UUID id;
    @NotBlank(message = "Please provide a name")
    private String body;
    private User users;
    private News news;
}
