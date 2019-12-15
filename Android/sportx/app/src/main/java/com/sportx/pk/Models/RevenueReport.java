package com.sportx.pk.Models;

public class RevenueReport {


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

    public RevenueReport(String name, int amount) {

        this.name = name;
        this.amount = amount;


    }

}
