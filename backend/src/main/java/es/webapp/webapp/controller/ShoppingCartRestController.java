package es.webapp.webapp.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemToBuyService;
import es.webapp.webapp.service.UserService;

@RestController
public class ShoppingCartRestController {
    
    @Autowired
    private ItemToBuyService itemToBuyService;

    @Autowired
    private UserService userService;

    //LOGGED WITH REDDIS

    @GetMapping("/shoppingcart/user")
    public ResponseEntity<ShoppingCart> getShoppingCart(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("user");
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            ShoppingCart shoppingCart = user.get().getShoppingCart();
            return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/shoppingcart/user/items")
    public ResponseEntity<List<ItemToBuy>> getItemsToBuy(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("user");
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            ShoppingCart shoppingCart = user.get().getShoppingCart();
            return new ResponseEntity<>(itemToBuyService.findByShoppingCart(shoppingCart), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //LOGGED WITH JWT

    @GetMapping("/api/shoppingcart/{username}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable String username){
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            ShoppingCart shoppingCart = user.get().getShoppingCart();
            return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/api/shoppingcart/{username}/items")
    public ResponseEntity<List<ItemToBuy>> getItemsToBuy(@PathVariable String username){
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            ShoppingCart shoppingCart = user.get().getShoppingCart();
            return new ResponseEntity<>(itemToBuyService.findByShoppingCart(shoppingCart), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/shoppingcart/{id}/remove")
    public ResponseEntity<String> deleteItemToBuyById(@PathVariable Integer id){
        Optional<ItemToBuy> item = itemToBuyService.findById(id);
        if(item.isPresent()){
            itemToBuyService.deleteById(id);
            return new ResponseEntity<>("done",HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
