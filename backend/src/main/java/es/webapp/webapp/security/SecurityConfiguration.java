package es.webapp.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf().disable()
            .formLogin(httpForm -> {
                httpForm.loginPage("/login");
                httpForm.loginProcessingUrl("/signin");
                httpForm.defaultSuccessUrl("/");
                httpForm.usernameParameter("username");
                httpForm.passwordParameter("password"); 
                httpForm.failureUrl("/error");
            })
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(auth -> auth
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .authorizeHttpRequests(registry -> {
                registry.antMatchers("/").permitAll();
                registry.antMatchers("/login").permitAll();
                registry.antMatchers("/users/session").permitAll();
                registry.antMatchers("/loginerror").permitAll();
                registry.antMatchers("/signup").permitAll();
                registry.antMatchers("/signin").permitAll();
                registry.antMatchers("/new").permitAll();
                registry.antMatchers("/signout").permitAll();
                registry.antMatchers("/my_profile").hasAnyRole("USER");
                registry.antMatchers("/items/{id}/page").hasAnyRole("USER");
                registry.antMatchers("/items/{id}").hasAnyRole("USER");
                registry.antMatchers("/items/{id}/purchase").hasAnyRole("USER");
                registry.antMatchers("/items/{code}/favourites/{id}/new").hasAnyRole("USER");
                registry.antMatchers("/orders/new/users/{username}").hasAnyRole("USER");
                registry.antMatchers("/orders/user").hasAnyRole("USER");
                registry.antMatchers("/orders/{id}").hasAnyRole("USER","ADMIN");
                registry.antMatchers("/shoppingcart/page").hasAnyRole("USER");
                registry.antMatchers("/shoppingcart/{id}/remove").hasAnyRole("USER");
                registry.antMatchers("/shoppingcart/user").hasAnyRole("USER");
                registry.antMatchers("/shoppingcart/user/items").hasAnyRole("USER");
                registry.antMatchers("/update/{id}").hasAnyRole("USER");
            })

            .logout(httpLogout -> {
                httpLogout.logoutUrl("/signout");
                httpLogout.logoutSuccessUrl("/");
                httpLogout.invalidateHttpSession(true);
            })

            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers(header -> 
        header.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")));
        http.cors();
        return http.build();
    }


    public void addCorsMapping(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true);
    }
}
