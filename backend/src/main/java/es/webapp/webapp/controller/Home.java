package es.webapp.webapp.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.webapp.webapp.model.LoginRequest;
import es.webapp.webapp.model.User;
import es.webapp.webapp.security.JWTGenerator;
import es.webapp.webapp.service.UserService;

@Controller
public class Home {

    @Autowired
    private UserService userService; 

    private AuthenticationManager authenticationManager;
	private JWTGenerator jwtGenerator;

    @Autowired
	private UserDetailsService userDetailsService;

    public Home(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator){
		this.authenticationManager = authenticationManager;	
		this.jwtGenerator = jwtGenerator;				
	}

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

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){ 
          
        return "index";
    }

    @GetMapping("/my_profile")
    public String getProfile(Model model, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("state_reg", "");
        addAttributes(model, request, userDetails);
        return "my_profile";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("state_reg","");
        return "signup";
    }

    private void addAccessTokenCookie(HttpHeaders httpHeaders, String token) {
		httpHeaders.add(HttpHeaders.SET_COOKIE,token);
        
	}

    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository(); 

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @PostMapping("/signin")
    public String processLogin(Model model, HttpServletRequest request, HttpServletResponse response, @RequestBody User user){

		HttpHeaders responseHeaders = new HttpHeaders();
		//authenticate the user
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		SecurityContext securitycontext = SecurityContextHolder.getContext();
		securitycontext.setAuthentication(authentication);

        String username = user.getUsername();
		UserDetails userDetail = userDetailsService.loadUserByUsername(username);

		String token = jwtGenerator.generateToken(userDetail);

        addAccessTokenCookie(responseHeaders, token);
        request.getSession(true);
		securityContextRepository.saveContext(securitycontext, request, response);

       // addAttributes(model, request, userDetail);

       return "login";
    }

    
    @RequestMapping("/signout")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response) { 
        // Limpiar contexto y sesión para usuarios web
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Borrar cookies
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookie.setMaxAge(0);
                cookie.setValue("");
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        addAttributes(model, request, null);
        return "index";
    }


    @RequestMapping("/loginerror")
    public String loginerror(HttpServletRequest request){
        return "error";
    }
}
