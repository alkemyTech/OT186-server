package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideDTOBasic;
import com.alkemy.ong.services.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/slide")
public class SlideController {

    @Autowired
    private SlideService slideService;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<SlideDTOBasic>> getAllSlide(){
        List<SlideDTOBasic> slideDTOBasic = slideService.getAllSlideBasic();
        return ResponseEntity.ok().body(slideDTOBasic);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SlideDTO> getById(@PathVariable UUID id){
        SlideDTO dto = slideService.getDetailsById(id);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity delete(@PathVariable UUID id){
        try {
            slideService.delete(id);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SlideDTO> update(@PathVariable UUID id, @RequestBody SlideDTO slideDTO) {
        SlideDTO result = this.slideService.update(id,slideDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SlideDTO> create(@RequestBody SlideDTO slideDto) throws IOException {
        SlideDTO result = slideService.create(slideDto);
        return ResponseEntity.ok().body(result);
    }

}
