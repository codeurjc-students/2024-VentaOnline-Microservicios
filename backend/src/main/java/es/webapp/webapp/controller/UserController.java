package es.webapp.webapp.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.webapp.webapp.model.Direction;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ShoppingCartService;
import es.webapp.webapp.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    /*@ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){
        String username = (String) request.getSession(false).getAttribute("user");
        if(username != null){
            Optional<User> user = userService.findByUsername(username);
            model.addAttribute("username",user.get().getUsername());
            model.addAttribute("admin", user.get().getRol().equals("ADMIN"));
            model.addAttribute("user", user.get().getRol().equals("USER"));
            model.addAttribute("id", user.get().getId());
            model.addAttribute("logged",true);
        } else {
            model.addAttribute("username","anonymous");
            model.addAttribute("admin", "");
            model.addAttribute("user", "");
            model.addAttribute("id", 6);
            model.addAttribute("logged",false);
        }
    }*/
    
    @PostMapping("/new")
    public String user(Model model, User user, Direction address, MultipartFile imageField) throws IOException{

        if(imageField != null)
            user.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
 

        if(user.getPassword() != null && user.getPassword().equals(user.getPasswordConfirmation())){            
            user.setRol("USER");
            ShoppingCart cart = new ShoppingCart();
            shoppingCartService.save(cart);
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
    public String updateUser(Model model, HttpServletRequest request, @PathVariable Integer id, @RequestParam(required=false) String name,
                    @RequestParam(required=false) String email, @RequestParam(required=false) String street, MultipartFile imageField,
                    @RequestParam(required=false) Integer number, @RequestParam(required=false) Integer zipCode, @RequestParam(required=false) String city) throws IOException{

        
        //HttpSession session = request.getSession(false);
        //if(session != null){
        //    String usernameReddis = (String) session.getAttribute("user");
        //    if(usernameReddis != null){
        //        Optional<User> userReddis = userService.findByUsername(usernameReddis);
            Optional<User> userReddis = userService.findById(id);
                if(userReddis.isPresent()){

                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);

                    Direction address = new Direction();
                    address.setStreet(street);
                    address.setNumber(number);
                    address.setZipCode(zipCode);
                    address.setCity(city);
                
                    userService.update(id, user, address, imageField);

                    model.addAttribute("state_reg", "updated");

                    return "redirect://localhost:8442/store/my_profile";
                } else {
                    //model.addAttribute("state_reg", "user not found");
                    return "redirect://localhost:8442/store/logginerror";
                }
    //        }
    //        return "redirect:/loginerror";
    //    }
    //    return "redirect:/loginerror";
    }
}
