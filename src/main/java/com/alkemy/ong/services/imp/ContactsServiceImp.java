package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.ContactsDTO;
import com.alkemy.ong.entity.Contacts;
import com.alkemy.ong.mapper.ContactsMapper;
import com.alkemy.ong.repository.ContactsRepository;
import com.alkemy.ong.services.ContactsService;
import com.alkemy.ong.services.EmailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Service
public class ContactsServiceImp implements ContactsService {
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private ContactsRepository contactsRepository;
    @Autowired
    private EmailService emailService;


    public ContactsDTO save(ContactsDTO dto) {
        Contacts entity = contactsMapper.contactsDTO2Entity(dto);
        Contacts entitySaved = this.contactsRepository.save(entity);
        ContactsDTO result = this.contactsMapper.contactsEntity2DTO(entitySaved);
        emailService.sendEmailContact(result.getEmail());
        return result;
    }

    public List<ContactsDTO> getAllContacts() {
        List<Contacts> contacts = contactsRepository.findAll();
        List<ContactsDTO> result = contactsMapper.entity2DTOList(contacts);
        return result;
    }

}
