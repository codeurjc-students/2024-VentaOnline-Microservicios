package es.webapp.webapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class Shoe extends Stock<Shoe.Size> {

    public enum Size{
        SIZE_36, SIZE_37, SIZE_38, SIZE_39, SIZE_40, SIZE_41, SIZE_42, SIZE_43, SIZE_44, SIZE_45, SIZE_46, SIZE_47;

        public String getsize() {
            return name().substring(5);
        } 
        
        public List<String> getSizes(){
            List<String> pairs = new ArrayList<>();
            for(Size size: Size.values()){
                pairs.add(size.getsize());
            }
            return pairs;
        } 
    }

    private Size size;
    
    public Shoe(){}

    @Override
    public void setSize(Size size) {
        //this.size=Size.valueOf("SIZE_"+size.getsize());
        this.size=size;
    }

    @Override
    public Size getSize() {
        return size;
    }
}
