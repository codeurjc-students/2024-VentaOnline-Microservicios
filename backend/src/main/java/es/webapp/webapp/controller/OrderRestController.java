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
import org.springframework.web.bind.annotation.RestController;

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.Order;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemToBuyService;
import es.webapp.webapp.service.OrderService;
import es.webapp.webapp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemToBuyService itemToBuyService;

    //LOGGED WITH REDDIS
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderByIdReddis(@PathVariable Integer id) {
        Optional<Order> order = orderService.findById(id);

        if(order.isPresent()) {
            return new ResponseEntity<>(order.get(),HttpStatus.OK);
        } 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    

    //LOGGED WITH JWT
    @Operation(summary = "Buy - Generate an order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Generate an order", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
        }),
        @ApiResponse(responseCode = "404", description = "No order generated", content = @Content)
    })
    @GetMapping("/api/orders/users/{username}")
    public ResponseEntity<Boolean> buy(@PathVariable String username) {
        
        Optional<User> user = userService.findByUsername(username);

        if(user.isPresent()) {
            orderService.buy(username);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
    
    @Operation(summary = "Get all orders paged")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get orders paged", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
        }),
        @ApiResponse(responseCode = "404", description = "No order generated", content = @Content)
    })
    @GetMapping("/api/orders")
    public Page<Order> getOrders(Pageable page) {
        return orderService.findAll(page);
    }

    @Operation(summary = "Get an order by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get an order by id", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
        }),
        @ApiResponse(responseCode = "404", description = "No order founded", content = @Content)
    })
    @GetMapping("/api/orders/{ident}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer ident) {
        Optional<Order> order = orderService.findById(ident);

        if(order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } 
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get items of an order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get items listing of an order", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
        }),
        @ApiResponse(responseCode = "404", description = "No item of any order founded", content = @Content)
    })
    @GetMapping("/api/orders/{id}/items")
    public ResponseEntity<List<ItemToBuy>> getItemsByOrder(@PathVariable Integer id) {
        Optional<Order> order = orderService.findById(id);

        if(order.isPresent()) {
            return new ResponseEntity<>(itemToBuyService.findByOrder(order.get()), HttpStatus.OK);
        } 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get user orders paged")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get user orders paged", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
        }),
        @ApiResponse(responseCode = "404", description = "Orders not founded", content = @Content)
    })
    @GetMapping("/api/orders/user/{id}")
    public ResponseEntity<Page<Order>> getOrdersByUser(@PathVariable Integer id, Pageable page) {
        Optional<User> user = userService.findById(id);
        if(user.isPresent()) {
            return new ResponseEntity<>(orderService.findByUser(user.get(),page),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
