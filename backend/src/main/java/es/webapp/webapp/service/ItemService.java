package es.webapp.webapp.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.Stock;
import es.webapp.webapp.model.User;
import es.webapp.webapp.repository.ItemRepo;
import es.webapp.webapp.repository.ItemToBuyRepo;
import es.webapp.webapp.repository.UserRepo;

@Service
public class ItemService {
    
    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ItemToBuyRepo itemToBuyRepo;

    public Item save(Item item){
        return itemRepo.save(item);
    }

    public boolean addToCart(String name, Integer id, ItemToBuy itemToBuy){
        Optional<User> user = userRepo.findByUsername(name);
        if(user.isPresent()) {

            Optional<Item> product = itemRepo.findById(id);
            if(product.isPresent()) {

                itemToBuy.getItems().add(product.get());
                if(user.get().getShoppingCart().getItems().isEmpty())
                    user.get().getShoppingCart().setBuyTime(LocalTime.now().toString());
                
                itemToBuy.setShoppingCart(user.get().getShoppingCart());
                itemToBuy.setCode(UUID.randomUUID().toString().toUpperCase().substring(0,7));

                //decrement stock of the item purchased
                for(Stock<?> stock: product.get().getStocks()){
                    if(stock.getSize().getLabel().equals(itemToBuy.getSize()) && itemToBuy.getCount() <= stock.getStock()){
                        int count = itemToBuy.getCount();
                        stock.setStock(stock.getStock() - count);
                        
                    }else if(itemToBuy.getCount() > stock.getStock()){
                        return false;
                    }
                }

                //update cost of the shopping cart
                double cost = (itemToBuy.getCount() * product.get().getPrice());
                ShoppingCart shoppingCart = user.get().getShoppingCart();
                user.get().getShoppingCart().setTotalCost(shoppingCart.getTotalCost() + cost);

                itemToBuyRepo.save(itemToBuy);
                return true;
            }
            return false;
        }
        return false;
    }

    public Page<Item> findAll(Pageable page){
        return itemRepo.findAll(page);
    }

    public List<Item> findAll(){
        return itemRepo.findAll();
    }

    public Optional<Item> findById(Integer id){
        return itemRepo.findById(id);
    }

    public Page<Item> findByName(String name, Pageable page){
        return itemRepo.findByName(name, page);
    }

    public Optional<Item> findByCode(String code){
        return itemRepo.findByCode(code);
    }
}
