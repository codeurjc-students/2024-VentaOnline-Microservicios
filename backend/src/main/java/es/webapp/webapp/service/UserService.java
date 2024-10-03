package es.webapp.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.User;
import es.webapp.webapp.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User add(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setPasswordConfirmation(passwordEncoder.encode(user.getPasswordConfirmation()));
        return userRepo.save(user);
    }

    public Optional<User> findById(Integer id){
        return userRepo.findById(id);
    }

    public Optional<User> findByUsername(String name){
        return userRepo.findByUsername(name);
    }

    public Optional<User> findByEmail(String email){
        return userRepo.findByEmail(email);
    }
    
}
