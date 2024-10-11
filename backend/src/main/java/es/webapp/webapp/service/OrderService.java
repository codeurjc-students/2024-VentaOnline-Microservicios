package es.webapp.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.Order;
import es.webapp.webapp.repository.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public void save(Order order){
        orderRepo.save(order);
    }
    
}
