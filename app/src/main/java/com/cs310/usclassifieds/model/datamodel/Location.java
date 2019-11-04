package com.cs310.usclassifieds.model.datamodel;

public class Location {
    public String address;
    public double latitude;
    public double longitude;

    public Location(String add, double lat, double lon) {
        this.address = add;
        this.latitude = lat;
        this.longitude = lon;
    }

    public Location() {
        this.address = null;
        this.latitude = -1;
        this.longitude = -1;
    }
}
