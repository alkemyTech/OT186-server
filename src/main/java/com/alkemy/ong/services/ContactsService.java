package com.alkemy.ong.services;

import com.alkemy.ong.dto.ContactsDTO;

import java.util.List;

public interface ContactsService {
    ContactsDTO save(ContactsDTO contacts);
    List<ContactsDTO> getAllContacts();
}
