package com.alkemy.ong.controller;


import com.alkemy.ong.auth.AuthForTest;
import com.alkemy.ong.config.AmazonS3ClientConfig;
import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.dto.SlideDTOBasic;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.services.OrganizationService;
import com.alkemy.ong.services.SlideService;
import com.alkemy.ong.services.imp.AmazonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizationControllerTest extends AuthForTest {

    @MockBean
    private OrganizationRepository organizationRepository;
    @MockBean
    private SlideService slideService;
    @MockBean
    private AmazonServiceImpl amazonService;
    @MockBean
    private AmazonS3ClientConfig amazonS3ClientConfig;

    private static String ORG_PATH = "/organization/public";

    private static UUID ID_ORG = UUID.fromString("6a830887-7d7e-49f4-a541-8a836cd3365f");

    private Organization aOrganization(){

        Organization org = new Organization();
        org.setId(ID_ORG);
        org.setName("Org Example");
        org.setEmail("example@gmail.com");
        org.setImage("image.jpg");
        org.setAddress("Street 1234");
        org.setAboutUsText("This text is for an example.");
        org.setFacebook("fb.com/org");
        org.setInstagram("ig.com/org");
        org.setLinkedin("in.com/org");
        org.setPhone(123456789);
        org.setWelcomeText("Welcome test");
        org.setSoftDelete(Boolean.FALSE);
        org.setTimestamps(Timestamp.from(Instant.now()));
        return org;
    }

    private OrganizationDTO aOrganizationDTO(){
        OrganizationDTO org = new OrganizationDTO();
        org.setId(ID_ORG);
        org.setName("Org Example");
        org.setImage("image.jpg");
        org.setAddress("Street 1234");
        org.setFacebook("fb.com/org");
        org.setInstagram("ig.com/org");
        org.setLinkedin("in.com/org");
        org.setSlides(List.of(new SlideDTOBasic()));
        return org;
    }

    @Test
    void getDetailsByIdHappyPath(){

        when(organizationRepository.findById(ID_ORG)).thenReturn(Optional.of(aOrganization()));
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_ADMIN");

        ResponseEntity<OrganizationDTO> response = testRestTemplate.exchange(
                generateUriWithPort(ORG_PATH + "/" + ID_ORG),
                HttpMethod.GET,
                httpEntity,
                OrganizationDTO.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ID_ORG, response.getBody().getId());
    }

    @Test
    void getDetailsByIdWithoutLogin401(){

        when(organizationRepository.findById(ID_ORG)).thenReturn(Optional.of(aOrganization()));
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<OrganizationDTO> response = testRestTemplate.exchange(
                generateUriWithPort(ORG_PATH + "/" + ID_ORG),
                HttpMethod.GET,
                httpEntity,
                OrganizationDTO.class
        );

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void updateHappyPath(){

        when(organizationRepository.findById(ID_ORG)).thenReturn(Optional.of(aOrganization()));
        when(organizationRepository.save(any(Organization.class))).thenReturn(aOrganization());
        HttpEntity<OrganizationDTO> httpEntity = new HttpEntity<>(aOrganizationDTO(), httpHeaders);
        putTokenInHeader("ROLE_ADMIN");

        ResponseEntity<OrganizationDTO> response = testRestTemplate.exchange(
                generateUriWithPort(ORG_PATH + "/" + ID_ORG),
                HttpMethod.PUT,
                httpEntity,
                OrganizationDTO.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateWithUser403(){

        when(organizationRepository.findById(ID_ORG)).thenReturn(Optional.of(aOrganization()));
        when(organizationRepository.save(any(Organization.class))).thenReturn(aOrganization());
        HttpEntity<OrganizationDTO> httpEntity = new HttpEntity<>(aOrganizationDTO(), httpHeaders);
        putTokenInHeader("ROLE_USER");

        ResponseEntity<OrganizationDTO> response = testRestTemplate.exchange(
                generateUriWithPort(ORG_PATH + "/" + ID_ORG),
                HttpMethod.PUT,
                httpEntity,
                OrganizationDTO.class);

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void updateInvalidBody400(){

        when(organizationRepository.findById(ID_ORG)).thenReturn(Optional.of(aOrganization()));
        HttpEntity<OrganizationDTO> httpEntity = new HttpEntity<>(new OrganizationDTO(), httpHeaders);
        putTokenInHeader("ROLE_ADMIN");

        ResponseEntity<OrganizationDTO> response = testRestTemplate.exchange(
                generateUriWithPort(ORG_PATH + "/" + ID_ORG),
                HttpMethod.PUT,
                httpEntity,
                OrganizationDTO.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}