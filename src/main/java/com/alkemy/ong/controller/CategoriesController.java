package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoriesBasicDTO;
import com.alkemy.ong.dto.CategoriesDTO;
import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.services.CategoriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(description = "Operations on Categories related to News", name = "Categories Controller")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @Operation(summary = "Get Category by Id", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully operation",content = @Content(schema = @Schema(implementation = CategoriesDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriesDTO> getById(   @Parameter(description = "Id of Category to find", required = true)
                                                    @PathVariable UUID id) {
        CategoriesDTO dto = categoriesService.getDetailsById(id);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Get all Categories", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = PageFormatter.class))),
    })
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageFormatter<CategoriesBasicDTO>> getAll(@PageableDefault(page=0, size = 10)Pageable pageable) {
        PageFormatter<CategoriesBasicDTO> categoriesBasicDTO = categoriesService.findPageable(pageable);
        return ResponseEntity.ok().body(categoriesBasicDTO);
    }

    @Operation(summary = "Add Category", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CategoriesDTO.class))),
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriesDTO> save(  @Parameter(description = "Category dto to save", required = true)
                                                @Valid @RequestBody CategoriesDTO dto) {
        CategoriesDTO categorySaved = categoriesService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySaved);
    }

    @Operation(summary = "Update Category", responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = CategoriesDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriesDTO> update( @Parameter(description = "Id of Category to update", required = true)
                                                 @PathVariable UUID id,
                                                 @Parameter(description = "Category dto updated", required = true)
                                                 @Valid @RequestBody CategoriesDTO dto) {
        CategoriesDTO CategoriesUpdated = categoriesService.update(id, dto);
        return ResponseEntity.ok().body(CategoriesUpdated);
    }

    @Operation(summary = "Delete Category", responses = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity delete(   @Parameter(description = "Id of Category to delete", required = true)
                                    @PathVariable UUID id) {
        try {
            categoriesService.delete(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
