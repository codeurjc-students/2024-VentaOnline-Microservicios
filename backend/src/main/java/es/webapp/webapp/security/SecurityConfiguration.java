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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable())

        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        .authorizeHttpRequests(auth -> auth

    .requestMatchers(
        new AntPathRequestMatcher("/"),
        new AntPathRequestMatcher("/login"),
        new AntPathRequestMatcher("/signin"),
        new AntPathRequestMatcher("/signup"),
        new AntPathRequestMatcher("/signout"),
        new AntPathRequestMatcher("/loginerror"),
        new AntPathRequestMatcher("/users/session"),
        new AntPathRequestMatcher("/new"),
        new AntPathRequestMatcher("/api/auth/**")
    ).permitAll()

    .requestMatchers(
        new AntPathRequestMatcher("/my_profile"),
        new AntPathRequestMatcher("/items/**"),
        new AntPathRequestMatcher("/orders/new/users/**"),
        new AntPathRequestMatcher("/orders/user"),
        new AntPathRequestMatcher("/shoppingcart/**"),
        new AntPathRequestMatcher("/update/**")
    ).hasRole("USER")

    .requestMatchers(
        new AntPathRequestMatcher("/orders/**")
    ).hasAnyRole("USER","ADMIN")

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
