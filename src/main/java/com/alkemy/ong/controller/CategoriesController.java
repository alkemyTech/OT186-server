package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoriesBasicDTO;
import com.alkemy.ong.dto.CategoriesDTO;
import com.alkemy.ong.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
>>>>>>> origin/main
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

<<<<<<< HEAD
    //OT186-40
    @GetMapping()
    public ResponseEntity<List<CategoriesBasicDTO>> getAll (){
=======
    @GetMapping("/{id}")
    public ResponseEntity<CategoriesDTO> getById(@PathVariable UUID id) {
        CategoriesDTO dto = categoriesService.getDetailsById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping()
    public ResponseEntity<List<CategoriesBasicDTO>> getAll() {
>>>>>>> origin/main
        List<CategoriesBasicDTO> categoriesBasicDTO = categoriesService.getBasicDTOList();
        return ResponseEntity.ok().body(categoriesBasicDTO);
    }

<<<<<<< HEAD
    //OT186-41
    @GetMapping("/{id}")
    public ResponseEntity<CategoriesDTO> getById (@PathVariable UUID id){
            CategoriesDTO dto = categoriesService.getDetailsById(id);
            return ResponseEntity.ok().body(dto);
    }


=======
    @PostMapping
    public ResponseEntity<CategoriesDTO> save(@Valid @RequestBody CategoriesDTO dto) {
        CategoriesDTO categorySaved = categoriesService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriesDTO> update(@PathVariable UUID id, @Valid @RequestBody CategoriesDTO dto) {
        CategoriesDTO CategoriesUpdated = categoriesService.update(id, dto);
        return ResponseEntity.ok().body(CategoriesUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        try {
            categoriesService.delete(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
>>>>>>> origin/main

}
