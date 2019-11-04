package com.cs310.usclassifieds.model.datamodel;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    public String username;
    public String profilePicture;
    public Contact contactInfo;
    public List<String> friends; // userIDs
    public List<Item> items;
    public String userId;

    public User(String username, String email, String userId) {
        this.username = username;
        this.contactInfo = new Contact(email);
        this.friends = null;
        this.items = null;
        this.userId = userId;
    }

    public User() {
        this.username = null;
    }

    public void addFriend(String friendId) {
        if(friends == null) {
            friends = new ArrayList<>();
        }

        friends.add(friendId);
    }

    // Returns the user as a map, excluding any members that are null
    // Can be used to update a user in the database by filling in only
    // the values that need to be updated
    public HashMap<String, Object> toMap() {
        final HashMap<String, Object> map = new HashMap<>();

        map.put("username", username);
        if(contactInfo != null) {
            map.put("email", contactInfo.email);
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
