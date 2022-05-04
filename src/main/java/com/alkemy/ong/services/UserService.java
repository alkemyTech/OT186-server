package com.alkemy.ong.services;
import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDetails loadUserByUsername(String email);
    ResponseEntity<AuthenticationResponse> save(User user) throws Exception;
    UserDetails login(LoginRequestDto loginRequestDto) throws BadCredentialsException;
    Boolean validateRole(UUID id, HttpServletRequest req);
    void delete(UUID id);
    List<UserDTO> getAll();
    UserDTO findBy(String username) throws UsernameNotFoundException;
    UserDTO update(UUID id, UserDTO userDTO);

}
