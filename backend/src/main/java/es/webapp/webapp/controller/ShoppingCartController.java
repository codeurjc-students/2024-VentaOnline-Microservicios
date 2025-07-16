package es.webapp.webapp.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.Stock;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemToBuyService;
import es.webapp.webapp.service.ShoppingCartService;
import es.webapp.webapp.service.UserService;

@Controller
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

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
            List<ItemToBuy> itemsToBuy = itemToBuyService.findByShoppingCart(user.get().getShoppingCart());
            if(itemsToBuy.size() > 0){
                model.addAttribute("neworder",false);
            }
        } else {
            model.addAttribute("logged",false);
        }
    }

    @GetMapping("/page")
    public String shoppingCartPage(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if(principal != null){
            String username = principal.getName();
            Optional<User> user = userService.findByUsername(username);
            if(user.isPresent()) {
                List<ItemToBuy> itemsToBuy = itemToBuyService.findByShoppingCart(user.get().getShoppingCart());
                if(itemsToBuy.size() > 0){
                    model.addAttribute("neworder",false);
                } else {
                    model.addAttribute("neworder",true);
                }
                return "shoppingCart";
            } else {
                return "error";
            }
        } else {
            return "error";
        }       
    }

    @GetMapping("/{id}/remove")
    public String removeItemToBuy(Model model, @PathVariable Integer id){

        Optional<ItemToBuy> itemToBuy = itemToBuyService.findById(id);

        User user = itemToBuy.get().getShoppingCart().getUser();
        Item product = itemToBuy.get().getItems().get(0);
        int count = itemToBuy.get().getCount();

        if(itemToBuy.isPresent()) {
            itemToBuyService.deleteById(id);
        }
    
        //remove item from shopping cart
        user.getShoppingCart().getItems().remove(itemToBuy.get());

        //increase the stock
        for(Stock<?> stock: product.getStocks()){
            if(stock.getSize().getLabel().equals(itemToBuy.get().getSize())){
                stock.setStock(stock.getStock() + count);
                
            }
        }

        //update cost of the shopping cart
        double cost = (count * product.getPrice());
        double totalCost = (user.getShoppingCart().getTotalCost() - cost);
        user.getShoppingCart().setTotalCost(totalCost);

        shoppingCartService.save(user.getShoppingCart());


        List<ItemToBuy> itemsToBuy = itemToBuyService.findByShoppingCart(user.getShoppingCart());
        if(itemsToBuy.size() > 0){
            model.addAttribute("neworder",false);
        } else {
            model.addAttribute("neworder",true);
        }
        return "shoppingCart";
    }
    
}
