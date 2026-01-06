package es.webapp.webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.Direction;

public interface DirectionRepo extends JpaRepository<Direction, Integer>{
 Optional<Direction> findByCode(String code);   
}
