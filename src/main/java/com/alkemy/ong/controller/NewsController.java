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

    ////Trae todas las news en una list
    @GetMapping()
    public ResponseEntity<List<NewsDTO>> getAll(){
        List<NewsDTO> newsDTOList = newsService.getAllNews();
        return ResponseEntity.ok().body(newsDTOList);
    }

    ///OT186-45 detalle news por id
    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getByID (@PathVariable UUID id){
        NewsDTO dto = newsService.getDetailsById(id);
        return ResponseEntity.ok().body(dto);
    }


}
