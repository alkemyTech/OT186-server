package com.alkemy.ong.controller;

import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.services.TestimonialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    @Autowired
    private TestimonialService service;

    @Operation(summary = "Get all News", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully operation",content = @Content(schema = @Schema(implementation = TestimonialDTO.class))),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter")
    })
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageFormatter<TestimonialDTO>> getAll(@PageableDefault(page= 0, size=10)Pageable pageable){
        PageFormatter<TestimonialDTO> testimonialDTO = service.findPageable(pageable);
        return ResponseEntity.ok().body(testimonialDTO);
    }

    @Operation(summary = "Adds a Testimonial", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter"),
            @ApiResponse(responseCode = "409", description = "Already exists")
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TestimonialDTO> save (@Parameter(description = "Testimonial DTO to save", required = true)
                                                @Valid  @RequestBody TestimonialDTO testimonialDTO){
        TestimonialDTO testimonialSave = service.save(testimonialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(testimonialSave);
    }

    @Operation(summary = "Adds a Testimonial", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TestimonialDTO> update (@Parameter(description = "Id of Testimonial to update", required = true)
                                                   @PathVariable UUID id,
                                                   @Parameter(description = "Testimonial DTO updated", required = true)
                                                   @RequestBody TestimonialDTO testimonialDTO){
        TestimonialDTO testimonialUpdate = service.update(id, testimonialDTO);
        return ResponseEntity.ok().body(testimonialUpdate);
    }

    @Operation(summary = "Deletes a message by id",  responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter"),
            @ApiResponse(responseCode = "404", description = "Message not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete (@Parameter(description = "Id of Testimonial to delete", required = true) @PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
