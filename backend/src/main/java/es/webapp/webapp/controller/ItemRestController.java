package es.webapp.webapp.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemService;
import es.webapp.webapp.service.UserService;


@RestController
@RequestMapping("/api")
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;
    

    @GetMapping("/items")
    public Page<Item> getItems(Pageable page){
        return itemService.findAll(page);
    }

    @GetMapping("/items/listing")
    public List<Item> getItemsListing(){
        return itemService.findAll();
    }

    @GetMapping("/items/{name}")
    public Page<Item> getItemsByName(@PathVariable String name, Pageable page){
        return itemService.findByName(name, page);
    }

    @GetMapping("/items/favourites/{username}")
    public ResponseEntity<Page<Item>> getFavouritesItems(@PathVariable String username,Pageable page){

        Optional<User> user = userService.findByUsername(username);
        Page<Item> items = new PageImpl<>(user.get().getFavouritesItems(), page, user.get().getFavouritesItems().size());
        if(user.isPresent()){
            return new ResponseEntity<>(items,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

}
