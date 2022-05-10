package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.dto.CommentsBasicDTO;
import com.alkemy.ong.entity.Comments;
import com.alkemy.ong.repository.CommentsRepository;
import com.alkemy.ong.services.CommentService;
import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentService service;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentsRepository commentsRepository;

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update (@PathVariable UUID id, @RequestBody CommentDTO commentDTO, HttpServletRequest req) {
        if(userService.validateRole(commentDTO.getUsers().getId(), req)) {
            CommentDTO commentUpdate;
            try {
                commentUpdate = service.update(id, commentDTO);
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok().body(commentUpdate);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable UUID id, HttpServletRequest req){
        Optional<Comments> result = commentsRepository.findById(id);
        if(!result.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Comments comment = result.get();
        if(userService.validateRole(comment.getUser().getId(), req)) {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping()
    public ResponseEntity<CommentDTO> save(@Valid @RequestBody CommentDTO comment, HttpServletRequest req) {
        if(userService.validateRole(comment.getUsers().getId(), req)) {
            CommentDTO commentCreated = service.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(commentCreated);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping
    public ResponseEntity<List<CommentsBasicDTO>> getAll(){
        List<CommentsBasicDTO> dtos = service.getAll();
        return ResponseEntity.ok().body(dtos);
    }

}
