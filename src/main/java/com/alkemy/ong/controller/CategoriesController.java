package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoriesBasicDTO;
import com.alkemy.ong.dto.CategoriesDTO;
import com.alkemy.ong.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriesDTO> getById(@PathVariable UUID id) {
        CategoriesDTO dto = categoriesService.getDetailsById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CategoriesBasicDTO>> getAll() {
        List<CategoriesBasicDTO> categoriesBasicDTO = categoriesService.getBasicDTOList();
        return ResponseEntity.ok().body(categoriesBasicDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriesDTO> save(@Valid @RequestBody CategoriesDTO dto) {
        CategoriesDTO categorySaved = categoriesService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySaved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriesDTO> update(@PathVariable UUID id, @Valid @RequestBody CategoriesDTO dto) {
        CategoriesDTO CategoriesUpdated = categoriesService.update(id, dto);
        return ResponseEntity.ok().body(CategoriesUpdated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity delete(@PathVariable UUID id) {
        try {
            categoriesService.delete(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
