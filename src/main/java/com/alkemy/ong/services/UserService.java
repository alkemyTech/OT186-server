package com.alkemy.ong.services;

import com.alkemy.ong.entities.LoginRequestDto;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    @Lazy
    UserService(UserRepository userRepository, final PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User login(LoginRequestDto loginRequestDto) throws FailedLoginException {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user == null)
            throw new UsernameNotFoundException("User Not Found");

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
            throw new FailedLoginException("Email and password don't match.");

        return user;
    }

}
