package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideDTOBasic;
import com.alkemy.ong.services.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/slide")
public class SlideController {

    @Autowired
    private SlideService slideService;

    @GetMapping()
    public ResponseEntity<List<SlideDTOBasic>> getAllSlide(){
        List<SlideDTOBasic> slideDTOBasic = slideService.getAllSlideBasic();
        return ResponseEntity.ok().body(slideDTOBasic);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlideDTO> getById(@PathVariable UUID id){
        SlideDTO dto = slideService.getDetailsById(id);
        return ResponseEntity.ok().body(dto);
    }

}
