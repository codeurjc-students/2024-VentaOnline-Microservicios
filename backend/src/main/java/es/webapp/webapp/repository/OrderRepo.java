package es.webapp.webapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.Order;
import es.webapp.webapp.model.User;

public interface OrderRepo extends JpaRepository<Order, Integer>{

    Page<Order> findByUser(User user, Pageable page);

    Optional<Order> findByCode(String code);

    Optional<Order> findById(Integer id);
}
