package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.ContactsDTO;
import com.alkemy.ong.entity.Contacts;
import com.alkemy.ong.mapper.ContactsMapper;
import com.alkemy.ong.repository.ContactsRepository;
import com.alkemy.ong.services.ContactsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@NoArgsConstructor
@AllArgsConstructor
@Service
public class ContactsServiceImp implements ContactsService {
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private ContactsRepository contactsRepository;


    public ContactsDTO save(ContactsDTO dto) {
        Contacts entity = contactsMapper.contactsDTO2Entity(dto);
        Contacts entitySaved = this.contactsRepository.save(entity);
        ContactsDTO result = this.contactsMapper.contactsEntity2DTO(entitySaved);
        return result;
    }
}
