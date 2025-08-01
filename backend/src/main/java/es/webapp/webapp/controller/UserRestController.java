package es.webapp.webapp.controller;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.webapp.webapp.model.Direction;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.UserService;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> addUser(@RequestBody User newUser, @RequestBody Direction newAddress, @RequestBody ShoppingCart cart){
        newUser.setDirection(newAddress);
        newUser.setShoppingCart(cart);
        User user = userService.add(newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.findAll();
    }

    @GetMapping("/users/current")
    public ResponseEntity<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return null;
        } 
    }

    /*@PutMapping("/databases/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User userUpdated, @PathVariable Integer id) throws IOException{
        Optional<User> user = userService.findById(id);
        if(user.isPresent()){
            userUpdated.setImageFile(user.get().getImageFile());
            if(userUpdated.getPassword() != null && userUpdated.getPasswordConfirmation() != null && userUpdated.getPassword().equals(userUpdated.getPasswordConfirmation())){
                userService.updatePassword(userUpdated);
            } else {
                userService.setPassword(id, userUpdated);
            }
            userService.update(id,userUpdated);
            return new ResponseEntity<>(userUpdated, HttpStatus.OK);        
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserByUsername(@PathVariable Integer id){
        Optional<User> user = userService.findById(id);
        if(user.isPresent()) {
            return new ResponseEntity<>(user.get(),HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*@GetMapping("/users/{username}")
    public ResponseEntity<User> getUserByUsernameAPI(@PathVariable String username){
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    @PostMapping("/users/{id}/image")
    public ResponseEntity<User> addUserImage(@PathVariable Integer id, @RequestParam MultipartFile avatar) throws IOException{
        
        Optional<User> user = userService.findById(id);

        URI location = fromCurrentRequest().build().toUri();
        
        user.get().setImageFile(BlobProxy.generateProxy(avatar.getInputStream(), avatar.getSize()));

        
        //userService.setPassword(id,user.get());
        userService.updateImage(user.get());

        if(user.isPresent()){
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users/{id}/update")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User newUser, Direction address) throws IOException{
        
        Optional<User> user = userService.findById(id);

        if(user.isPresent()){

            //newUser.setDirection(user.get().getDirection());
            //newUser.setId(user.get().getId());
            userService.update(user.get(), newUser, address);

            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/users/{id}/update/address")
    public ResponseEntity<User> updateUserAddresse(@PathVariable Integer id, @RequestBody Direction address) throws IOException{
        
        Optional<User> user = userService.findById(id);

        if(user.isPresent()){

            //address.setId(user.get().getDirection().getId());
            userService.update(user.get(), user.get(), address);

            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/{id}/image")
    public ResponseEntity<Object> getUserImageById(@PathVariable Integer id) throws SQLException{

        Optional<User> user = userService.findById(id);

        if(user.isPresent() && user.get().getImageFile() != null){
            Resource file = new InputStreamResource(user.get().getImageFile().getBinaryStream());
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE,"image/jpeg")
                .contentLength(user.get().getImageFile().length())
                .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }   
}
