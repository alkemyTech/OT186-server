package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.entity.Comments;
import com.alkemy.ong.mapper.CommentsMapper;
import com.alkemy.ong.repository.CommentsRepository;
import com.alkemy.ong.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private CommentsMapper commentsMapper;

    public CommentDTO update(UUID id, CommentDTO commentDTO) {
        Optional<Comments> result = commentsRepository.findById(id);
        if(result.isPresent()){
            Comments entity = commentsMapper.updateComment2DTO(result.get(),commentDTO);
            Comments entityUpdated = commentsRepository.save(entity);
            return commentsMapper.comment2DTO(entityUpdated);
        }else{
            throw new EntityNotFoundException("Comments not found");
        }
    }

    public void delete(UUID id){
        if(commentsRepository.findById(id) == null){
            throw new EntityNotFoundException("Comments not found");
        }
        commentsRepository.deleteById(id);
    }
}
