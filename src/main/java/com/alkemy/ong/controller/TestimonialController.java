package com.alkemy.ong.controller;

import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.services.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    @Autowired
    private TestimonialService service;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageFormatter<TestimonialDTO>> getAll(@PageableDefault(page= 0, size=10)Pageable pageable){
        PageFormatter<TestimonialDTO> testimonialDTO = service.findPageable(pageable);
        return ResponseEntity.ok().body(testimonialDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TestimonialDTO> save (@Valid  @RequestBody TestimonialDTO testimonialDTO){
        TestimonialDTO testimonialSave = service.save(testimonialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(testimonialSave);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TestimonialDTO> update (@PathVariable UUID id, @RequestBody TestimonialDTO testimonialDTO){
        TestimonialDTO testimonialUpdate = service.update(id, testimonialDTO);
        return ResponseEntity.ok().body(testimonialUpdate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete (@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
