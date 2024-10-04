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
        user1.setUsername("administrator1");
        user1.setEmail("administrator1@gmail.com");
        user1.setPassword(passwordEncoder.encode("admin123"));
        user1.setPasswordConfirmation(passwordEncoder.encode("admin123"));
        user1.setRol("ADMIN");

        Direction address = new Direction("Calle Roma",2,85503,"Almería");
        user1.setDirection(address);
        userRepo.save(user1);
    }
}
