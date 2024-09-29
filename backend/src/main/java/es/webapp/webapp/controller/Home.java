package es.webapp.webapp.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
            Optional<User> user = userService.findByName(name);
            model.addAttribute("username",user.get().getUsername());
            model.addAttribute("logged",true);
        } else {
            model.addAttribute("logged",false);
        }
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){
        //CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        //model.addAttribute("token", token.getToken());
        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("state_reg","");
        model.addAttribute("state_log","");
        model.addAttribute("size","");
        return "login";
    }
    
}
