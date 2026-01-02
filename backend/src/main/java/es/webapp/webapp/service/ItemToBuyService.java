package es.webapp.webapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.Order;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.Stock;
import es.webapp.webapp.model.User;
import es.webapp.webapp.repository.ItemToBuyRepo;
import es.webapp.webapp.repository.ShoppingCartRepo;

@Service
public class ItemToBuyService {

    @Autowired
    private ItemToBuyRepo itemToBuyRepo;

    @Autowired
    private ShoppingCartRepo shoppingCartRepo;

    public void save(ItemToBuy item){
        itemToBuyRepo.save(item);
    }
    
    public List<ItemToBuy> findByShoppingCart(ShoppingCart shoppingCart){
        return itemToBuyRepo.findByShoppingCart(shoppingCart);
    }

    public List<ItemToBuy> findByOrder(Order order){
        return itemToBuyRepo.findByOrder(order);
    }

    public Optional<ItemToBuy> findById(Integer id){
        return itemToBuyRepo.findById(id);
    }

    public void deleteById(Integer id){
        itemToBuyRepo.deleteById(id);
    }

    public void updateShoppingCart(ItemToBuy itemToBuy){

        User user = itemToBuy.getShoppingCart().getUser();
        Item product = itemToBuy.getItems().get(0);
        int count = itemToBuy.getCount();

        //remove item from shopping cart
        user.getShoppingCart().getItems().remove(itemToBuy);

        //increase the stock
        for(Stock<?> stock: product.getStocks()){
            if(stock.getSize().getLabel().equals(itemToBuy.getSize())){
                stock.setStock(stock.getStock() + count);
                
            }
        }

        //update cost of the shopping cart
        double cost = (count * product.getPrice());
        double totalCost = (user.getShoppingCart().getTotalCost() - cost);
        user.getShoppingCart().setTotalCost(totalCost);

        shoppingCartRepo.save(user.getShoppingCart());
    }
}
