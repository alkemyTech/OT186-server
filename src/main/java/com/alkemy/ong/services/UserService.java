package com.alkemy.ong.services;

import javax.security.auth.login.FailedLoginException;

import com.alkemy.ong.dto.LoginRequestDto;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.exception.EmailAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

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

    public User save(User user)
            throws EmailAlreadyExistException {
         if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new EmailAlreadyExistException();
            }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }
}
