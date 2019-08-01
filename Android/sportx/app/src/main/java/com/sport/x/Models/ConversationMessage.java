package com.sport.x.Models;

public class ConversationMessage {
    private String conversationMessageId;
    private String conversationId;
    private String senderEmail;
    private String message;
    private String type;
    private String file_path;
    private String date;

    public ConversationMessage(String conversationMessageId, String conversationId, String senderEmail, String message, String type, String file_path, String date) {
        this.conversationMessageId = conversationMessageId;
        this.conversationId = conversationId;
        this.senderEmail = senderEmail;
        this.message = message;
        this.type = type;
        this.file_path = file_path;
        this.date = date;
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
}
