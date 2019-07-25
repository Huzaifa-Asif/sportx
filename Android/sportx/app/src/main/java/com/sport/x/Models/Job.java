package com.sport.x.Models;

public class Job {
    private String jobId;
    private String date;
    private String state;
    private String bookingType;
    private String time;
    private String serviceProviderEmail;
    private String serviceProviderName;
    private String serviceProviderNumber;
    private String customerEmail;
    private String customerName;
    private String customerNumber;

    public Job(String jobId, String date, String state, String bookingType, String time, String serviceProviderEmail, String serviceProviderName, String serviceProviderNumber, String customerEmail, String customerName, String customerNumber) {
        this.jobId = jobId;
        this.date = date;
        this.state = state;
        this.bookingType = bookingType;
        this.time = time;
        this.serviceProviderEmail = serviceProviderEmail;
        this.serviceProviderName = serviceProviderName;
        this.serviceProviderNumber = serviceProviderNumber;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
    }

    public Job(String date, String state, String bookingType, String time, String serviceProviderEmail, String customerEmail, String serviceProviderName, String serviceProviderNumber, String customerName, String customerNumber) {
        this.date = date;
        this.bookingType = bookingType;
        this.state = state;
        this.time = time;
        this.serviceProviderEmail = serviceProviderEmail;
        this.serviceProviderName = serviceProviderName;
        this.serviceProviderNumber = serviceProviderNumber;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerNumber = customerNumber;

    }

    public String getJobId() {
        return jobId;
    }

    public String getDate() {
        return date;
    }

    public String getBookingType() {
        return bookingType;
    }

    public String getState() {
        return state;
    }

    public String getTime() {
        return time;
    }

    public String getServiceProviderEmail() {
        return serviceProviderEmail;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public String getServiceProviderNumber() {
        return serviceProviderNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }


}