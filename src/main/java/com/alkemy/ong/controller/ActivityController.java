package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    public ActivityService activityService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ActivityDTO> save (@Valid @RequestBody ActivityDTO dto){
        ActivityDTO activitySave = activityService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(activitySave);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ActivityDTO> update (@PathVariable UUID id, @RequestBody ActivityDTO dto){
        ActivityDTO Update = activityService.update(id, dto);
        return ResponseEntity.ok().body(Update);
    }
}