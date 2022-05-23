package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.AuthForTest;
import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.auth.utils.JwtUtils;
import com.alkemy.ong.config.AmazonS3ClientConfig;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.Role;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.services.OrganizationService;
import com.alkemy.ong.services.SlideService;
import com.alkemy.ong.services.imp.AmazonServiceImpl;
import com.alkemy.ong.services.imp.UserServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserAuthControllerTest extends AuthForTest {

    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UserServiceImp userService;
    @MockBean
    private SlideService slideService;
    @MockBean
    private OrganizationService service;
    @MockBean
    private AmazonServiceImpl amazonService;
    @MockBean
    private AmazonS3ClientConfig amazonS3ClientConfig;
    private final UUID ID_USER = UUID.fromString("f55d8214-81c9-4e06-89d1-90b5c828b4ad");

    private Role aRole(){
        Role role = new Role();
        role.setId(UUID.randomUUID());
        role.setName("ROLE_USER");
        return role;
    }

    private User aUser(){
        User user = new User();
        user.setId(ID_USER);
        user.setEmail("user@gmail.com");
        user.setRoles(aRole());
        user.setPassword("pass");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setSoftDelete(Boolean.FALSE);
        return user;
    }

    private UserDTO aUserDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID_USER);
        userDTO.setEmail("user@gmail.com");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setRoles(aRole());
        return userDTO;
    }

    @Test
    void registerHappyPath(){

        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user@gmail.com",
                "pass",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        when(userRepository.findByEmail(any(String.class))).thenReturn(null);
        when(userRepository.save(aUser())).thenReturn(aUser());
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userService.loadUserByUsername(any(String.class))).thenReturn(userDetails);

        HttpEntity<User> httpEntity = new HttpEntity<>(aUser(), httpHeaders);

        ResponseEntity<AuthenticationResponse> response = testRestTemplate.exchange(
                generateUriWithPort("/auth/register"),
                HttpMethod.POST,
                httpEntity,
                AuthenticationResponse.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void loginHappyPath() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("user@gmail.com");
        loginRequestDto.setPassword("pass");
        when(userRepository.findByEmail(any())).thenReturn(aUser());
        when(jwtUtils.generateToken(any())).thenReturn("this_is_a_valid_token");
        HttpEntity<LoginRequestDto> httpEntity = new HttpEntity<>(loginRequestDto, httpHeaders);


        ResponseEntity<AuthenticationResponse> response = testRestTemplate.exchange(
                generateUriWithPort("/auth/login"),
                HttpMethod.POST,
                httpEntity,
                AuthenticationResponse.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("this_is_a_valid_token", response.getBody().getToken());
    }

    @Test
    void loginBadCredentials401() {

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("notUser@gmail.com");
        loginRequestDto.setPassword("notPass");
        when(userService.login(any())).thenThrow(BadCredentialsException.class);
        HttpEntity<LoginRequestDto> httpEntity = new HttpEntity<>(loginRequestDto, httpHeaders);

        ResponseEntity<AuthenticationResponse> response = testRestTemplate.exchange(
                generateUriWithPort("/auth/login"),
                HttpMethod.POST,
                httpEntity,
                AuthenticationResponse.class);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

}