package es.webapp.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemService;
import es.webapp.webapp.service.ItemToBuyService;
import es.webapp.webapp.service.ShoppingCartService;
import es.webapp.webapp.service.UserService;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemToBuyService itemToBuyService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if(principal != null){
            String name = principal.getName();
            Optional<User> user = userService.findByUsername(name);
            model.addAttribute("username",user.get().getUsername());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            model.addAttribute("user", request.isUserInRole("USER"));
            model.addAttribute("logged",true);
        } else {
            model.addAttribute("logged",false);
        }
        model.addAttribute("sizeS",false);
        model.addAttribute("sizeM",false);
        model.addAttribute("sizeL",false);
        model.addAttribute("sizeXL",false);
        model.addAttribute("sizeByDefault",false);
    }

    /*private void showSizes(Model model, Optional<Item> item){
        if(item.get().getSizes() != null && item.get().getSizes()[0] != null){
            model.addAttribute("sizeS",true);
            model.addAttribute("size1",item.get().getSizes()[0]);   
        } else {
            model.addAttribute("size1",""); 
        }
        if(item.get().getSizes() != null && item.get().getSizes()[1] != null){
            model.addAttribute("sizeM",true);
            model.addAttribute("size2",item.get().getSizes()[1]);   
        } else {
            model.addAttribute("size2",""); 
        }
        if(item.get().getSizes() != null && item.get().getSizes()[2] != null){
            model.addAttribute("sizeL",true);
            model.addAttribute("size3",item.get().getSizes()[2]);   
        } else {
            model.addAttribute("size3",""); 
        }
        if(item.get().getSizes() != null && item.get().getSizes()[3] != null){
            model.addAttribute("sizeXL",true);
            model.addAttribute("size4",item.get().getSizes()[3]);   
        } else {
            model.addAttribute("size4",""); 
        } 
        if(item.get().getSizes() == null){
            model.addAttribute("sizeByDefault",true);
            model.addAttribute("size1","unique"); 
        }
    }

    private void showStocks(Model model, Optional<Item> item){
        if(item.get().getStocks() != null && item.get().getStocks()[0] != null){
            if(item.get().getStocks()[0] >= 5){model.addAttribute("stock1","(Avaiable)");}else{model.addAttribute("stock1","(Less than 5 items avaiable)");}  
        } else {
            model.addAttribute("stock1",0); 
        }
        if(item.get().getStocks() != null && item.get().getStocks()[1] != null){
            if(item.get().getStocks()[0] >= 5){model.addAttribute("stock2","(Avaiable)");}else{model.addAttribute("stock2","(Less than 5 items avaiable)");}  
        } else {
            model.addAttribute("stock2",0); 
        }
        if(item.get().getStocks() != null && item.get().getStocks()[2] != null){
            if(item.get().getStocks()[0] >= 5){model.addAttribute("stock3","(Avaiable)");}else{model.addAttribute("stock3","(Less than 5 items avaiable)");}    
        } else {
            model.addAttribute("stock3",0); 
        }
        if(item.get().getStocks() != null && item.get().getStocks()[3] != null){
            if(item.get().getStocks()[0] >= 5){model.addAttribute("stock4","(Avaiable)");}else{model.addAttribute("stock4","(Less than 5 items avaiable)");}  
        } else {
            model.addAttribute("stock4",0); 
        }
    }*/

    @GetMapping("/{id}/page")
    public String itemPage(Model model, @PathVariable Integer id){   
            
        model.addAttribute("status","");
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()) {
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
            
            //showSizes(model,item);
            //showStocks(model, item);
  
            model.addAttribute("type",item.get().getType());
            model.addAttribute("description",item.get().getDescription());
        } else {
            return "error";
        }
        return "product";
    }

    @PostMapping("/{id}/purchase")
    public String itemBuy(Model model, Item item, ItemToBuy itemToBuy, @PathVariable Integer id, HttpServletRequest request) throws IOException{

        Optional<Item> product = itemService.findById(id);
        if(product.isPresent()) {

            Principal principal = request.getUserPrincipal();

            if(principal != null){
                String name = principal.getName();
                Optional<User> user = userService.findByUsername(name);
                if(user.isPresent()) {
                    if(user.get().getShoppingCart() == null){
                        ShoppingCart cart = new ShoppingCart();
                        user.get().setShoppingCart(cart);
                    }
                    shoppingCartService.save(user.get().getShoppingCart());

                    itemToBuy.setItem(product.get());
                    itemToBuy.setShoppingCart(user.get().getShoppingCart());

                    itemToBuyService.save(itemToBuy);

                    model.addAttribute("name",product.get().getName());
                    model.addAttribute("price",product.get().getPrice());
                    model.addAttribute("gender",product.get().getGender());
                    
                    //showSizes(model,product);
                    //showStocks(model, product);
        
                    model.addAttribute("type",product.get().getType());
                    model.addAttribute("description",product.get().getDescription());

                    model.addAttribute("status","item successfully added to the cart");
                    return "product";
                } else {
                    return "error";
                }
            }
            return "error";

        }
        return "error";
    }

    /*@GetMapping("/{id}/favourites/{username}/new")
    public String addFavourite(Model model, @PathVariable Integer id, @PathVariable String username)  {
        Optional<Item> item = itemService.findById(id);
        Optional<User> user = userService.findByUsername(username);
        
        userService.newItem(user.get().getId(),item.get());
        return "index";

    }*/
}
