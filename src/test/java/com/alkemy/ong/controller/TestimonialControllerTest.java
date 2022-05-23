package com.alkemy.ong.controller;

import com.alkemy.ong.auth.AuthForTest;
import com.alkemy.ong.config.AmazonS3ClientConfig;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.entity.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.services.OrganizationService;
import com.alkemy.ong.services.SlideService;
import com.alkemy.ong.services.imp.AmazonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestimonialControllerTest extends AuthForTest {

    @MockBean
    private TestimonialRepository testimonialRepository;
    @MockBean
    private SlideService slideService;
    @MockBean
    private OrganizationService service;
    @MockBean
    private AmazonServiceImpl amazonService;
    @MockBean
    private AmazonS3ClientConfig amazonS3ClientConfig;
    private final UUID uuid = UUID.randomUUID();
    @Test
    void saveWithAdmin() {
        TestimonialDTO testimonialDTO = buildRequest();
        when(testimonialRepository.findById(uuid)).thenReturn(fakeTestimonial());
        when(testimonialRepository.save(any(Testimonial.class))).thenReturn(fakeSaveTestimonial());
        HttpEntity<TestimonialDTO> httpEntity = new HttpEntity<>(testimonialDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<TestimonialDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/testimonials"), HttpMethod.POST, httpEntity, TestimonialDTO.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(testimonialDTO.getName(), responseEntity.getBody().getName());
        Assertions.assertEquals(testimonialDTO.getContent(), responseEntity.getBody().getContent());

    }
    @Test
    void saveWithNoAdmin() {
        TestimonialDTO testimonialDTO = buildRequest();
        when(testimonialRepository.findById(uuid)).thenReturn(fakeTestimonial());
        when(testimonialRepository.save(any(Testimonial.class))).thenReturn(fakeSaveTestimonial());
        HttpEntity<TestimonialDTO> httpEntity = new HttpEntity<>(testimonialDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<TestimonialDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/testimonials"), HttpMethod.POST, httpEntity, TestimonialDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
    @Test
    void updateWithAdmin() {
        TestimonialDTO testimonialDTO = buildRequest();
        when(testimonialRepository.findById(uuid)).thenReturn(fakeTestimonial());
        when(testimonialRepository.save(any(Testimonial.class))).thenReturn(fakeSaveTestimonial());
        HttpEntity<TestimonialDTO> httpEntity = new HttpEntity<>(testimonialDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<TestimonialDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/testimonials/" + uuid.toString()), HttpMethod.PUT, httpEntity, TestimonialDTO.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Test
    void saveAdminBadRequestWhenNoNameTestimonial(){
        TestimonialDTO testimonialDTO = new TestimonialDTO();
        testimonialDTO.setName(null);
        HttpEntity<TestimonialDTO> httpEntity = new HttpEntity<>(testimonialDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<TestimonialDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/testimonials"), HttpMethod.POST, httpEntity, TestimonialDTO.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }
    @Test
    void updateWithNoAdmin() {
        TestimonialDTO testimonialDTO = buildRequest();
        when(testimonialRepository.findById(uuid)).thenReturn(fakeTestimonial());
        when(testimonialRepository.save(any(Testimonial.class))).thenReturn(fakeSaveTestimonial());
        HttpEntity<TestimonialDTO> httpEntity = new HttpEntity<>(testimonialDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<TestimonialDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/testimonials/" + uuid.toString()), HttpMethod.PUT, httpEntity, TestimonialDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
    @Test
    void deleteWithNoAdmin() {
        TestimonialDTO userDTO = buildRequest();
        when(testimonialRepository.findById(uuid)).thenReturn(fakeTestimonial());
        when(testimonialRepository.save(any(Testimonial.class))).thenReturn(fakeSaveTestimonial());
        HttpEntity<TestimonialDTO> httpEntity = new HttpEntity<>(userDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<TestimonialDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/users/" + uuid.toString()), HttpMethod.DELETE, httpEntity, TestimonialDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
    private Optional<Testimonial> fakeTestimonial() {
        Testimonial testimonial = new Testimonial();
        testimonial.setId(uuid);
        testimonial.setImage("some img");
        testimonial.setName("some name");
        testimonial.setContent("some content");
        testimonial.setCreateAt(new Timestamp(System.currentTimeMillis()));
        return Optional.of(testimonial);
    }
    private TestimonialDTO buildRequest() {
        TestimonialDTO testimonialDTO = new TestimonialDTO();
        testimonialDTO.setContent("some content");
        testimonialDTO.setName("some name");
        testimonialDTO.setImage("some img");
        return testimonialDTO;
    }
    private Testimonial fakeSaveTestimonial() {
        Testimonial testimonial = new Testimonial();
        testimonial.setId(uuid);
        testimonial.setImage("some img");
        testimonial.setName("some name");
        testimonial.setContent("some content");
        testimonial.setCreateAt(new Timestamp(System.currentTimeMillis()));
        return testimonial;
    }
}