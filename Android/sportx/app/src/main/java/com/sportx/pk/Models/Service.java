package com.sportx.pk.Models;

public class Service {
    private String serviceId;
    private String serviceName;
    private String serviceImage;

    public Service(String serviceId, String serviceName, String serviceImage) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceImage = serviceImage;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceImage() {
        return serviceImage;
    }
}
