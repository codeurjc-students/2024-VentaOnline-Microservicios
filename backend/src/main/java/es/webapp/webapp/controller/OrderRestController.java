package es.webapp.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.webapp.webapp.model.Order;
import es.webapp.webapp.service.OrderService;



@RestController
@RequestMapping("/databases")
public class OrderRestController {

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
