package es.webapp.webapp.controller;

import java.util.List;
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

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.Order;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemToBuyService;
import es.webapp.webapp.service.OrderService;
import es.webapp.webapp.service.UserService;




@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemToBuyService itemToBuyService;

    @GetMapping("/users/{username}")
    public ResponseEntity<Boolean> buy(@PathVariable String username) {
        
        Optional<User> user = userService.findByUsername(username);

        if(user.isPresent()) {
            orderService.buy(username);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
    
    
    @GetMapping("/orders")
    public Page<Order> getOrders(Pageable page) {
        return orderService.findAll(page);
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<Page<Order>> getOrdersByUser(@PathVariable Integer id, Pageable page) {
        Optional<User> user = userService.findById(id);
        if(user.isPresent()) {
            return new ResponseEntity<>(orderService.findByUser(user.get(),page),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{ident}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer ident) {
        Optional<Order> order = orderService.findById(ident);

        if(order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } 
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemToBuy>> getItemsByOrder(@PathVariable Integer id) {
        Optional<Order> order = orderService.findById(id);

        if(order.isPresent()) {
            return new ResponseEntity<>(itemToBuyService.findByOrder(order.get()), HttpStatus.OK);
        } 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
