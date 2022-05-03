package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("organization/public")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getDetailsById(@PathVariable UUID id){
        OrganizationDTO organization = this.organizationService.getDetailsById(id);
        return ResponseEntity.ok(organization);

    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrganizationDTO> update(@PathVariable UUID id, @Valid @RequestBody OrganizationDTO dto){
        OrganizationDTO organizationDTO = organizationService.update(id, dto);
        return ResponseEntity.ok().body(organizationDTO);
    }
}
