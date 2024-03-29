package com.cs310.usclassifieds.model.datamodel;

import java.io.Serializable;

public class Contact implements Serializable {
    public String phone;
    public String email;
    public String instagram;
    public String snapchat;

    public Contact(String email) {
        this.email = email;
        this.phone = null;
        this.instagram = null;
        this.snapchat = null;
    }

    public Contact(String email, String phoneNumber) {
        this.email = email;
        this.phone = phoneNumber;
        this.instagram = null;
        this.snapchat = null;
    }

    // default constructor necessary to be written into firestore
    public Contact() {
        this.email = null;
        this.phone = null;
        this.instagram = null;
        this.snapchat = null;
    }
}
