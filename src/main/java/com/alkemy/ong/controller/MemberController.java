package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageFormatter<MemberDTO>>getAll(@PageableDefault(page=0, size= 10)Pageable pageable){
        PageFormatter<MemberDTO> memberDTO = memberService.findPageable(pageable);
        return ResponseEntity.ok().body(memberDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody MemberDTO updated){
        MemberDTO memberDTO;
        try {
            memberDTO = memberService.update(id, updated);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(memberDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.memberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping
    public ResponseEntity<MemberDTO> create(@Valid @RequestBody MemberDTO memberDTO) {
        MemberDTO savedMember = memberService.create(memberDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }
}

