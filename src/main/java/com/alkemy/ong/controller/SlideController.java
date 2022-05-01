package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideDTOBasic;
import com.alkemy.ong.services.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/slide")
public class SlideController {

    @Autowired
    private SlideService slideService;

    @GetMapping()
    public ResponseEntity<List<SlideDTOBasic>> getAllSlide(){
        List<SlideDTOBasic> slideDTOBasicList = slideService.getAllSlideBasic();
        return ResponseEntity.ok().body(slideDTOBasicList);
    }

}
