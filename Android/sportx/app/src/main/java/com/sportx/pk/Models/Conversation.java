package com.sportx.pk.Models;

public class Conversation {
    private String conversationId;
    private String customerEmail;
    private String customerName;
    private String customerPicture;
    private String serviceProviderEmail;
    private String serviceProviderName;
    private String serviceProviderPicture;
    private String state;
    private String date;
    private String time;
    private int user_role;

    public Conversation(String conversationId, String customerEmail, String customerName, String customerPicture, String serviceProviderEmail, String serviceProviderName, String serviceProviderPicture, String state, String date, String time, int user_role) {
        this.conversationId = conversationId;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerPicture = customerPicture;
        this.serviceProviderEmail = serviceProviderEmail;
        this.serviceProviderName = serviceProviderName;
        this.serviceProviderPicture = serviceProviderPicture;
        this.state = state;
        this.date = date;
        this.time=time;
        this.user_role = user_role;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getConversationCustomerEmail() {
        return customerEmail;
    }

    public String getConversationCustomerName() {
        return customerName;
    }

    public String getConversationCustomerPicture() {
        return customerPicture;
    }

    public String getConversationServiceProviderEmail() {
        return serviceProviderEmail;
    }

    public String getConversationServiceProviderName() {
        return serviceProviderName;
    }

    public String getConversationServiceProviderPicture() {
        return serviceProviderPicture;
    }

    public String getConversationState() {
        return state;
    }

    public String getConversationDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getConversationUserRole() {
        return user_role;
    }
}
