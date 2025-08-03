package es.webapp.webapp.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import es.webapp.webapp.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTGenerator {

    public String generateToken(UserDetails user){
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        List<String> roles = authorities.stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList());

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();
        return token;
    }

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public long getExpirationMillis(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(SecurityConstants.JWT_SECRET)
            .parseClaimsJws(token)
            .getBody();
        Date expiration = claims.getExpiration();
        return expiration.getTime() - System.currentTimeMillis(); // tiempo restante en milis
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch(Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
    
}
