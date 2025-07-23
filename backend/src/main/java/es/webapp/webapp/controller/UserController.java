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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.webapp.webapp.model.Direction;
import es.webapp.webapp.model.ShoppingCart;
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
    public String user(Model model, User user, Direction address, MultipartFile imageField) throws IOException{

        if(imageField != null)
            user.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
 

        if(user.getPassword() != null && user.getPassword().equals(user.getPasswordConfirmation())){            
            user.setRol("USER");
            ShoppingCart cart = new ShoppingCart();
            user.setDirection(address);
            user.setShoppingCart(cart);
            userService.add(user);
            model.addAttribute("state_reg", "user registered");
         
        }else{
            model.addAttribute("state_reg", "password is out of range [5-10] or not correspond with the confirmation");
        }

        return "signup";
    }

    @PostMapping("/update/{id}")
    public String updateUser(Model model, @PathVariable Integer id, @RequestParam(required=false) String name, @RequestParam(required=false) String username,
                    @RequestParam(required=false) String email, @RequestParam(required=false) String password, @RequestParam(required=false) String street,
                    @RequestParam(required=false) Integer number, @RequestParam(required=false) Integer zipCode, @RequestParam(required=false) String city){

            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);

            userService.updateById(id, user);

            Direction address = new Direction();
            address.setStreet(street);
            address.setNumber(number);
            address.setZipCode(zipCode);
            address.setCity(city);
        
            userService.updateAddressById(id, address);

            model.addAttribute("state_reg", "updated");

        return "my_profile";
    }
}
