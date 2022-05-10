package com.alkemy.ong.services.imp;

import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginRequestDto;
import com.alkemy.ong.auth.utils.JwtUtils;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.services.EmailService;
import com.alkemy.ong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImp implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired @Lazy
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    public ResponseEntity<AuthenticationResponse> save(User user)  throws Exception {
        User findUser = userRepository.findByEmail(user.getEmail());
        if(findUser !=null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse("Email is Used, Change Email"));
        }
        String oldPassword = user.getPassword();
        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
        authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getEmail(), oldPassword));
        final UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        final String jwt = jwtUtils.generateToken(userDetails);
        emailService.sendWelcomeEmail(user.getEmail());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
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

    public Boolean validateRole(UUID id, HttpServletRequest req){

        String token = req.getHeader("Authorization").replace("Bearer ", "");
        String email = jwtUtils.extractUsername(token);
        UserDetails userDetails = this.loadUserByUsername(email);

        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return true;
        }else{
            User user = userRepository.findByEmail(email);

            if (id.compareTo(user.getId()) == 0)
                return true;
            else
                return false;
        }
    }

    public List<UserDTO> getAll() {
        List<User> entities = this.userRepository.findAll();
        List<UserDTO> result = this.userMapper.userEntityList2DTOList(entities);
        return result;
    }

    public void delete(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User not found.");
        }
    }
    @Override
    public UserDTO findBy(String username) throws UsernameNotFoundException {
        return userMapper.userEntity2DTO(getUser(username));
    }

    private User getUser(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return user;
    }

    public UserDTO update(UUID id, UserDTO userDTO) {
        Optional<User> entity = this.userRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFound("Invalid User ID");
        }

        this.userMapper.userEntityRefreshValues(entity.get(),userDTO);
        User entitySaved = this.userRepository.save(entity.get());
        UserDTO result = this.userMapper.userEntity2DTO(entitySaved);
        return result;
    }
}
