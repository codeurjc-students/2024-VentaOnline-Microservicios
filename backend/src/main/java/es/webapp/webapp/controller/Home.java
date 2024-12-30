package es.webapp.webapp.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import es.webapp.webapp.model.User;
import es.webapp.webapp.service.UserService;

@Controller
public class Home {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if(principal != null){
            String name = principal.getName();
            Optional<User> user = userService.findByUsername(name);
            model.addAttribute("username",user.get().getUsername());
            model.addAttribute("id",user.get().getId());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            model.addAttribute("user", request.isUserInRole("USER"));
            model.addAttribute("logged",true);
        } else {
            model.addAttribute("username","anonymous");
            model.addAttribute("logged",false);
        }
        model.addAttribute("status","");
    }

    @GetMapping("/")
    public String home(Model model){
        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("state_reg","");
        model.addAttribute("state_log","");
        model.addAttribute("size","");
        model.addAttribute("zip_code","");
        return "signup";
    }

    @RequestMapping("/login")
    public String login(Model model){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(Model model) { 
        return "index";
    }

    @RequestMapping("/loginerror")
    public String loginerror(){
        return "error";
    }
}
