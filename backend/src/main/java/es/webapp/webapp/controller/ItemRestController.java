package es.webapp.webapp.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemService;
import es.webapp.webapp.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    //LOGGED WITH REDDIS

    @GetMapping("/items/favourites/user")
    public ResponseEntity<Page<Item>> getFavItems(HttpServletRequest request, Pageable page){
        String username = (String) request.getSession().getAttribute("user");
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()){
            Page<Item> items = new PageImpl<>(user.get().getItems(), page, user.get().getItems().size());
            return new ResponseEntity<>(items,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getParticularItemReddis(@PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);

        if(item.isPresent()){
            return new ResponseEntity<>(item.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //LOGGED WITH JWT
    
    @PostMapping("/api/add/cart/users/{name}/items/{id}")
    public ResponseEntity<Boolean> addToCart(@PathVariable String name,@PathVariable Integer id, @RequestBody ItemToBuy itemToBuy) {
        if(itemService.addToCart(name, id, itemToBuy))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
    

    @GetMapping("/api/items")
    public ResponseEntity<Page<Item>> getItems(Pageable page){
        Page<Item> items = itemService.findAll(page);
        return new ResponseEntity<>(items,HttpStatus.OK);
    }

    @GetMapping("/api/items/listing")
    public ResponseEntity<List<Item>> getItemsListing(){
        List<Item> items = itemService.findAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/api/items/{id}/info")
    public ResponseEntity<Item> getParticularItem(@PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);

        if(item.isPresent()){
            return new ResponseEntity<>(item.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/items/{name}")
    public ResponseEntity<Page<Item>> getItemsByName(@PathVariable String name, Pageable page){
        return new ResponseEntity<>(itemService.findByName(name, page),HttpStatus.OK);
    }

    @GetMapping("/api/items/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable Integer id) throws SQLException {

        Optional<Item> item = itemService.findById(id);

        if(item.isPresent() && item.get().getImageFile() != null){
            Resource file = new InputStreamResource(item.get().getImageFile().getBinaryStream());
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .contentLength(item.get().getImageFile().length())
                .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/items/favourites/{username}")
    public ResponseEntity<Page<Item>> getFavouritesItems(@PathVariable String username,Pageable page){

        Optional<User> user = userService.findByUsername(username);
        Page<Item> items = new PageImpl<>(user.get().getItems(), page, user.get().getItems().size());
        if(user.isPresent()){
            return new ResponseEntity<>(items,HttpStatus.OK);
        }else{
            return null;
        }
    }
    

    /*@PostMapping("/items/{id}/stock")
    public ResponseEntity<Stock> postMethodName(@PathVariable Integer id, @RequestBody Stock stock) {
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()) {
            stock.setItem(item.get());
            return new ResponseEntity<>(stock, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
}
