package es.webapp.webapp.model;

import javax.persistence.Entity;

@Entity
public class Clothes extends Stock<Size>{

    //private String size;

    public Clothes(){}

    /*@Override
    public void setSize(Size size){
        this.size=size.getLabel();
    }

    @Override
    public Size getSize(){
        return new Size(size);
    }*/
}
