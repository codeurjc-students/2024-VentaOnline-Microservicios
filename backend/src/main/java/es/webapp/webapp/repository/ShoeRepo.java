package es.webapp.webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.Shoe;

public interface ShoeRepo extends JpaRepository<Shoe, Integer>{

    Optional<Shoe> findByCode(String code);
    
}
