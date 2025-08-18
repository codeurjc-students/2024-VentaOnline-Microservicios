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
        HttpSession session = request.getSession(false);
        if(session != null) {   
            String username = (String) session.getAttribute("user");
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
        } else {
            model.addAttribute("username","anonymous");
            model.addAttribute("admin", "");
            model.addAttribute("user", "");
            model.addAttribute("id", 6);
            model.addAttribute("logged",false);
        }
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){   
        return "index";
    }

    @GetMapping("/my_profile")
    public String getProfile(Model model, HttpServletRequest request){
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

    @PostMapping("/signin")
    public String processLogin(Model model,
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request) {

        User user = userService.authenticate(username, password);

        if (user != null) {
            HttpSession session = request.getSession(true); // <- Redis guarda esto si está configurado
            session.setAttribute("user", user.getUsername());
            
            String usernameLogged = (String) session.getAttribute("user");
         
            if (username != null) {
                Optional<User> userLogged = userService.findByUsername(usernameLogged);
                model.addAttribute("username",userLogged.get().getUsername());
                model.addAttribute("admin", userLogged.get().getRol().equals("ADMIN"));
                model.addAttribute("user", userLogged.get().getRol().equals("USER"));
                model.addAttribute("id", userLogged.get().getId());
                model.addAttribute("logged",true);   
                return "index";
            }
        }
        return "error";
    }

    
    @GetMapping("/signout")
    public String logout(Model model, HttpServletRequest request) { 
        request.getSession().removeAttribute("user");
        
        model.addAttribute("username","anonymous");
        model.addAttribute("admin", "");
        model.addAttribute("user", "");
        model.addAttribute("id", 6);
        model.addAttribute("logged",false);
        
        return "index";
    }


    @GetMapping("/loginerror")
    public String loginerror(HttpServletRequest request){
        return "error";
    }
}
