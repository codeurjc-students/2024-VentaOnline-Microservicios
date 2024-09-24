package es.webapp.webapp.service;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.Direction;
import es.webapp.webapp.model.User;
import es.webapp.webapp.repository.UserRepo;

@Service
public class DataBaseInitializer {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;
    
    @PostConstruct
    public void init() throws IOException, URISyntaxException {

        //1:1 bidirectional relationship
        
        User user1 = new User();
        user1.setName("Ana");
        user1.setEmail("ana1@gmail.com");
        user1.setEncodedPassword(passwordEncoder.encode("1ana23"));
        user1.setRol("USER");

        user1.setDirection(new Direction("mullhenbergstrasse", 21, 38303, "otze"));
        userRepo.save(user1);
    }
}
