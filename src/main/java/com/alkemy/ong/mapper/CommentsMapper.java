package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.CommentDTO;
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

    public CommentBasicDTO entity2CommentBasicDTO(Comments comments) {
        CommentBasicDTO commentBasicDTO = new CommentBasicDTO();
        commentBasicDTO.setId(comments.getId());
        commentBasicDTO.setBody(comments.getBody());
        commentBasicDTO.setNewsId(comments.getNews().getId());
        commentBasicDTO.setNewsName(comments.getNews().getName());
        commentBasicDTO.setUserId(comments.getUser().getId());
        commentBasicDTO.setFirstName(comments.getUser().getFirstName());
        commentBasicDTO.setLastName(comments.getUser().getLastName());
        commentBasicDTO.setEmail(comments.getUser().getEmail());
        commentBasicDTO.setRole(comments.getUser().getRoles().getName());
        return commentBasicDTO;
    }
    public List<CommentBasicDTO> listComment2listCommentDTO(List<Comments> listComment){
        List<CommentBasicDTO> listCommentDTO = new ArrayList<>();
        for (Comments comments: listComment ) {
            listCommentDTO.add(this.entity2CommentBasicDTO(comments));
        }
        return listCommentDTO;
    }
}
