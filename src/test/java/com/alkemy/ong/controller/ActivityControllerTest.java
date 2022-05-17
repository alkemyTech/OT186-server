package com.alkemy.ong.controller;

import com.alkemy.ong.auth.AuthForTest;
import com.alkemy.ong.config.AmazonS3ClientConfig;
import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.entity.Activity;
import com.alkemy.ong.repository.ActivityRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActivityControllerTest extends AuthForTest {
    @MockBean
    private ActivityRepository activityRepository;
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
        ActivityDTO activityDTO = buildRequest();
        when(activityRepository.findById(uuid)).thenReturn(fakeActivity());
        when(activityRepository.save(any(Activity.class))).thenReturn(fakeSaveActivity());
        HttpEntity<ActivityDTO> httpEntity = new HttpEntity<>(activityDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<ActivityDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/activities"), HttpMethod.POST, httpEntity, ActivityDTO.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(activityDTO.getName(), responseEntity.getBody().getName());
        Assertions.assertEquals(activityDTO.getContent(), responseEntity.getBody().getContent());

    }
    @Test
    void saveWithNoAdmin() {
        ActivityDTO activityDTO = buildRequest();
        when(activityRepository.findById(uuid)).thenReturn(fakeActivity());
        when(activityRepository.save(any(Activity.class))).thenReturn(fakeSaveActivity());
        HttpEntity<ActivityDTO> httpEntity = new HttpEntity<>(activityDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<ActivityDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/activities"), HttpMethod.POST, httpEntity, ActivityDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
    @Test
    void updateWithAdmin() {
        ActivityDTO activityDTO = buildRequest();
        when(activityRepository.findById(uuid)).thenReturn(fakeActivity());
        when(activityRepository.save(any(Activity.class))).thenReturn(fakeSaveActivity());
        HttpEntity<ActivityDTO> httpEntity = new HttpEntity<>(activityDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<ActivityDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/activities/" + uuid.toString()), HttpMethod.PUT, httpEntity, ActivityDTO.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Test
    void saveAdminBadRequestWhenNoNameActivity(){
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName(null);
        HttpEntity<ActivityDTO> httpEntity = new HttpEntity<>(activityDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<ActivityDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/activities"), HttpMethod.POST, httpEntity, ActivityDTO.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }
    @Test
    void updateWithNoAdmin() {
        ActivityDTO activityDTO = buildRequest();
        when(activityRepository.findById(uuid)).thenReturn(fakeActivity());
        when(activityRepository.save(any(Activity.class))).thenReturn(fakeSaveActivity());
        HttpEntity<ActivityDTO> httpEntity = new HttpEntity<>(activityDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<ActivityDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/activities/" + uuid.toString()), HttpMethod.PUT, httpEntity, ActivityDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    private Optional<Activity> fakeActivity() {
        Activity activity = new Activity();
        activity.setId(uuid);
        activity.setImage("some img");
        activity.setName("some name");
        activity.setContent("some content");
        activity.setCreateAt(new Timestamp(System.currentTimeMillis()));
        activity.setSoft_delete(false);
        return Optional.of(activity);
    }
    private ActivityDTO buildRequest() {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setContent("some content");
        activityDTO.setName("some name");
        activityDTO.setImage("some img");
        return activityDTO;
    }
    private Activity fakeSaveActivity() {
        Activity activity = new Activity();
        activity.setId(uuid);
        activity.setImage("some img");
        activity.setName("some name");
        activity.setContent("some content");
        activity.setCreateAt(new Timestamp(System.currentTimeMillis()));
        activity.setSoft_delete(false);
        return activity;
    }
}