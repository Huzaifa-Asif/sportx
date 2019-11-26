package com.sport.x.Models;

public class BookingSettings {
    public String getBookingSettingsId() {
        return bookingSettingsId;
    }

    public void setBookingSettingsId(String bookingSettingsId) {
        this.bookingSettingsId = bookingSettingsId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTotalGrounds() {
        return totalGrounds;
    }

    public void setTotalGrounds(int totalGrounds) {
        this.totalGrounds = totalGrounds;
    }

    public String getServiceProviderEmail() {
        return serviceProviderEmail;
    }

    public void setServiceProviderEmail(String serviceProviderEmail) {
        this.serviceProviderEmail = serviceProviderEmail;
    }

    public boolean isWholeDayBookingAllowed() {
        return wholeDayBookingAllowed;
    }

    public void setWholeDayBookingAllowed(boolean wholeDayBookingAllowed) {
        this.wholeDayBookingAllowed = wholeDayBookingAllowed;
    }

    public int getWholeDayBookingPrice() {
        return wholeDayBookingPrice;
    }

    public void setWholeDayBookingPrice(int wholeDayBookingPrice) {
        this.wholeDayBookingPrice = wholeDayBookingPrice;
    }

    private String bookingSettingsId;
    private int amount;
    private String openingTime;
    private String closingTime;
    private int duration;
    private int totalGrounds;
    private String serviceProviderEmail;
    private boolean wholeDayBookingAllowed;
    private int wholeDayBookingPrice;

    public BookingSettings(String bookingSettingsId,int amount,String openingTime,String closingTime,int duration,int totalGrounds,String serviceProviderEmail, boolean wholeDayBookingAllowed,int wholeDayBookingPrice) {
        this.bookingSettingsId=bookingSettingsId;
        this.amount=amount;
        this.openingTime=openingTime;
        this.closingTime=closingTime;
        this.duration=duration;
        this.totalGrounds=totalGrounds;
        this.serviceProviderEmail=serviceProviderEmail;
        this.wholeDayBookingAllowed=wholeDayBookingAllowed;
        this.wholeDayBookingPrice=wholeDayBookingPrice;
    }


}

