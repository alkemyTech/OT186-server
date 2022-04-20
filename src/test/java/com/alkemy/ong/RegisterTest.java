package com.alkemy.ong;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.alkemy.ong.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.alkemy.ong.repository.UserRepository;

public class RegisterTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    public void createUserTest() {
        User us = new User();
        us.setFirstName("AlkemyTest");
        us.setPassword(encoder.encode("AlkemyPassword"));
        us.setEmail("AlkemyTest@Alkemy.org");
        User retorno = userRepository.save(us);

        assertEquals(retorno.getPassword(), us.getPassword());

    }
}