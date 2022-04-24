package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    ///OT186-46 crear news
    @PostMapping
    public ResponseEntity<NewsDTO> save (@ResponseBody NewsDTO newsDTO){
        NewsDTO newsSaved = newsService.save(newsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsSaved);
    }


}
