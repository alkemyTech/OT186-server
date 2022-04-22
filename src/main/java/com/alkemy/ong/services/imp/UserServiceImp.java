package com.alkemy.ong.services.imp;

import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.exception.LoginFailedException;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.Collections;

@Service
public class UserServiceImp implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    @Lazy
    UserServiceImp(UserRepository userRepository, final PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public User login(LoginRequestDto loginRequestDto) throws LoginFailedException {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user == null)
            throw new UsernameNotFoundException("User Not Found");

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
            throw new LoginFailedException("Email and password don't match.");

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Email not found.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRoles().getName())));
    }
}
