package com.alkemy.ong.services;
import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.exception.LoginFailedException;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService {

    UserDetails loadUserByUsername(String email);
    User save(User user);
    User login(LoginRequestDto loginRequestDto) throws LoginFailedException;
}
