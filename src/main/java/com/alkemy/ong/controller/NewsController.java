package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping()
    public ResponseEntity<PageFormatter<NewsDTO>> getAll(@PageableDefault(page=0, size = 10)Pageable pageable){
        PageFormatter<NewsDTO> newsDTO = newsService.findPageable(pageable);
        return ResponseEntity.ok().body(newsDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NewsDTO> getByID (@PathVariable UUID id){
        NewsDTO dto = newsService.getDetailsById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NewsDTO> save (@Valid @RequestBody NewsDTO newsDTO){
        NewsDTO newsSaved = newsService.save(newsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsSaved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NewsDTO> update (@PathVariable UUID id, @RequestBody NewsDTO newsDTO){
        NewsDTO newsUpdated = newsService.update(id, newsDTO);
        return ResponseEntity.ok().body(newsUpdated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete (@PathVariable UUID id){
        newsService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
