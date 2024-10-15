package es.webapp.webapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
}
