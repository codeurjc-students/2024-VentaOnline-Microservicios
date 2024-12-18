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

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemToBuyService;
import es.webapp.webapp.service.UserService;

@Controller
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

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
            //System.out.println(name);
            Optional<User> user = userService.findByUsername(username);
            if(user.isPresent()) {
                if(user.get().getShoppingCart() == null){
                    ShoppingCart cart = new ShoppingCart();
                    user.get().setShoppingCart(cart);
                }
                List<ItemToBuy> itemsToBuy = itemToBuyService.findByShoppingCart(user.get().getShoppingCart());
                if(itemsToBuy.size() > 0){
                    model.addAttribute("neworder",false);
                } else {
                    model.addAttribute("neworder","");
                }
                model.addAttribute("order","");
                return "shoppingCart";
            } else {
                return "error";
            }
        } else {
            return "error";
        }       
    }

    @GetMapping("/items/{id}/remove")
    public String removeItemToBuy(Model model, @PathVariable Integer id){

        Optional<ItemToBuy> item = itemToBuyService.findById(id);

        if(item.isPresent()) {
            itemToBuyService.deleteById(id);
            model.addAttribute("order","");
            return "shoppingCart";
        }
        return "error";
    }
    
}
