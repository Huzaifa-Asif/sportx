package com.sport.x.Models;


public class Revenue {

    private String revenueId;
    private String revenueCategory;
    private int amount;
    private String serviceProviderEmail;
    private String date;
    private String description;

    public String getrevenueId() {
        return revenueId;
    }

    public void setrevenueId(String revenueId) {
        this.revenueId = revenueId;
    }

    public String getrevenueCategory() {
        return revenueCategory;
    }

    public void setrevenueCategory(String revenueCategory) {
        this.revenueCategory = revenueCategory;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getServiceProviderEmail() {
        return serviceProviderEmail;
    }

    public void setServiceProviderEmail(String serviceProviderEmail) {
        this.serviceProviderEmail = serviceProviderEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Revenue(String revenueId, String revenueCategory, int amount, String serviceProviderEmail, String date, String description) {

        this.revenueId = revenueId;
        this.revenueCategory = revenueCategory;
        this.amount = amount;
        this.serviceProviderEmail = serviceProviderEmail;
        this.date = date;
        this.description = description;

    }
}

