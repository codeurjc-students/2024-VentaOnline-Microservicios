package es.webapp.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private boolean selected;

    private String size;

    private Integer stock;

    @ManyToOne
    private Item item;

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public boolean getSelected(){
        return selected;
    }

    public void setSize(String size){
        this.size = size;
    }

    public String getSize(){
        return size;
    }

    public void setSotck(Integer stock){
        this.stock = stock;
    }

    public Integer getStock(){
        return stock;
    }
}
