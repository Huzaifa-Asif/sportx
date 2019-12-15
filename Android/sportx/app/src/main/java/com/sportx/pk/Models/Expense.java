package com.sportx.pk.Models;


public class Expense {

    private String expenseId;
    private String expenseCategory;
    private int amount;
    private String serviceProviderEmail;
    private String date;
    private String description;

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
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

    public Expense(String expenseId, String expenseCategory, int amount, String serviceProviderEmail, String date, String description) {

        this.expenseId = expenseId;
        this.expenseCategory = expenseCategory;
        this.amount = amount;
        this.serviceProviderEmail = serviceProviderEmail;
        this.date = date;
        this.description = description;

    }
}

