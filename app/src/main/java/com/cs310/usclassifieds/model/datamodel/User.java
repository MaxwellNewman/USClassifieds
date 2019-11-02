package com.cs310.usclassifieds.model.datamodel;

import java.util.HashMap;
import java.util.List;

public class User {
    public String passwordHash;
    public String username;
    public Contact contactInfo;
    public List<String> friends; // userIDs
    public List<Item> items;
    public String userId;

    public User(String username, String passwordHash, String email) {
        this.passwordHash = passwordHash;
        this.username = username;
        this.contactInfo = null;
        this.friends = null;
        this.items = null;
        this.userId = email;
    }

    public User() {
        this.passwordHash = null;
        this.username = null;
    }

    public String getPassword() {
        return passwordHash;
    }

    // Returns the user as a map, excluding any members that are null
    // Can be used to update a user in the database by filling in only
    // the values that need to be updated
    public HashMap<String, Object> toMap() {
        final HashMap<String, Object> map = new HashMap<>();

        map.put("username", username);
        map.put("passwordHash", passwordHash);
        if(contactInfo != null) {
            map.put("contactInfo", contactInfo);
        }
        if(friends != null) {
            map.put("friends", friends);
        }
        if(items != null) {
            map.put("items", items);
        }

        return map;
    }

}
