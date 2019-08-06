package com.sport.x.Models;

public class ConversationMessage {
    private String conversationMessageId;
    private String conversationId;
    private String senderEmail;
    private String message;
    private String type;
    private String file_path;
    private String date;



    private String customerName;
    private String serviceProviderName;
    private String customerPicture;
    private String serviceProviderPicture;
    private String customerEmail;
    private String serviceProviderEmail;

    public ConversationMessage(String conversationMessageId, String conversationId, String senderEmail, String message, String type, String file_path, String date,
                               String customerName,String serviceProviderName, String customerPicture,String serviceProviderPicture, String customerEmail,String serviceProviderEmail) {
        this.conversationMessageId = conversationMessageId;
        this.conversationId = conversationId;
        this.senderEmail = senderEmail;
        this.message = message;
        this.type = type;
        this.file_path = file_path;
        this.date = date;
        this.customerName=customerName;
        this.serviceProviderName=serviceProviderName;
        this.customerPicture=customerPicture;
        this.serviceProviderPicture=serviceProviderPicture;
        this.customerEmail=customerEmail;
        this.customerEmail=customerEmail;
    }

    public String getConversationMessageId() {
        return conversationMessageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getConversationSenderEmail() {
        return senderEmail;
    }

    public String getConversationMessage() {
        return message;
    }

    public String getConversationType() {
        return type;
    }

    public String getConversationFilePath() { return file_path; }

    public String getConversationDate() { return date; }
    public String getCustomerName() {
        return customerName;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public String getCustomerPicture() {
        return customerPicture;
    }

    public String getServiceProviderPicture() {
        return serviceProviderPicture;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getServiceProviderEmail() {
        return serviceProviderEmail;
    }
}
