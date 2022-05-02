package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.config.services.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    @Autowired
    private TestimonialService service;

    @PostMapping
    public ResponseEntity<TestimonialDTO> save (@Valid  @RequestBody TestimonialDTO testimonialDTO){
        TestimonialDTO testimonialSave = service.save(testimonialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(testimonialSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestimonialDTO> update (@PathVariable UUID id, @RequestBody TestimonialDTO testimonialDTO){
        TestimonialDTO testimonialUpdate = service.update(id, testimonialDTO);
        return ResponseEntity.ok().body(testimonialUpdate);
    }
}
