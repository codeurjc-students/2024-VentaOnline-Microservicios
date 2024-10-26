package es.webapp.webapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.webapp.webapp.model.Order;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.OrderService;
import es.webapp.webapp.service.UserService;



@RestController
@RequestMapping("/databases")
public class OrderRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public Page<Order> getOrders(Pageable page) {
        return orderService.findAll(page);
    }
    
    
    /*@GetMapping("/orders/user/{username}")
    public ResponseEntity<Page<Order>> getOrdersByUser(@PathVariable Integer id, Pageable page) {
        Optional<User> user = userService.findById(id);
        if(user.isPresent()) {
            return new ResponseEntity<>(orderService.findByUser(user.get(),page),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
}
