package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoriesBasicDTO;
import com.alkemy.ong.dto.CategoriesDTO;
import com.alkemy.ong.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    //OT186-40    @GetMapping()
    public ResponseEntity<List<CategoriesBasicDTO>> getAll (){
        List<CategoriesBasicDTO> categoriesBasicDTO = categoriesService.getBasicDTOList();
        return ResponseEntity.ok().body(categoriesBasicDTO);
    }


}
