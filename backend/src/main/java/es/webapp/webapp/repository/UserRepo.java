package es.webapp.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
    
    
}
