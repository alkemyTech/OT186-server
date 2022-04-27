package com.alkemy.ong.services.imp;

import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImp implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired @Lazy
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserMapper userMapper;


    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public UserDetails login(LoginRequestDto loginRequestDto) throws BadCredentialsException {

        UserDetails userDetails;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
            );
            User user = userRepository.findByEmail(loginRequestDto.getEmail());
            userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRoles().getName())));
        } catch (BadCredentialsException e) {
            throw e;
        }
        return userDetails;
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


    public List<UserDTO> getAll() {
        List<User> entities = this.userRepository.findAll();
        List<UserDTO> result = this.userMapper.userEntityList2DTOList(entities);
        return result;
    }
}
