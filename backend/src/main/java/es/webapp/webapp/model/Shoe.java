package es.webapp.webapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class Shoe extends Stock<Shoe.Shoe_Size> {

    public enum Shoe_Size{
        SIZE_36, SIZE_37, SIZE_38, SIZE_39, SIZE_40, SIZE_41, SIZE_42, SIZE_43, SIZE_44, SIZE_45, SIZE_46, SIZE_47;

        public String getsize(Shoe_Size size) {
            return size.name().substring(5);
        } 
        
        public List<String> getSizes(){
            List<String> pairs = new ArrayList<>();
            for(Shoe_Size size: Shoe_Size.values()){
                pairs.add(getsize(size));
            }
            return pairs;
        } 
    }

    private Shoe_Size size;
    
    public Shoe(){}

    @Override
    public void setSize(Shoe_Size size) {
        this.size=size;
    }

    @Override
    public Shoe_Size getSize() {
        return size;
    }
}
