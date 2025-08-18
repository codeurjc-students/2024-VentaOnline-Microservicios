package es.webapp.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemToBuyService;
import es.webapp.webapp.service.OrderService;
import es.webapp.webapp.service.UserService;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    /*@ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("user");
        //model.addAttribute("username",username);
        if(username != null){
            Optional<User> user = userService.findByUsername(username);
            model.addAttribute("username",user.get().getUsername());
            model.addAttribute("admin", user.get().getRol().equals("ADMIN"));
            model.addAttribute("user", user.get().getRol().equals("USER"));
            model.addAttribute("id", user.get().getId());
            model.addAttribute("logged",true);
        } else {
            model.addAttribute("username","anonymous");
            model.addAttribute("logged",false);
        }
    }*/

    @GetMapping("/new/user")
    public String generate(Model model,HttpServletRequest request) throws IOException {

        String username = (String) request.getSession().getAttribute("user");
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()){
            orderService.buy(username);
            return "redirect://localhost:8442/store";
        }
        return "error";
    }

}
