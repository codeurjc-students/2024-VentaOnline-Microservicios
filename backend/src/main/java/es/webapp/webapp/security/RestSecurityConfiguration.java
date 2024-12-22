package es.webapp.webapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@Order(1)
public class RestSecurityConfiguration{


    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
            .csrf().disable()
            .antMatcher("/api/**")
            .authorizeHttpRequests(registry -> {
                registry.antMatchers("").hasAnyRole(null);
                registry.anyRequest().permitAll();
            
            })
            .build();
    }

     
}
