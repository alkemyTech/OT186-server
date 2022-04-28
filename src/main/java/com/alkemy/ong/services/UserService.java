package com.alkemy.ong.services;
import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.exception.EmailAlreadyExistException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


public interface UserService {

    UserDetails loadUserByUsername(String email);
    User save(User user) throws EmailAlreadyExistException;
    UserDetails login(LoginRequestDto loginRequestDto) throws BadCredentialsException;
    Boolean validateRole(UUID id, HttpServletRequest req);
    UserDto findBy(String username) throws UsernameNotFoundException;
}
