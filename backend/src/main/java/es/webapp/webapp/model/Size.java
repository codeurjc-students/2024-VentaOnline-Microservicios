package es.webapp.webapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.webapp.webapp.sizeFactoryMethod.SizeFactory;

@Entity
public class Size implements SizeFactory{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String code;

    private String label;

    
    @JsonIgnore
    @OneToOne(mappedBy = "size")
    private Stock<?> stock;

    public Size(){}

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setLabel(String label){
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    //@Override
    //public String toString() {
    //    return label;
    //}

    public void setStock(Stock<?> stock){
        this.stock=stock;
    }

    public Stock<?> getStock(){
        return stock;
    }

    public void setCode(String code){
        this.code=code;
    }

    public String getCode(){
        return code;
    }
}
