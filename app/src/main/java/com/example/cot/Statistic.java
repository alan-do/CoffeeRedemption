package com.example.cot;

public class Statistic {
    private  int today_order;
    private int today_revenue;
    private int this_month_revenue;
    private int this_month_order;

    public Statistic(int today_order, int today_revenue, int this_month_revenue, int this_month_order) {
        this.today_order = today_order;
        this.today_revenue = today_revenue;
        this.this_month_revenue = this_month_revenue;
        this.this_month_order = this_month_order;
    }

    public int getToday_order() {
        return today_order;
    }

    public void setToday_order(int today_order) {
        this.today_order = today_order;
    }

    public int getToday_revenue() {
        return today_revenue;
    }

    public void setToday_revenue(int today_revenue) {
        this.today_revenue = today_revenue;
    }



    public int getThis_month_revenue() {
        return this_month_revenue;
    }

    public void setThis_month_revenue(int this_month_revenue) {
        this.this_month_revenue = this_month_revenue;
    }

    public int getThis_month_order() {
        return this_month_order;
    }

    public void setThis_month_order(int this_month_order) {
        this.this_month_order = this_month_order;
    }
}