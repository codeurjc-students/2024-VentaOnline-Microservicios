package es.webapp.webapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.Order;
import es.webapp.webapp.model.Order.State;
import es.webapp.webapp.model.User;
import es.webapp.webapp.repository.ItemToBuyRepo;
import es.webapp.webapp.repository.OrderRepo;
import es.webapp.webapp.repository.UserRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ItemToBuyRepo itemToBuyRepo;

    public void save(Order order){
        orderRepo.save(order);
    }

    public void buy(String username){
        Order order = new Order();

        Optional<User> user = userRepo.findByUsername(username);

        if(user.isPresent()) {
            List<ItemToBuy> itemsToBuy = itemToBuyRepo.findByShoppingCart(user.get().getShoppingCart());
       
            double cost=0.0;
            for(ItemToBuy item: itemsToBuy){
                cost += (item.getCount()*item.getItems().get(0).getPrice());
                item.setOrder(order);
                item.setShoppingCart(null);
            }

            user.get().getShoppingCart().setTotalCost(0.0);

            order.setTotalCost(Math.round(cost * 100.0) / 100.0);
            order.setCode(UUID.randomUUID().toString().toUpperCase().substring(0,7));
            order.setState(State.PENDING.name());
            String date = LocalDate.now().toString();
            order.setCreationDate(date);
            order.setUser(user.get());
            orderRepo.save(order);
        }
    }
    
    public Page<Order> findAll(Pageable page) {
        return orderRepo.findAll(page);
    }

    public Optional<Order> findById(Integer id) {
        return orderRepo.findById(id);
    }

    public Page<Order> findByUser(User user, Pageable page){
        return orderRepo.findByUser(user, page);
    }
}
