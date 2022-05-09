package com.alkemy.ong.services;

import com.alkemy.ong.dto.CommentDTO;

import java.util.UUID;

public interface CommentService {

    CommentDTO update(UUID id, CommentDTO commentDTO);
    void delete(UUID id);
    CommentDTO save(CommentDTO dto);

}
