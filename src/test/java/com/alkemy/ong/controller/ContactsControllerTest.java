package com.alkemy.ong.controller;

import com.alkemy.ong.auth.AuthForTest;
import com.alkemy.ong.config.AmazonS3ClientConfig;
import com.alkemy.ong.dto.ContactsDTO;
import com.alkemy.ong.entity.Contacts;
import com.alkemy.ong.repository.ContactsRepository;
import com.alkemy.ong.services.OrganizationService;
import com.alkemy.ong.services.SlideService;
import com.alkemy.ong.services.imp.AmazonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactsControllerTest extends AuthForTest {

    @MockBean
    private ContactsRepository contactsRepository;
    @MockBean
    private SlideService slideService;
    @MockBean
    private OrganizationService service;
    @MockBean
    private AmazonServiceImpl amazonService;
    @MockBean
    private AmazonS3ClientConfig amazonS3ClientConfig;

    private final UUID uuid = UUID.randomUUID();

    private List<Contacts> fakeContactsList = List.of(fakeContact());

    @Test
    void saveAdmin() {
        ContactsDTO contactsDTO = buildRequest();
        when(contactsRepository.findById(uuid)).thenReturn(fakeOptionalContact());
        when(contactsRepository.save(any(Contacts.class))).thenReturn(fakeContact());
        HttpEntity<ContactsDTO> httpEntity = new HttpEntity<>(contactsDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<ContactsDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/contacts"), HttpMethod.POST, httpEntity, ContactsDTO.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(contactsDTO.getName(), responseEntity.getBody().getName());
        Assertions.assertEquals(contactsDTO.getEmail(), responseEntity.getBody().getEmail());

    }
    @Test
    void saveBadRequest() {
        ContactsDTO contactsDTO = buildRequest();
        when(contactsRepository.findById(uuid)).thenReturn(fakeOptionalContact());
        when(contactsRepository.save(any(Contacts.class))).thenReturn(fakeContact());
        HttpEntity<ContactsDTO> httpEntity = new HttpEntity<>(new ContactsDTO(), httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<ContactsDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/contacts"), HttpMethod.POST, httpEntity, ContactsDTO.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void getAllAdmin() {
        when(contactsRepository.findAll()).thenReturn(fakeContactsList);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<List<ContactsDTO>> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/contacts"), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<ContactsDTO>>() {});
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getAllUser() {
        when(contactsRepository.findAll()).thenReturn(fakeContactsList);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<List<ContactsDTO>> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/contacts"), HttpMethod.GET, httpEntity, (Class<List<ContactsDTO>>) null);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    private Optional<Contacts> fakeOptionalContact() {
        Contacts contact = new Contacts();
        contact.setId(uuid);
        contact.setName("contact");
        contact.setPhone(123456789);
        contact.setEmail("email@email.com");
        contact.setMessage("message");
        contact.setDeletedAt(false);
        return Optional.of(contact);
    }
    private ContactsDTO buildRequest() {
        ContactsDTO contactDTO = new ContactsDTO();
        contactDTO.setName("contact");
        contactDTO.setPhone(123456789);
        contactDTO.setEmail("email@email.com");
        contactDTO.setMessage("message");
        return contactDTO;
    }
    private Contacts fakeContact() {
        Contacts contact = new Contacts();
        contact.setId(uuid);
        contact.setName("contact");
        contact.setPhone(123456789);
        contact.setEmail("email@email.com");
        contact.setMessage("message");
        contact.setDeletedAt(false);
        return contact;
    }

}