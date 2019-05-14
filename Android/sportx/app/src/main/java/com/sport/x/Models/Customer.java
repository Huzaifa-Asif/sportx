package com.sport.x.Models;

public class Customer {

    private String name;
    private String email;
    private String picture;
    private String contact;
    private String password;
    private Integer role;

    public Customer( String name, String email, String picture, String contact,  String password, Integer role) {

        this.name = name;
        this.email = email;
        this.picture = picture;
        this.contact = contact;
        this.password = password;
        this.role = role;
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

    public Integer getCustomerRole() {
        return role;
    }
}
