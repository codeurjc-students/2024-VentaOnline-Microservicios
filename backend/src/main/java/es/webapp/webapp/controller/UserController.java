package es.webapp.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.webapp.webapp.model.Direction;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if(principal != null){
            String name = principal.getName();
            Optional<User> user = userService.findByUsername(name);
            model.addAttribute("username",user.get().getUsername());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            model.addAttribute("user", request.isUserInRole("USER"));
            model.addAttribute("logged",true);
        } else {
            model.addAttribute("logged",false);
        }
    }
    
    @PostMapping("/new")
    public String newUser(Model model, User user, Direction address, MultipartFile imageField, @RequestParam String passwordConfirmation) throws IOException{

        Optional<User> username = userService.findByUsername(user.getUsername());
        Optional<User> email = userService.findByEmail(user.getEmail());

        if(imageField.isEmpty()){
            Optional<User> anonymous = userService.findById(39);
            if(anonymous.isPresent()) {
                user.setImageFile(anonymous.get().getImageFile());
            }
        }else{
            user.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
        }    

        if(user.getUsername() != null && user.getEmail() != null && user.getPassword() != null && 
            user.getPassword().equals(passwordConfirmation) && address.getStreet() != null &&
            address.getNumber() != null && address.getZipCode() != null && address.getCity() != null){
            if(passwordConfirmation.length() < 6 && passwordConfirmation.length() > 10){
                model.addAttribute("size", "password must contains between 6 and 10 characters");
            } else {
                model.addAttribute("size", "");
            } 
            if(address.getZipCode().toString().length() != 5){
                model.addAttribute("zip_code", "zip code must contains 5 characters");
            } else {
                model.addAttribute("zip_code", "");
            } 
            if(username.isPresent() || email.isPresent()){
                model.addAttribute("state_reg", "there is already an user with this email or name");
            } else {              
                user.setRol("USER");
                user.setDirection(address);
                userService.add(user);
                model.addAttribute("state_reg", "user registered");
            }
        }else{
            model.addAttribute("state_reg", "some mnadatory fields are empty or incorrect");
        }
        model.addAttribute("state_log", "");

        return "signup";
    }

}
