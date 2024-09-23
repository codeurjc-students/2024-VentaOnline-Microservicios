package es.webapp.webapp.controller;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.service.ItemService;

@RestController
@RequestMapping("/databases")
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public Page<Item> getItems(Pageable page){
        return itemService.findAll(page);
    }

    @GetMapping("/items/{name}")
    public Page<Item> getItemsByName(@PathVariable String name, Pageable page){
        return itemService.findByName(name, page);
    }

    @GetMapping("/items/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable Integer id) throws SQLException {

        Optional<Item> item = itemService.findById(id);

        if(item.isPresent() && item.get().getImageFile() != null){
            Resource file = new InputStreamResource(item.get().getImageFile().getBinaryStream());
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .contentLength(item.get().getImageFile().length())
                .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
