package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MemberDTO>> getAll(){
        List<MemberDTO> members = memberService.getAll();
        return ResponseEntity.ok().body(members);
    }

}

