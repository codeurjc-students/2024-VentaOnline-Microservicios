package es.webapp.webapp.model;

import javax.persistence.Entity;

@Entity
public class Clothes extends Stock<Clothes.Size>{

    public enum Size{
        S, M, L, XL;

        public String getSize(Size size){
            return size.name();
        }
    }

    private Size size;

    public Clothes(){}

    @Override
    public void setSize(Size size){
        this.size=size;
    }

    @Override
    public Size getSize(){
        return size;
    }
}
