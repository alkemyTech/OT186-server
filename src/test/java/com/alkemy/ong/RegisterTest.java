package com.alkemy.ong;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.alkemy.ong.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class RegisterTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
     void createUserTest() {
        User us = new User();
        us.setFirstName("Alkemy2");
        us.setLastName("Test2");
        us.setEmail("AlkemyTest2@Alkemy.org");
        us.setPassword(passwordEncoder.encode("AlkemyPassword2"));
        us.setPhoto("fotito2");
        User retorno = userRepository.save(us);
        assertEquals(retorno.getPassword(), us.getPassword());

    }
}