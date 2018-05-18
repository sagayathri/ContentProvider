package com.gayathri.contentproviderdemo;

import java.io.Serializable;

/**
 * Created by Ragavan Raveendran on 20/03/2018.
 */

public class Product implements Serializable{
    private String id, name;
    private int price, quantity;

    public Product() {
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setQuantity(quantity);
    }

    public Product(String id,String name,int price,int quantity) {
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setQuantity(quantity);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toStringname(){
        return "ID : "+ getId()+
                "\n\n Name : "+ getName()+
                "\n\n Price : "+ getPrice()+
                "\n\n Quantity : "+ getQuantity();
    }
}
