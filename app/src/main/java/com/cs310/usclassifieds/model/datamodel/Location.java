package com.cs310.usclassifieds.model.datamodel;

public class Location {
    public String address;
    public double latitude;
    public double longitude;

    // necessary to be written into firestore
    public Location() {

    }

    public Location(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
