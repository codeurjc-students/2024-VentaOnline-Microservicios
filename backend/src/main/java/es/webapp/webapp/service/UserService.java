package es.webapp.webapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.model.Order;
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
        user.setPasswordConfirmation(passwordEncoder.encode(user.getPasswordConfirmation()));
        user.setRol("USER");
        return userRepo.save(user);
    }

    public Optional<User> findById(Integer id){
        return userRepo.findById(id);
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public Optional<User> findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public Optional<User> findByName(String name){
        return userRepo.findByName(name);
    }


    public Optional<User> findByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public void setPassword(Integer id, User newUser){
        Optional<User> user = userRepo.findById(id);
        newUser.setPassword(user.get().getPassword());
        newUser.setPasswordConfirmation(user.get().getPasswordConfirmation());
        
    }
    public void updatePassword(User newUser){
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setPasswordConfirmation(passwordEncoder.encode(newUser.getPasswordConfirmation()));
        
    }

    /*public void update(Integer id, User newUser){
        Optional<User> user = userRepo.findById(id);
        if(newUser.getName() == null){
            newUser.setName(user.get().getName());
        }
        if(newUser.getUsername() == null){
            newUser.setUsername(user.get().getUsername());
        }
        if(newUser.getEmail() == null){
            newUser.setEmail(user.get().getEmail());
        }
        
        newUser.setRol("USER");
        newUser.setDirection(user.get().getDirection());
        newUser.setShoppingCart(user.get().getShoppingCart());
        for(Item item: user.get().getFavouritesItems()){
            newUser.getFavouritesItems().add(item);
        }
        for(Order order: user.get().getOrders()){
            newUser.getOrders().add(order);
        }
        newUser.setId(id);
        userRepo.save(newUser);
    }*/

    /*public void newItem(Integer id, Item newItem){
        User newUser = new User();
        Optional<User> user = userRepo.findById(id);
        newUser.setName(user.get().getName());
        newUser.setUsername(user.get().getUsername());
        newUser.setEmail(user.get().getEmail());
        newUser.setPassword(user.get().getPassword());
        newUser.setPasswordConfirmation(user.get().getPasswordConfirmation());
        newUser.setRol("USER");
        newUser.setDirection(user.get().getDirection());
        newUser.setShoppingCart(user.get().getShoppingCart());
        if(!user.get().getFavouritesItems().contains(newItem)){
            newUser.getFavouritesItems().add(newItem);
        }
        for(Item item: user.get().getFavouritesItems()){
            newUser.getFavouritesItems().add(item);
        }
        for(Order order: user.get().getOrders()){
            newUser.getOrders().add(order);
        }
        newUser.setId(id);
        userRepo.save(newUser);
    }*/

}
