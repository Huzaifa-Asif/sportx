package com.sport.x.Models;

public class ExpenseReport {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private int amount;

    public ExpenseReport( String name, int amount) {

        this.name = name;
        this.amount = amount;


    }

}
