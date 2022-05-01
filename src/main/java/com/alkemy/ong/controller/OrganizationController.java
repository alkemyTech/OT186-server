package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("organization/public")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;
    private OrganizationRepository organizationRepository;

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getDetailsById(@PathVariable UUID id){
        OrganizationDTO organization = this.organizationService.getDetailsById(id);
        return ResponseEntity.ok(organization);

    }

    @PostMapping("/update")
    public Organization update(@RequestBody Organization organization){
        return organizationRepository.save(organization);
    }
}
