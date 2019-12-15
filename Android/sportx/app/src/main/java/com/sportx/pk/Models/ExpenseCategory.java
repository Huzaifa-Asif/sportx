package com.sportx.pk.Models;

public class ExpenseCategory {

    public String getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public void setExpenseCategoryId(String expenseCategoryId) {
        this.expenseCategoryId = expenseCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceProviderEmail() {
        return serviceProviderEmail;
    }

    public void setServiceProviderEmail(String serviceProviderEmail) {
        this.serviceProviderEmail = serviceProviderEmail;
    }
    private String expenseCategoryId;
    private String name;
    private String serviceProviderEmail;

    public ExpenseCategory(String expenseCategoryId, String name, String serviceProviderEmail) {

        this.expenseCategoryId = expenseCategoryId;
        this.name = name;
        this.serviceProviderEmail = serviceProviderEmail;


    }

}
