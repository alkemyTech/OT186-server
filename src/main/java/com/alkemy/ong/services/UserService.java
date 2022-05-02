package com.alkemy.ong.services;
import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.exception.EmailAlreadyExistException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;


public interface UserService {

    UserDetails loadUserByUsername(String email);
    UserDTO save(UserDTO userDTO) throws EmailAlreadyExistException;
    UserDetails login(LoginRequestDto loginRequestDto) throws BadCredentialsException;
    Boolean validateRole(UUID id, HttpServletRequest req);
    void delete(UUID id);
    List<UserDTO> getAll();
    UserDTO findBy(String username) throws UsernameNotFoundException;

}
