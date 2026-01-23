package es.webapp.webapp.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.Direction;
import es.webapp.webapp.repository.DirectionRepo;

@Service
public class DirectionService {

    @Autowired
    private DirectionRepo directionRepo;

    public Direction save(Direction address){
        address.setCode(UUID.randomUUID().toString().toUpperCase().substring(0, 7));  
        return directionRepo.save(address);
    }

    public Optional<Direction> findById(Integer id){
        return directionRepo.findById(id);
    }
    
}
