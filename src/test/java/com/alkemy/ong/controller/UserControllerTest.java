package com.alkemy.ong.controller;

import com.alkemy.ong.auth.AuthForTest;
import com.alkemy.ong.config.AmazonS3ClientConfig;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;
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
class UserControllerTest extends AuthForTest {
    @MockBean
    private SlideService slideService;
    @MockBean
    private OrganizationService service;
    @MockBean
    private AmazonServiceImpl amazonService;
    @MockBean
    private AmazonS3ClientConfig amazonS3ClientConfig;
    private final UUID uuid = UUID.randomUUID();
    private List<User> fakeUserList = List.of(fakeUser());

    @Test
    void getAllAdmin() {
        when(userRepository.findAll()).thenReturn(fakeUserList);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<List<UserDTO>> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/users"), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<UserDTO>>() {});
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getAllUser() {
        when(userRepository.findAll()).thenReturn(fakeUserList);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<List<UserDTO>> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/users"), HttpMethod.GET, httpEntity, (Class<List<UserDTO>>) null);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void getAllWithoutLogin() {
        when(userRepository.findAll()).thenReturn(fakeUserList);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<UserDTO>> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/users"), HttpMethod.GET, httpEntity, (Class<List<UserDTO>>) null);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    void updateWithNoAdmin() {
        UserDTO userDTO = buildRequest();
        when(userRepository.findById(uuid)).thenReturn(fakeOptionalUser());
        when(userRepository.save(any(User.class))).thenReturn(fakeUser());
        HttpEntity<UserDTO> httpEntity = new HttpEntity<>(userDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<UserDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/users/" + uuid.toString()), HttpMethod.PATCH, httpEntity, UserDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void updateWithAdmin() {
        UserDTO userDTO = buildRequest();
        when(userRepository.findById(uuid)).thenReturn(fakeOptionalUser());
        when(userRepository.save(any(User.class))).thenReturn(fakeUser());
        HttpEntity<UserDTO> httpEntity = new HttpEntity<>(userDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<UserDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/users/" + uuid.toString()), HttpMethod.PATCH, httpEntity, UserDTO.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteWithNoAdmin() {
        UserDTO userDTO = buildRequest();
        when(userRepository.findById(uuid)).thenReturn(fakeOptionalUser());
        when(userRepository.save(any(User.class))).thenReturn(fakeUser());
        HttpEntity<UserDTO> httpEntity = new HttpEntity<>(userDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<UserDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort("/users/" + uuid.toString()), HttpMethod.DELETE, httpEntity, UserDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    private Optional<User> fakeOptionalUser() {
        User user = new User();
        user.setId(uuid);
        user.setFirstName("user");
        user.setLastName("alkemy");
        user.setEmail("email@email.com");
        user.setPassword("123456");
        user.setPhoto("foto");
        user.setSoftDelete(false);
        return Optional.of(user);
    }
    private UserDTO buildRequest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("user");
        userDTO.setLastName("alkemy");
        userDTO.setEmail("email@email.com");
        userDTO.setPassword("123456");
        userDTO.setPhoto("foto");
        return userDTO;
    }
    private User fakeUser() {
        User user = new User();
        user.setId(uuid);
        user.setFirstName("user");
        user.setLastName("alkemy");
        user.setEmail("email@email.com");
        user.setPassword("123456");
        user.setPhoto("foto");
        user.setSoftDelete(false);
        return user;
    }
}