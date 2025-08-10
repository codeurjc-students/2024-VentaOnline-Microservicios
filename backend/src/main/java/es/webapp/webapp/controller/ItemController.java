package es.webapp.webapp.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemService;
import es.webapp.webapp.service.UserService;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    /*@ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("user");
        //model.addAttribute("username",username);
        if(username != null){
            Optional<User> user = userService.findByUsername(username);
            model.addAttribute("username",user.get().getUsername());
            model.addAttribute("admin", user.get().getRol().equals("ADMIN"));
            model.addAttribute("user", user.get().getRol().equals("USER"));
            model.addAttribute("id", user.get().getId());
            model.addAttribute("logged",true);
        } else {
            model.addAttribute("username","anonymous");
            model.addAttribute("logged",false);
        }
    }*/

    @GetMapping("/{id}/page")
    public String itemPage(Model model, @PathVariable Integer id){   
            
        model.addAttribute("status","");
        Optional<Item> item = itemService.findById(id);
        if(!item.isPresent()) {
            return "error";
        }
        return "product";
    }

    @PostMapping("/{id}/purchase")
    public String itemBuy(Model model, ItemToBuy itemToBuy, @PathVariable Integer id, HttpServletRequest request) throws IOException{
      
        HttpSession session = request.getSession(false);
        if(session != null) {   
            String username = (String) session.getAttribute("user");
            Optional<User> user = userService.findByUsername(username);
            if(user.isPresent()){
                if(itemService.addToCart(user.get().getUsername(), id, itemToBuy)){
                    model.addAttribute("status","item successfully added to the cart");
                    return "product";
                }else{
                    model.addAttribute("status","failed to add the item to the cart");
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
        return "redirect:/";

    }
}
