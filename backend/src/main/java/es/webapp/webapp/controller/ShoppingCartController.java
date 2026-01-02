package es.webapp.webapp.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /*@ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null) {   
            String username = (String) session.getAttribute("user");
            if(username != null){
                Optional<User> user = userService.findByUsername(username);
                model.addAttribute("username",user.get().getUsername());
                model.addAttribute("admin", user.get().getRol().equals("ADMIN"));
                model.addAttribute("user", user.get().getRol().equals("USER"));
                model.addAttribute("id", user.get().getId());
                model.addAttribute("logged",true);
            } else {
                model.addAttribute("username","anonymous");
                model.addAttribute("admin", "");
                model.addAttribute("user", "");
                model.addAttribute("id", 6);
                model.addAttribute("logged",false);
            }
        }
    }*/

    @GetMapping("/page")
    public String shoppingCartPage(Model model, HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if(session != null) {   
            String username = (String) session.getAttribute("user");
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

        if(itemToBuy.isPresent()) {
            itemToBuyService.deleteById(id);
        }
    
        itemToBuyService.updateShoppingCart(itemToBuy.get());


        List<ItemToBuy> itemsToBuy = itemToBuyService.findByShoppingCart(user.getShoppingCart());
        if(itemsToBuy.size() > 0){
            model.addAttribute("neworder",false);
        } else {
            model.addAttribute("neworder",true);
        }
        return "shoppingCart";
    }
    
}
