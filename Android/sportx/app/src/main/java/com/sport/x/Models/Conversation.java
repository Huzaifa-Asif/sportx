package com.sport.x.Models;

public class Conversation {
    private String conversationId;
    private String customerEmail;
    private String serviceProviderEmail;
    private String state;
    private String date;

    public Conversation(String conversationId, String customerEmail, String serviceProviderEmail, String state, String date) {
        this.conversationId = conversationId;
        this.customerEmail = customerEmail;
        this.serviceProviderEmail = serviceProviderEmail;
        this.state = state;
        this.date = date;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getConversationCustomerEmail() {
        return customerEmail;
    }

    public String getConversationServiceProviderEmail() {
        return serviceProviderEmail;
    }

    public String getConversationState() {
        return state;
    }

    public String getConversationDate() {
        return date;
    }
}
