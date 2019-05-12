package com.sport.x.Models;

public class Complain {
    private String complainId;
    private String complainMessage;
    private String userName;
    private String userImage;

    public Complain(String complainId, String complainMessage, String userName, String userImage) {
        this.complainId = complainId;
        this.complainMessage = complainMessage;
        this.userName = userName;
        this.userImage = userImage;
    }

    public String getComplainId() {
        return complainId;
    }

    public String getComplainMessage() {
        return complainMessage;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }
}
