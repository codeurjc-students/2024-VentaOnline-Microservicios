package es.webapp.webapp.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemToBuyService;
import es.webapp.webapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class ShoppingCartRestController {
    
    @Autowired
    private ItemToBuyService itemToBuyService;

    @Autowired
    private UserService userService;

    //LOGGED WITH REDDIS

    @GetMapping("/shoppingcart/user")
    public ResponseEntity<ShoppingCart> getShoppingCart(HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails){
       
        Optional<User> user = userService.findByUsername(userDetails.getUsername()  );
        if(user.isPresent()) {
            ShoppingCart shoppingCart = user.get().getShoppingCart();
            return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/shoppingcart/user/items")
    public ResponseEntity<List<ItemToBuy>> getItemsToBuy(HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails){
        
        Optional<User> user = userService.findByUsername(userDetails.getUsername());
        if(user.isPresent()) {
            ShoppingCart shoppingCart = user.get().getShoppingCart();
            return new ResponseEntity<>(itemToBuyService.findByShoppingCart(shoppingCart), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //LOGGED WITH JWT

    @Operation(summary = "Get an user shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get an user shopping cart", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ShoppingCart.class))
        }),
        @ApiResponse(responseCode = "404", description = "No shopping cart founded", content = @Content)
    })
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

    @Operation(summary = "Get a shopping cart items listing")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get a shopping cart items", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ShoppingCart.class))
        }),
        @ApiResponse(responseCode = "404", description = "No item founded", content = @Content)
    })
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

    @Operation(summary = "Delete an item from a shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete an item from a shopping cart", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ShoppingCart.class))
        }),
        @ApiResponse(responseCode = "404", description = "No item removed", content = @Content)
    })
    @DeleteMapping("/api/shoppingcart/{id}/remove")
    public ResponseEntity<String> deleteItemToBuyById(@PathVariable Integer id){
        Optional<ItemToBuy> item = itemToBuyService.findById(id);
        if(item.isPresent()){
            
            itemToBuyService.updateShoppingCart(item.get());
            itemToBuyService.deleteById(id);
            return new ResponseEntity<>("done",HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
