package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ContactsDTO;
import com.alkemy.ong.entity.Contacts;
import org.springframework.stereotype.Component;

@Component
public class ContactsMapper {
    public Contacts contactsDTO2Entity(ContactsDTO dto) {
        Contacts entity = new Contacts();
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setMessage(dto.getMessage());
        return entity;
    }

    public ContactsDTO contactsEntity2DTO(Contacts contacts) {
        ContactsDTO dto = new ContactsDTO();
        dto.setId(contacts.getId());
        dto.setName(contacts.getName());
        dto.setPhone(contacts.getPhone());
        dto.setEmail(contacts.getEmail());
        dto.setMessage(contacts.getMessage());
        return dto;

    }
}
