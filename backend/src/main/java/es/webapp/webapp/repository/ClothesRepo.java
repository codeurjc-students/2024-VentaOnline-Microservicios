package es.webapp.webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.Clothes;

public interface ClothesRepo extends JpaRepository<Clothes, Integer> {
    
    Optional<Clothes> findByCode(String code);
}
