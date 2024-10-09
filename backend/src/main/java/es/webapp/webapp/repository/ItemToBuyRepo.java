package es.webapp.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.ItemToBuy;

public interface ItemToBuyRepo extends JpaRepository<ItemToBuy, Integer>{
    
}
