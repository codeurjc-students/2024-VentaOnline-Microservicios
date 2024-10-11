package es.webapp.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.Order;

public interface OrderRepo extends JpaRepository<Order, Integer>{
    
}
