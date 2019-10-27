package com.cs310.usclassifieds.model.datamodel;

import java.util.List;

public class User {
    private String passwordHash;
    public String username;
    public Contact contactInfo;
    public List<Integer> friends; // userIDs
    public List<Item> items;

    public String getPassword() {
        return passwordHash;
    }
}
