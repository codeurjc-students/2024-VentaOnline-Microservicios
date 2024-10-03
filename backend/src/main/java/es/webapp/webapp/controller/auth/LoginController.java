package es.webapp.webapp.controller.auth;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.webapp.webapp.data.AuthResponse;
import es.webapp.webapp.repository.UserRepo;
import es.webapp.webapp.security.JWTGenerator;

//import  es.webapp.webapp.security.jwt.AuthResponse;
//import  es.webapp.webapp.security.jwt.LoginRequest;
//import  es.webapp.webapp.security.jwt.UserLoginService;
//import  es.webapp.webapp.security.jwt.AuthResponse.Status;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	private AuthenticationManager authenticationManager;
	private UserRepo userRepo;
	private PasswordEncoder passwordEncoder;
	private JWTGenerator jwtGenerator;

	@Autowired
	public LoginController(AuthenticationManager authenticationManager, UserRepo userRepo,
						PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator){
		this.authenticationManager = authenticationManager;
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;		
		this.jwtGenerator = jwtGenerator;				
	}

	

	/*@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(
			@CookieValue(name = "refreshToken", required = false) String refreshToken) {

		return userService.refresh(refreshToken);
	}

	@PostMapping("/logout")
	public ResponseEntity<AuthResponse> logout(HttpServletRequest request, HttpServletResponse response) {

		return ResponseEntity.ok(new AuthResponse(Status.SUCCESS, userService.logout(request, response)));
	}*/
}