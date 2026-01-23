package es.webapp.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.webapp.webapp.model.User;
import es.webapp.webapp.service.OrderService;
import es.webapp.webapp.service.UserService;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {

        Principal principal = request.getUserPrincipal();
        
        if(principal != null) {
            model.addAttribute("logged", true);
            model.addAttribute("username", principal.getName());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            model.addAttribute("user", request.isUserInRole("USER"));
            Optional<User> userSession = userService.findByUsername(userDetails.getUsername());
            model.addAttribute("id", userSession.get().getId());
        } else {
            model.addAttribute("logged", false);
        }
    }

    @GetMapping("/user")
    public String buy(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        
        Optional<User> user = userService.findByUsername(userDetails.getUsername());

        if(user.isPresent()) {
            orderService.buy(user.get().getUsername());
            model.addAttribute("neworder",true);
            return "shoppingCart";
        } else
            return "error";
    }

    @GetMapping("/new/user")
    public String generate(Model model,HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        Optional<User> user = userService.findByUsername(userDetails.getUsername());
        if(user.isPresent()){
            orderService.buy(userDetails.getUsername());
            return "index";
        }
        return "error";
    }

}
