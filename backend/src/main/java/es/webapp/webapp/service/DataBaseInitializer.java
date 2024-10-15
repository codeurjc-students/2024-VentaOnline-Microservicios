package es.webapp.webapp.service;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

        User user2 = new User();
        user2.setUsername("carlos");
        user2.setName("carlos");
        user2.setEmail("carlos@hotmail.es");
        user2.setPassword(passwordEncoder.encode("user02"));
        user2.setPasswordConfirmation(passwordEncoder.encode("user02"));
        user2.setRol("USER");
        setUserImage(user2, "/images/ava1.jpg");

        Direction address2 = new Direction("Calle de la constitución",1,28933,"Madrid");
        user2.setDirection(address2);
        userRepo.save(user2);
    }

    public void setUserImage(User user, String ClasspathResource)throws IOException{
		Resource image = new ClassPathResource(ClasspathResource);
		user.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
	}
}
