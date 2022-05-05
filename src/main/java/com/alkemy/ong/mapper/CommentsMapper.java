package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.entity.Comments;
import org.springframework.stereotype.Component;

@Component
public class CommentsMapper {

    public CommentDTO comment2DTO(Comments entity){
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setBody(entity.getBody());
        dto.setUsers(entity.getUser());
        dto.setNews(entity.getNews());
        return dto;
    }

    public Comments updateComment2DTO(Comments entity, CommentDTO dto){
        entity.setId(dto.getId());
        entity.setBody(dto.getBody());
        entity.setUser(dto.getUsers());
        entity.setNews(dto.getNews());
        return entity;
    }
}
