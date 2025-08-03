package es.webapp.webapp.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.webapp.webapp.model.User;
import es.webapp.webapp.service.UserService;

@Controller
public class Home {

    @Autowired
    private UserService userService; 
    

    @ModelAttribute
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
    }

    @GetMapping("/")
    public String home(Model model){
        return "index";
    }

    @GetMapping("/my_profile")
    public String getProfile(Model model){
        model.addAttribute("state_reg", "");
        return "my_profile";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("state_reg","");
        return "signup";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request) {

        User user = userService.authenticate(username, password);

        if (user != null) {
            HttpSession session = request.getSession(); // <- Redis guarda esto si está configurado
            session.setAttribute("user", user.getUsername());
            //session.setAttribute("role", user.getRol());
            return "index";
        } else {
            return "error";
        }
    }


    @GetMapping("/signout")
    public String logout(Model model, HttpServletRequest request) { 
        HttpSession session = request.getSession(false); // false = no crear si no existe
        if (session != null) {
            session.invalidate(); // Esto borra también de Redis si estás usando Spring Session
            return "index";
        } 
        return "error";
    }

    @GetMapping("/loginerror")
    public String loginerror(){
        return "error";
    }
}
