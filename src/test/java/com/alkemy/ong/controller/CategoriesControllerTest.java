package com.alkemy.ong.controller;

import com.alkemy.ong.auth.AuthForTest;
import com.alkemy.ong.config.AmazonS3ClientConfig;
import com.alkemy.ong.dto.CategoriesBasicDTO;
import com.alkemy.ong.dto.CategoriesDTO;
import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.entity.Categories;
import com.alkemy.ong.repository.CategoriesRepository;
import com.alkemy.ong.services.OrganizationService;
import com.alkemy.ong.services.SlideService;
import com.alkemy.ong.services.imp.AmazonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoriesControllerTest extends AuthForTest {

    @MockBean
    private CategoriesRepository categoriesRepository;

    @MockBean
    private SlideService slideService;

    @MockBean
    private OrganizationService service;

    @MockBean
    private AmazonServiceImpl amazonService;

    @MockBean
    private AmazonS3ClientConfig amazonS3ClientConfig;

    private final UUID uuid = UUID.randomUUID();
    private Pageable pageable;

    @Test
    void getByIdWithAdmin() {
        CategoriesDTO categoriesDTO = buildRequest1();
        when(categoriesRepository.findById(uuid)).thenReturn(fakeCategory());
        when(categoriesRepository.save(any(Categories.class))).thenReturn(fakeSaveCategory());
        HttpEntity<CategoriesDTO> httpEntity = new HttpEntity<>(categoriesDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<CategoriesDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/categories/" + uuid.toString()), HttpMethod.GET, httpEntity, CategoriesDTO.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getByIdWithNoAdmin() {
        CategoriesDTO categoriesDTO = buildRequest1();
        when(categoriesRepository.findById(uuid)).thenReturn(fakeCategory());
        when(categoriesRepository.save(any(Categories.class))).thenReturn(fakeSaveCategory());
        HttpEntity<CategoriesDTO> httpEntity = new HttpEntity<>(categoriesDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<CategoriesDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/categories/" + uuid.toString()), HttpMethod.GET, httpEntity, CategoriesDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    public List<Categories> categoriesPage = List.of(new Categories());

    @Test
    void getAllWithAdmin() {
        Page<Categories> page = new PageImpl(categoriesPage);
        CategoriesBasicDTO categoriesBasicDTO = buildRequest2();
        when(categoriesRepository.findAll(any(Pageable.class))).thenReturn(page);

        HttpEntity<CategoriesBasicDTO> httpEntity = new HttpEntity<>(categoriesBasicDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<Categories> responseEntity = testRestTemplate.exchange(generateUriWithPort("/categories"), HttpMethod.GET, httpEntity, Categories.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getAllWithNoAdmin() {
        Page<Categories> page = new PageImpl(categoriesPage);
        CategoriesBasicDTO categoriesBasicDTO = buildRequest2();
        when(categoriesRepository.findAll(any(Pageable.class))).thenReturn(page);

        HttpEntity<CategoriesBasicDTO> httpEntity = new HttpEntity<>(categoriesBasicDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<Categories> responseEntity = testRestTemplate.exchange(generateUriWithPort("/categories"), HttpMethod.GET, httpEntity, Categories.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void saveWithAdmin() {
        CategoriesDTO categoriesDTO = buildRequest1();
        when(categoriesRepository.findById(uuid)).thenReturn(fakeCategory());
        when(categoriesRepository.save(any(Categories.class))).thenReturn(fakeSaveCategory());
        HttpEntity<CategoriesDTO> httpEntity = new HttpEntity<>(categoriesDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<CategoriesDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/categories"), HttpMethod.POST, httpEntity, CategoriesDTO.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void saveWithNoAdmin() {
        CategoriesDTO categoriesDTO = buildRequest1();
        when(categoriesRepository.findById(uuid)).thenReturn(fakeCategory());
        when(categoriesRepository.save(any(Categories.class))).thenReturn(fakeSaveCategory());
        HttpEntity<CategoriesDTO> httpEntity = new HttpEntity<>(categoriesDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<CategoriesDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/categories"), HttpMethod.POST, httpEntity, CategoriesDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void updateWithAdmin() {
        CategoriesDTO categoriesDTO = buildRequest1();
        when(categoriesRepository.findById(uuid)).thenReturn(fakeCategory());
        when(categoriesRepository.save(any(Categories.class))).thenReturn(fakeSaveCategory());
        HttpEntity<CategoriesDTO> httpEntity = new HttpEntity<>(categoriesDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<CategoriesDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/categories/" + uuid.toString()), HttpMethod.PUT, httpEntity, CategoriesDTO.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateWithNoAdmin() {
        CategoriesDTO categoriesDTO = buildRequest1();
        when(categoriesRepository.findById(uuid)).thenReturn(fakeCategory());
        when(categoriesRepository.save(any(Categories.class))).thenReturn(fakeSaveCategory());
        HttpEntity<CategoriesDTO> httpEntity = new HttpEntity<>(categoriesDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<CategoriesDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/categories/" + uuid.toString()), HttpMethod.PUT, httpEntity, CategoriesDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void deleteWithAdmin() {
        CategoriesDTO categoriesDTO = buildRequest1();
        when(categoriesRepository.findById(uuid)).thenReturn(fakeCategory());
        when(categoriesRepository.save(any(Categories.class))).thenReturn(fakeSaveCategory());
        HttpEntity<CategoriesDTO> httpEntity = new HttpEntity<>(categoriesDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<CategoriesDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/categories/" + uuid.toString()), HttpMethod.DELETE, httpEntity, CategoriesDTO.class);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void deleteWithNotAdmin() {
        CategoriesDTO categoriesDTO = buildRequest1();
        when(categoriesRepository.findById(uuid)).thenReturn(fakeCategory());
        when(categoriesRepository.save(any(Categories.class))).thenReturn(fakeSaveCategory());
        HttpEntity<CategoriesDTO> httpEntity = new HttpEntity<>(categoriesDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<CategoriesDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/categories/" + uuid.toString()), HttpMethod.DELETE, httpEntity, CategoriesDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    private Optional<Categories> fakeCategory() {
        Categories category = new Categories();
        category.setId(uuid);
        category.setImage("some img");
        category.setName("some name");
        category.setDescription("some description");
        category.setTimestamp(new Timestamp(System.currentTimeMillis()));
        category.setSoftDelete(false);
        return Optional.of(category);
    }

    private CategoriesDTO buildRequest1() {
        CategoriesDTO categoriesDTO = new CategoriesDTO();
        categoriesDTO.setId(uuid);
        categoriesDTO.setImage("some img");
        categoriesDTO.setName("some name");
        categoriesDTO.setDescription("some description");
        return categoriesDTO;
    }

    private CategoriesBasicDTO buildRequest2() {
        CategoriesBasicDTO categoriesBasicDTO = new CategoriesBasicDTO();
        categoriesBasicDTO.setName("Some name");
        return categoriesBasicDTO;
    }

    private Categories fakeSaveCategory(){
        Categories category = new Categories();
        category.setId(uuid);
        category.setImage("some img");
        category.setName("some name");
        category.setDescription("some description");
        category.setTimestamp(new Timestamp(System.currentTimeMillis()));
        category.setSoftDelete(false);
        return category;
    }


}