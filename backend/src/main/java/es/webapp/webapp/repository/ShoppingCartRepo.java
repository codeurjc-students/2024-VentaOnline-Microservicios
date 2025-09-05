package es.webapp.webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.User;

public interface ShoppingCartRepo extends JpaRepository<ShoppingCart, Integer>{
    
    Optional<ShoppingCart> findByUser(User user);

    Optional<ShoppingCart> findByCode(String code);
}
