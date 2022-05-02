package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.config.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    public ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityDTO> save (@Valid @RequestBody ActivityDTO dto){
        ActivityDTO activitySave = activityService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(activitySave);
    }

}