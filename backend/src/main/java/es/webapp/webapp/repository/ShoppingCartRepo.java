package es.webapp.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.ShoppingCart;

public interface ShoppingCartRepo extends JpaRepository<ShoppingCart, Integer>{
    
}
