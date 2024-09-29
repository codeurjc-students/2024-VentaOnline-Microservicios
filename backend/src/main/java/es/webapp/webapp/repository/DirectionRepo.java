package es.webapp.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.Direction;

public interface DirectionRepo extends JpaRepository<Direction, Integer>{
    
}
