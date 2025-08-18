package es.webapp.webapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.webapp.webapp.model.Direction;
import es.webapp.webapp.model.User;
import es.webapp.webapp.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public UserService(RedisTemplate<String, Object> redisTemplate, PasswordEncoder passwordEncoder) {
        this.redisTemplate = redisTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String username, String rawPassword) {
        Object obj = redisTemplate.opsForValue().get("user:" + username);
        User user = objectMapper.convertValue(obj, User.class);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User add(User user){
        if(user.getPassword().equals(user.getPasswordConfirmation())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setPasswordConfirmation(passwordEncoder.encode(user.getPasswordConfirmation()));
            user.setRol("USER");

            User userReddis = new User();
            userReddis.setUsername(user.getUsername());
            userReddis.setPassword(user.getPassword());

            redisTemplate.opsForValue().set("user:" + user.getUsername(), userReddis);
            return userRepo.save(user);
        } else
            return null;
    }

    public void updateImage(User user){
        userRepo.save(user);
    }

    public void update(Integer id, User newUser, Direction address, MultipartFile imageField) throws IOException{
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            if(!imageField.isEmpty()){
                user.get().setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
            }if(newUser != null && !newUser.getName().isEmpty()){
                user.get().setName(newUser.getName());
            }
            if(newUser != null && !newUser.getEmail().isEmpty()){
                user.get().setEmail(newUser.getEmail());
            }
            if(address != null && address.getNumber() != null){
                user.get().getDirection().setNumber(address.getNumber());
            }
            if(address != null && !address.getStreet().isEmpty()){
                user.get().getDirection().setStreet(address.getStreet());
            }
            if(address != null && address.getZipCode() != null){
                user.get().getDirection().setZipCode(address.getZipCode());
            }
            if(address != null && !address.getCity().isEmpty()){
                user.get().getDirection().setCity(address.getCity());
            }
            //user.get().setId(user.get().getId());

            //User userReddis = new User();
            //userReddis.setUsername(user.get().getUsername());
            //userReddis.setPassword(user.get().getPassword());
//
            //redisTemplate.opsForValue().set("user:" + user.get().getUsername(), userReddis);
            userRepo.save(user.get());
        }
    }

    /*public void updateById(Integer id, User newUser){
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            if(!newUser.getName().isEmpty()){
                user.get().setName(newUser.getName());
            }else{
                user.get().setName(user.get().getName());
            }
            if(!newUser.getUsername().isEmpty()){
                user.get().setUsername(newUser.getUsername());
            }else{
                user.get().setUsername(user.get().getUsername());
            }
            if(!newUser.getEmail().isEmpty()){
                user.get().setEmail(newUser.getEmail());
            }else{
                user.get().setEmail(user.get().getEmail());
            }
            if(!newUser.getPassword().isEmpty() && newUser.getPassword().equals(newUser.getPasswordConfirmation())){
                user.get().setPassword(passwordEncoder.encode(newUser.getPassword()));
                user.get().setPasswordConfirmation(passwordEncoder.encode(newUser.getPasswordConfirmation()));
            }else{
                user.get().setPassword(user.get().getPassword());
                user.get().setPasswordConfirmation(user.get().getPasswordConfirmation());
            }
            user.get().setId(id);
            userRepo.save(user.get());
        }
    }

    public void updateAddressById(Integer id, Direction address){
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            if(address.getNumber() != null){
                user.get().getDirection().setNumber(address.getNumber());
            }

            if(!address.getStreet().isEmpty()){
                user.get().getDirection().setStreet(address.getStreet());
            }else{
                user.get().setDirection(user.get().getDirection());
            }

            if( address.getZipCode() != null){
                user.get().getDirection().setZipCode(address.getZipCode());
            }
            if(!address.getCity().isEmpty()){
                user.get().getDirection().setCity(address.getCity());
            } else {
                user.get().getDirection().setCity(user.get().getDirection().getCity());
            }
            user.get().setId(id);
            userRepo.save(user.get());
        }
    }*/

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
        if(newUser != null && newUser.getName() != null){
            newUser.setName(user.get().getName());
        }
        if(newUser != null && newUser.getUsername() != null){
            newUser.setUsername(user.get().getUsername());
        }
        if(newUser != null && newUser.getEmail() != null){
            newUser.setEmail(user.get().getEmail());
        }
        
        newUser.setRol("USER");
        newUser.setDirection(user.get().getDirection());
        newUser.setShoppingCart(user.get().getShoppingCart());
        foraddress != null && (Item item: user.get().getFavouritesItems()){
            newUser.getFavouritesItems().add(item);
        }
        foraddress != null && (Order order: user.get().getOrders()){
            newUser.getOrders().add(order);
        }
        newaddress != null && User.setId(id);
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
