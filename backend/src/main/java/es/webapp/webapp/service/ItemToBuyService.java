package es.webapp.webapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.repository.ItemToBuyRepo;

@Service
public class ItemToBuyService {

    @Autowired
    private ItemToBuyRepo itemToBuyRepo;

    public void save(ItemToBuy item){
        itemToBuyRepo.save(item);
    }
    
    public List<ItemToBuy> findByShoppingCart(ShoppingCart shoppingCart){
        return itemToBuyRepo.findByShoppingCart(shoppingCart);
    }

    public Optional<ItemToBuy> findById(Integer id){
        return itemToBuyRepo.findById(id);
    }

    public void deleteById(Integer id){
        itemToBuyRepo.deleteById(id);
    }
}
