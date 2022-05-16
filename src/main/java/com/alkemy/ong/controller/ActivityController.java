package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.services.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Save an activity", description = "Add an activity only for admins", responses = {
            @ApiResponse(responseCode = "200", description = "Succesful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid Activity suplied", content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter"),
            @ApiResponse(responseCode = "409", description = "Activity already exists", content = @Content)
    })
    @PostMapping(produces = {"application/json"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ActivityDTO> save (@Parameter(description = "Activity DTO to save", required = true) @Valid @RequestBody ActivityDTO dto){
        ActivityDTO activitySave = activityService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(activitySave);
    }

    @Operation(summary = "Update an activity by Id", description = "Update an activity saved, only for admins", responses = {
            @ApiResponse(responseCode = "200", description = "Succesful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid Activity suplied", content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter"),
            @ApiResponse(responseCode = "404", description = "Activity not found", content = @Content)
    })
    @PutMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ActivityDTO> update (@Parameter(description ="Activity Id to update", required = true) @PathVariable UUID id,
                                               @Parameter(description ="Activity DTO to update", required = true) @RequestBody ActivityDTO dto){
        ActivityDTO Update = activityService.update(id, dto);
        return ResponseEntity.ok().body(Update);
    }
}