package com.example.cot;

public class OrderDetail {
    private int id;
    private int product_id;
    private int quantity;
    private int order_id;
    private String product_name;
    private int product_price;

    public OrderDetail(int id, int product_id, int quantity, int order_id, String product_name, int product_price) {
        this.id = id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.order_id = order_id;
        this.product_name = product_name;
        this.product_price = product_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }
}

