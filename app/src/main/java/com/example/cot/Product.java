package com.example.cot;

import java.io.Serializable;

public class Product implements Serializable {
    private int Id;
    private  String Name;
    private  int Price;
    private String Producttype;
    private String Img;
    private String URL;

    public Product(int id, String name, int price,String producttype, String img) {
        Id = id;
        Name = name;
        Price = price;
        Producttype = producttype;
        Img = img;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }


    public String getProducttype() {
        return Producttype;
    }

    public void setProducttype(String producttype) {
        Producttype = producttype;
    }
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Product(int id, String name, int price, String URL) {
        Id = id;
        Name = name;
        Price = price;
//        this.description = description;
        this.URL = URL;
    }
}