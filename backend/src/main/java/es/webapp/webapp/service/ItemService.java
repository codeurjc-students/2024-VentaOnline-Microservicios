package es.webapp.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.repository.ItemRepo;

@Service
public class ItemService {
    
    @Autowired
    private ItemRepo itemRepo;

    public Page<Item> findAll(Pageable page){
        return itemRepo.findAll(page);
    }

    public Optional<Item> findById(Integer id){
        return itemRepo.findById(id);
    }

    public Page<Item> findByName(String name, Pageable page){
        return itemRepo.findByName(name, page);
    }
}
