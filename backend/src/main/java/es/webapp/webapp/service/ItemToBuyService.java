package es.webapp.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.repository.ItemToBuyRepo;

@Service
public class ItemToBuyService {

    @Autowired
    private ItemToBuyRepo itemToBuyRepo;

    public void save(ItemToBuy item){
        itemToBuyRepo.save(item);
    }
    
}
