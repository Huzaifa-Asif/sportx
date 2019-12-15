package com.sportx.pk.Models;

public class LiveStream {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceProviderEmail() {
        return serviceProviderEmail;
    }

    public void setServiceProviderEmail(String serviceProviderEmail) {
        this.serviceProviderEmail = serviceProviderEmail;
    }

    public String getServiceProvideName() {
        return serviceProvideName;
    }

    public void setServiceProvideName(String serviceProvideName) {
        this.serviceProvideName = serviceProvideName;
    }

    public String getServiceProviderPicture() {
        return serviceProviderPicture;
    }

    public void setServiceProviderPicture(String serviceProviderPicture) {
        this.serviceProviderPicture = serviceProviderPicture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getOngoing() {
        return ongoing;
    }

    public void setOngoing(Boolean ongoing) {
        this.ongoing = ongoing;
    }

    String id,serviceProviderEmail,serviceProvideName,serviceProviderPicture,date,time;
    Boolean ongoing;
    public LiveStream(String id,String serviceProviderEmail,String serviceProvideName,String serviceProviderPicture,String date,String time,Boolean ongoing)
    {
        this.id=id;
        this.serviceProviderEmail=serviceProviderEmail;
        this.serviceProvideName=serviceProvideName;
        this.serviceProviderPicture=serviceProviderPicture;
        this.date=date;
        this.time=time;
        this.ongoing=ongoing;
    }
}
