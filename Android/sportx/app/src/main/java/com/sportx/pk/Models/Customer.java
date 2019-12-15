package com.sportx.pk.Models;

public class Customer {

    private String name;
    private String email;
    private String picture;
    private String contact;
    private String password;


    public Customer( String name, String email, String picture, String contact,  String password) {

        this.name = name;
        this.email = email;
        this.picture = picture;
        this.contact = contact;
        this.password = password;

    }


    public String getCustomerName() {
        return name;
    }

    public String getCustomerEmail() {
        return email;
    }

    public String getCustomerContact() {
        return contact;
    }

    public String getCustomerPicture() {
        return picture;
    }

    public String getCustomerPassword() {
        return password;
    }

}
