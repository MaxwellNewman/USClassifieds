package com.cs310.usclassifieds.model.datamodel;

public class Contact {
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
}
