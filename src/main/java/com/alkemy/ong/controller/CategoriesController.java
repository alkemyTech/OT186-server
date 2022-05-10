package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoriesBasicDTO;
import com.alkemy.ong.dto.CategoriesDTO;
import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.services.CategoriesService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@Api(tags = "Categories Controller", description = "Operations on Categories related to News")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval", response = CategoriesDTO.class),
            @ApiResponse(code = 404, message = "Category not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriesDTO> getById(   @ApiParam(value = "category_id", required = true)
                                                    @PathVariable UUID id) {
        CategoriesDTO dto = categoriesService.getDetailsById(id);
        return ResponseEntity.ok().body(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval", response = CategoriesBasicDTO.class, responseContainer = "PageFormatter"),
    })
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageFormatter<CategoriesBasicDTO>> getAll(@PageableDefault(page=0, size = 10)Pageable pageable) {
        PageFormatter<CategoriesBasicDTO> categoriesBasicDTO = categoriesService.findPageable(pageable);
        return ResponseEntity.ok().body(categoriesBasicDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = CategoriesDTO.class)
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriesDTO> save(  @ApiParam(value = "Category dto to save", required = true)
                                                @Valid @RequestBody CategoriesDTO dto) {
        CategoriesDTO categorySaved = categoriesService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySaved);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = CategoriesDTO.class),
            @ApiResponse(code = 404, message = "Category not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriesDTO> update( @ApiParam(value = "category_id", required = true)
                                                 @PathVariable UUID id,
                                                 @ApiParam(value = "Category dto updated", required = true)
                                                 @Valid @RequestBody CategoriesDTO dto) {
        CategoriesDTO CategoriesUpdated = categoriesService.update(id, dto);
        return ResponseEntity.ok().body(CategoriesUpdated);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Category deleted successfully"),
            @ApiResponse(code = 404, message = "Category not found")
    })
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
