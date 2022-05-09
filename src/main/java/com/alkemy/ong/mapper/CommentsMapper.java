package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.dto.CommentsBasicDTO;
import com.alkemy.ong.entity.Comments;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public Comments DTO2Entity(CommentDTO dto) {
        Comments entity = new Comments();
        entity.setBody(dto.getBody());
        entity.setNews(dto.getNews());
        entity.setUser(dto.getUsers());
        return entity;
    }

    public List<CommentsBasicDTO> entityList2DTOList(List<Comments> entities){
        List<CommentsBasicDTO> dtos = new ArrayList<>();
        for (Comments entity : entities) {
            CommentsBasicDTO dto = new CommentsBasicDTO();
            dto.setBody(entity.getBody());
            dtos.add(dto);
        }
        return dtos;
    }
}
