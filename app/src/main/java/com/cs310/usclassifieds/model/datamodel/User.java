package com.cs310.usclassifieds.model.datamodel;

import java.util.List;

public class User {
    public String passwordHash;
    public String username;
    public Contact contactInfo;
    public List<Integer> friends; // userIDs
    public List<Item> items;
    public int userId;

    public User(String username, String passwordHash) {
        this.passwordHash = passwordHash;
        this.username = username;
    }

    public User() {
        this.passwordHash = null;
        this.username = null;
    }
}
