package es.webapp.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.Direction;
import es.webapp.webapp.repository.DirectionRepo;

@Service
public class DirectionService {

    @Autowired
    private DirectionRepo directionRepo;

    public Direction save(Direction address){
        return directionRepo.save(address);
    }
    
}
