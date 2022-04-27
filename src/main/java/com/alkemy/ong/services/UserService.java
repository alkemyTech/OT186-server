package com.alkemy.ong.services;
import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.entity.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;


public interface UserService {

    UserDetails loadUserByUsername(String email);
    User save(User user);
    UserDetails login(LoginRequestDto loginRequestDto) throws BadCredentialsException;
    void delete(UUID id);
}
