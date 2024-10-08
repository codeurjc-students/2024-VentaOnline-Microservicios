package es.webapp.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import es.webapp.webapp.model.Item;
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
        model.addAttribute("sizeByDefault",false);
    }

    private void showSizes(Model model, Optional<Item> item){
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
            if(item.get().getStocks()[0] >= 5){model.addAttribute("stock1","S (Avaiable)");}else{model.addAttribute("stock1","Less than 5 items avaiable for size S");}  
        } else {
            model.addAttribute("stock1",0); 
        }
        if(item.get().getStocks() != null && item.get().getStocks()[1] != null){
            if(item.get().getStocks()[0] >= 5){model.addAttribute("stock2","M (Avaiable)");}else{model.addAttribute("stock2","Less than 5 items avaiable for size M");}  
        } else {
            model.addAttribute("stock2",0); 
        }
        if(item.get().getStocks() != null && item.get().getStocks()[2] != null){
            if(item.get().getStocks()[0] >= 5){model.addAttribute("stock3","L (Avaiable)");}else{model.addAttribute("stock3","Less than 5 items avaiable for size L");}    
        } else {
            model.addAttribute("stock3",0); 
        }
        if(item.get().getStocks() != null && item.get().getStocks()[3] != null){
            if(item.get().getStocks()[0] >= 5){model.addAttribute("stock4","XL (Avaiable)");}else{model.addAttribute("stock4","Less than 5 items avaiable for size XL");}  
        } else {
            model.addAttribute("stock4",0); 
        }
    }

    @GetMapping("/{id}/page")
    public String itemPage(Model model, @PathVariable Integer id){   
            
        model.addAttribute("status","");
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()) {
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
            
            showSizes(model,item);
            showStocks(model, item);
  
            model.addAttribute("type",item.get().getType());
            model.addAttribute("description",item.get().getDescription());
        } else {
            return "error";
        }
        return "product";
    }

    @PostMapping("/{id}/buy")
    public String itemBuy(Model model, Item itemUpdated, @PathVariable Integer id) throws IOException{

        /*Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            if(!imageField.isEmpty()){
                itemUpdated.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
            }
            
            itemService.update(item.get().getId(), itemUpdated);
            model.addAttribute("status","item updated");
            
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
            showSizes(model, item);
            showStocks(model, item);
            model.addAttribute("type",item.get().getType());
            model.addAttribute("description",item.get().getDescription());
            return "edition";
        } else {
            return "error";
        }*/
        return "product";
    }
    
}
