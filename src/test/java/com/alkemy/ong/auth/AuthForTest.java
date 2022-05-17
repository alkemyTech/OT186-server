package com.alkemy.ong.auth;
import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.entity.Role;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthForTest {
    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected AuthenticationManager authenticationManager;
    protected HttpHeaders httpHeaders = new HttpHeaders();
    protected TestRestTemplate testRestTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;
    private final UUID uuid = UUID.randomUUID();
    protected String generateUriWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
    protected String getTokenForTest(String role) {
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByEmail("user@test")).thenReturn(fakeUser(role));
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("user@test");
        loginRequestDto.setPassword("123");
        HttpEntity<LoginRequestDto> httpEntity = new HttpEntity<>(loginRequestDto, httpHeaders);
        ResponseEntity<AuthenticationResponse> responseEntity =
                testRestTemplate.exchange(this.generateUriWithPort("/auth/login"), HttpMethod.POST, httpEntity, AuthenticationResponse.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity.getBody().getToken();
    }
    protected void putTokenInHeader(String roleName) {
        List<String> authHeader = new ArrayList<>();
        authHeader.add("Bearer " + getTokenForTest(roleName));
        httpHeaders.put("Authorization", authHeader);
    }
    private Role testRole(String roleName) {
        Role role = new Role();
        role.setId(uuid);
        role.setName(roleName);
        return role;
    }
    private User fakeUser(String role) {
        User user = new User();
        user.setId(uuid);
        user.setEmail("user@test");
        user.setPhoto("photo");
        user.setFirstName("user");
        user.setLastName("fake");
        user.setPassword("123");
        user.setRoles(testRole(role));
        return user;
    }

}
