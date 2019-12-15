package com.sport.x.Models;

public class RevenueCategory {

    private String revenueCategoryId;
    private String name;
    private String serviceProviderEmail;

    public String getRevenueCategoryId() {
        return revenueCategoryId;
    }

    public void setRevenueCategoryId(String revenueCategoryId) {
        this.revenueCategoryId = revenueCategoryId;
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

    public RevenueCategory(String revenueCategoryId, String name, String serviceProviderEmail) {

        this.revenueCategoryId = revenueCategoryId;
        this.name = name;
        this.serviceProviderEmail = serviceProviderEmail;


    }

}
