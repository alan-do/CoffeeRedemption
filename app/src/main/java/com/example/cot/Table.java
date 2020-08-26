package com.example.cot;

import java.io.Serializable;

public class Table implements Serializable {
    private int id;
    private int number_of_seat;
    private int floor;

    public Table(int id, int number_of_seat, int floor) {
        this.id = id;
        this.number_of_seat = number_of_seat;
        this.floor = floor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber_of_seat() {
        return number_of_seat;
    }

    public void setNumber_of_seat(int number_of_seat) {
        this.number_of_seat = number_of_seat;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}

