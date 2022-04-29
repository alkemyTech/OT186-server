package com.alkemy.ong.services;
<<<<<<< HEAD

import com.alkemy.ong.dto.LoginRequestDto;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
=======
import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;
import org.springframework.security.authentication.BadCredentialsException;
>>>>>>> origin/main
import org.springframework.security.core.userdetails.UserDetails;

<<<<<<< HEAD
import javax.security.auth.login.FailedLoginException;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
=======
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
>>>>>>> origin/main


public interface UserService {

<<<<<<< HEAD

    public User login(LoginRequestDto loginRequestDto) throws FailedLoginException {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user == null)
            throw new UsernameNotFoundException("User Not Found");

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
            throw new FailedLoginException("Email and password don't match.");

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }
=======
    UserDetails loadUserByUsername(String email);
    User save(User user);
    UserDetails login(LoginRequestDto loginRequestDto) throws BadCredentialsException;
    Boolean validateRole(UUID id, HttpServletRequest req);
    void delete(UUID id);
    List<UserDTO> getAll();
>>>>>>> origin/main
}
