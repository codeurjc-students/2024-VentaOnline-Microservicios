package es.webapp.webapp.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.User;
import es.webapp.webapp.repository.UserRepo;

@Service
public class UserDetailService implements UserDetailsService{

    @Autowired
    private UserRepo userRepository;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        System.out.println("username " + username);

		Optional<User> user = userRepository.findByUsername(username);

		if(!user.isPresent()){
			throw new UsernameNotFoundException("error.html");
		}
    
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_" + user.get().getRol()));

		return new org.springframework.security.core.userdetails.User(user.get().getUsername(), 
				user.get().getPassword(), roles);

    }
}
