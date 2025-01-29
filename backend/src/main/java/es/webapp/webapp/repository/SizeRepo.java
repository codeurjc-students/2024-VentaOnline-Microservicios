package es.webapp.webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.Size;

public interface SizeRepo extends JpaRepository<Size, Integer>{
    
    Optional<Size> findByCode(String code);
}
