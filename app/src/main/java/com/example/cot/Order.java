package com.example.cot;

import java.io.Serializable;

public class Order implements Serializable {
    private  int id;
    private int table_id;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order(int id, int table_id, String status) {
        this.id = id;
        this.table_id = table_id;
        this.status = status;
    }
}