package es.webapp.webapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * conversión de la clase Stock en clase genérica para que cada subclase pueda definir su propia versión de Size
 */
@Entity
@Table(name = "tbl_stock")
public abstract class Stock<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String code;

    //private boolean selected;
    private Integer stock;

    @ManyToOne
    @JsonIgnore
    private Item item;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Size size;
    
    public Stock(){}

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setCode(String code){
        this.code=code;
    }

    public String getCode(){
        return code;
    }

    //public abstract void setSize(T size);
//
    //public abstract T getSize();

    public void setStock(Integer stock){
        this.stock = stock;
    }

    public Integer getStock(){
        return stock;
    }

    public void setItem(Item item){
        this.item = item;
    }

    public Item getItem(){
        return item;
    }

    public void setSize(Size size){
        this.size=size;
    }

    public Size getSize(){
        return size;
    }
}
