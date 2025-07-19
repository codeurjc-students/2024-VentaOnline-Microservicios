package es.webapp.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalTime;
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
import es.webapp.webapp.model.Stock;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemService;
import es.webapp.webapp.service.ItemToBuyService;
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
    public String itemBuy(Model model, ItemToBuy itemToBuy, @PathVariable Integer id, HttpServletRequest request) throws IOException{

        Optional<Item> product = itemService.findById(id);
        if(product.isPresent()) {

            Principal principal = request.getUserPrincipal();

            if(principal != null){
                String name = principal.getName();
                Optional<User> user = userService.findByUsername(name);
                if(user.isPresent()) {

                    itemToBuy.getItems().add(product.get());
                    if(user.get().getShoppingCart().getItems().isEmpty())
                        user.get().getShoppingCart().setBuyTime(LocalTime.now());
                    
                    itemToBuy.setShoppingCart(user.get().getShoppingCart());

                
                    //decrement stock of the item purchased
                    for(Stock<?> stock: product.get().getStocks()){
                        if(stock.getSize().getLabel().equals(itemToBuy.getSize()) && itemToBuy.getCount() <= stock.getStock()){
                            int count = itemToBuy.getCount();
                            stock.setStock(stock.getStock() - count);
                            
                        }else if(itemToBuy.getCount() > stock.getStock()){
                            model.addAttribute("status","there is not enough items to buy");
                            return "product";
                        }
                    }

                    //update cost of the shopping cart
                    double cost = (itemToBuy.getCount() * product.get().getPrice());
                    ShoppingCart shoppingCart = user.get().getShoppingCart();
                    user.get().getShoppingCart().setTotalCost(shoppingCart.getTotalCost() + cost);

                    itemToBuyService.save(itemToBuy);

                    model.addAttribute("status","item successfully added to the cart");
                    return "product";
                }else {
                    return "error";
                }
            }
            return "error";

        }
        return "error";
    }

    @GetMapping("/{code}/favourites/{id}/new")
    public String addFavourite(Model model, @PathVariable String code, @PathVariable Integer id)  {
        Optional<Item> item = itemService.findByCode(code);
        Optional<User> user = userService.findById(id);
        
        item.get().getUsers().add(user.get());
        itemService.save(item.get());
        model.addAttribute("username",user.get().getUsername());
        return "index";

    }
}
