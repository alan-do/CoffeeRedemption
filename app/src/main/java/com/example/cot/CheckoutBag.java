package com.example.cot;

import java.io.Serializable;

public class CheckoutBag implements Serializable {
    private int id;
    private String name;
    private  int price;
    private int type;
    private String url;
    private String sl;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public CheckoutBag(int id, String name, int price, int type, String url, String sl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.url = url;
        this.sl = sl;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }
}
