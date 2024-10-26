package es.webapp.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.ShoppingCart;

public interface ItemToBuyRepo extends JpaRepository<ItemToBuy, Integer>{

    List<ItemToBuy> findByShoppingCart(ShoppingCart shoppingCart);
    
}
