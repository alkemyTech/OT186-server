package com.alkemy.ong.services;

import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.login.FailedLoginException;

public interface UserService {

    User login(LoginRequestDto loginRequestDto) throws FailedLoginException;

    UserDetails loadUserByUsername(String email);
}
