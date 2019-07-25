package com.sport.x.Models;

public class Service_Provider {

    private String name;
    private String email;
    private String address;
    private String picture;
    private String contact;
    private String password;
    private String category;
    private Double userLat;
    private Double userLon;

    public Service_Provider( String name, String email, String address,String picture, String contact,  String password,  String category, Double userLat, Double userLon) {

        this.name = name;
        this.email = email;
        this.address = address;
        this.picture = picture;
        this.contact = contact;
        this.password = password;
        this.category = category;
        this.userLat = userLat;
        this.userLon = userLon;

    }


    public String getServiceProviderName() {
        return name;
    }

    public String getServiceProviderEmail() {
        return email;
    }

    public String getServiceProviderAddress() {
        return address;
    }

    public String getServiceProviderContact() {
        return contact;
    }

    public String getServiceProviderPicture() {
        return picture;
    }

    public String getServiceProviderPassword() {
        return password;
    }

    public String getServiceProviderCategory() {
        return category;
    }

    public Double getUserLat() {
        return userLat;
    }

    public Double getUserLon() {
        return userLon;
    }

}
