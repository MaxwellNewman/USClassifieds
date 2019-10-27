package com.cs310.usclassifieds.model.datamodel;

import java.util.List;

public class User {
    private String passwordHash;
    private String username;
    private Contact contactInfo;
    private List<Integer> friends; // userIDs
    private List<Item> items;
    private int userId;

    public User(String username, String passwordHash) {
        this.passwordHash = passwordHash;
        this.username = username;
    }

    public User() {
        this.passwordHash = null;
        this.username = null;
    }

    public String getPassword() {
        return passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public Contact getContactInfo() {
        return contactInfo;
    }

    public List<Integer> getFriends() {
        return friends;
    }

    public List<Item> getItems() {
        return items;
    }
}
