package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactsDTO;
import com.alkemy.ong.services.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactsService contactsService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactsDTO> save(@Valid @RequestBody ContactsDTO contacts) {
        ContactsDTO contactsCreated = contactsService.save(contacts);
        return ResponseEntity.status(HttpStatus.CREATED).body(contactsCreated);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ContactsDTO>> getAll() {
        List<ContactsDTO> dtos = contactsService.getAllContacts();
        return ResponseEntity.ok().body(dtos);
    }

}
