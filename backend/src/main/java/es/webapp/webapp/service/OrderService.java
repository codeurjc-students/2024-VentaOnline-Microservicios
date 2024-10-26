package es.webapp.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.Order;
import es.webapp.webapp.model.User;
import es.webapp.webapp.repository.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public void save(Order order){
        orderRepo.save(order);
    }

    public Page<Order> findAll(Pageable page) {
        return orderRepo.findAll(page);
    }
    
    public Page<Order> findByUser(User user, Pageable page){
        return orderRepo.findByUser(user, page);
    }
}
