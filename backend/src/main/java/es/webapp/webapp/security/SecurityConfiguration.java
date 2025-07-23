package es.webapp.webapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf().disable()
            .formLogin(httpForm -> {
                httpForm.loginPage("/login").permitAll();
                httpForm.defaultSuccessUrl("/");
                httpForm.usernameParameter("username");
                httpForm.passwordParameter("password"); 
                httpForm.failureUrl("/error");
            })

            .authorizeHttpRequests(registry -> {
                registry.antMatchers("/").permitAll();
                registry.antMatchers("/login").permitAll();
                registry.antMatchers("/loginerror").permitAll();
                registry.antMatchers("/signup").permitAll();
                registry.antMatchers("/new").permitAll();

                registry.antMatchers("/logout").hasAnyRole("ADMIN", "USER");

                registry.antMatchers("/my_profile").hasAnyRole("USER");
                registry.antMatchers("/items/{id}/page").hasAnyRole("USER");
                registry.antMatchers("/items/{id}/purchase").hasAnyRole("USER");
                registry.antMatchers("/items/{code}/favourites/{id}/new").hasAnyRole("USER");
                registry.antMatchers("/orders/new/users/{username}").hasAnyRole("USER");
                registry.antMatchers("/shoppingcart/page").hasAnyRole("USER");
                registry.antMatchers("/shoppingcart/{id}/remove").hasAnyRole("USER");
                registry.antMatchers("/update/{id}").hasAnyRole("USER");
            })
            
            .logout(httpLogout -> {
                httpLogout.logoutUrl("/logout").permitAll();
                httpLogout.logoutSuccessUrl("/");
            });

        http.headers(header -> header.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")));
        return http.build();
    }

    public void addCorsMapping(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:8444")
                .allowedOrigins("https://localhost:8445")
                .allowCredentials(true);
    }
}
