package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.entity.Comments;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.mapper.CommentsMapper;
import com.alkemy.ong.repository.CommentsRepository;
import com.alkemy.ong.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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
        Optional<Comments> result = commentsRepository.findById(id);
        if(!result.isPresent()){
            throw new EntityNotFoundException("Comments not found");
        }
        commentsRepository.deleteById(id);
    }

    public CommentDTO save(CommentDTO dto) {
        Comments entitySaved = commentsRepository.save(commentsMapper.DTO2Entity(dto));
        CommentDTO result = commentsMapper.comment2DTO(entitySaved);
        return result;
    }

    @Override
    public List<CommentBasicDTO> findAllByNewsId(UUID id) {
        News news = new News();
        news.setId(id);
        Optional<List<Comments>> commentsList = commentsRepository.findByNews(news);
        if(commentsList.isEmpty()){
            throw new EntityNotFoundException("News not found");
        }
        return commentsMapper.listComment2listCommentDTO(commentsList.get());

    }

}
