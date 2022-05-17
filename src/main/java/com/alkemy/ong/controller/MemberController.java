package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.services.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @Operation(summary = "Get a list of Members", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageFormatter.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content=@Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content)
    })
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageFormatter<MemberDTO>>getAll(@PageableDefault(page=0, size= 10)
                                                              @Parameter(description = "Number of page, default is 0")Pageable pageable){
        PageFormatter<MemberDTO> memberDTO = memberService.findPageable(pageable);
        return ResponseEntity.ok().body(memberDTO);
    }

    @Operation(summary = "Update a member", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDTO
                    .class))),
            @ApiResponse(responseCode = "400", description = "Invalid id or Member supplied", content=@Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content)
    })
    @PutMapping("{id}")
    public ResponseEntity update(@Parameter(description = "Id of Member to update", required = true) @PathVariable UUID id,
                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Member DTO update", required = true)
                                 @Valid @RequestBody MemberDTO updated){
        MemberDTO memberDTO;
        try {
            memberDTO = memberService.update(id, updated);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(memberDTO);
    }

    @Operation(summary = "Delete a member", responses = {
            @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content=@Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of Member to delete", required = true) @PathVariable UUID id) {
        this.memberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Save a member", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDTO
                    .class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content=@Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content=@Content)
    })
    @PostMapping
    public ResponseEntity<MemberDTO> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Member to save", required = true)
                                                @Valid @RequestBody MemberDTO memberDTO) {
        MemberDTO savedMember = memberService.create(memberDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }
}

