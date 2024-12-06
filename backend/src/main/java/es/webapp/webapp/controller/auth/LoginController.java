package es.webapp.webapp.controller.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.webapp.webapp.data.AuthResponse;
import es.webapp.webapp.data.Status;
import es.webapp.webapp.model.User;
import es.webapp.webapp.repository.UserRepo;
import es.webapp.webapp.security.JWTGenerator;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	private AuthenticationManager authenticationManager;
	private JWTGenerator jwtGenerator;

	public LoginController(AuthenticationManager authenticationManager, UserRepo userRepo,
						PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator){
		this.authenticationManager = authenticationManager;	
		this.jwtGenerator = jwtGenerator;				
	}

	private SecurityContextRepository securityContextRepository =
        new HttpSessionSecurityContextRepository(); 

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(HttpServletRequest request, HttpServletResponse response, @RequestBody User user){

		//authenticate the user
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		SecurityContext securitycontext = SecurityContextHolder.getContext();
		securitycontext.setAuthentication(authentication);

		String token = jwtGenerator.generateToken(authentication);

		securityContextRepository.saveContext(securitycontext, request, response);
		
		return new ResponseEntity<>(new AuthResponse(token),HttpStatus.OK);
	
	}

	@PostMapping("/logout")
	public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				cookie.setMaxAge(0);
				cookie.setValue("");
				cookie.setHttpOnly(true);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		return ResponseEntity.ok(new AuthResponse(Status.SUCCESS,"logout successfully"));
	}
}