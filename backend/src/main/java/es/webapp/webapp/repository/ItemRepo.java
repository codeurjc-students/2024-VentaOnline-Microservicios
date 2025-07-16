package es.webapp.webapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.webapp.webapp.model.Item;

public interface ItemRepo extends JpaRepository<Item, Integer> {


    @Query("select m from Item m where m.code like %:name% or m.name like %:name% or m.description like %:name% or m.gender like %:name% or m.type like %:name%")
    Page<Item> findByName(String name, Pageable page);

    Optional<Item> findByCode(String code);    
}
