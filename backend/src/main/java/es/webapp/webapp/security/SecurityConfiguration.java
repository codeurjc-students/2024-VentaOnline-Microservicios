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

        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        .authorizeHttpRequests(auth -> auth
            // 🔓 públicos
            .antMatchers(
                "/",
                "/login",
                "/signin",
                "/signup",
                "/signout",
                "/loginerror",
                "/users/session",
                "/new",
                "/api/auth/**"
            ).permitAll()

            // 👤 USER
            .antMatchers(
                "/my_profile",
                "/items/*",
                "/items/*/page",
                "/items/*/purchase",
                "/items/*/favourites/*/new",
                "/orders/new/users/*",
                "/orders/user",
                "/shoppingcart/**",
                "/update/*"
            ).hasRole("USER")

            // 👑 ADMIN
            .antMatchers("/orders/*").hasAnyRole("USER","ADMIN")

            // 🔒 TODO lo demás
            .anyRequest().authenticated()
        )

        .logout(logout -> logout
            .logoutUrl("/signout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
        )

        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

        .headers(headers ->
            headers.addHeaderWriter(
                new StaticHeadersWriter("Access-Control-Allow-Origin", "*")
            )
        )

        .cors();

        return http.build();
    }


    public void addCorsMapping(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true);
    }
}
