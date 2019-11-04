package com.cs310.usclassifieds.model.datamodel;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    // Input fields for Create Account
    public String userId;
    public String fullName;
    public String username;
    public Contact contactInfo;
    public String profileDescription;

    public String profilePicture;
    public List<String> friends; // userIDs
    public List<Item> items;

    public User(String userId, String fullName, String username, String email, String phoneNumber, String profileDescription) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.contactInfo = new Contact(email, phoneNumber);
        this.profileDescription = profileDescription;
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

        map.put("userId", this.userId);
        map.put("full_name", this.fullName);
        map.put("username", this.username);
        map.put("profile_description", this.profileDescription);

        if(contactInfo != null) {
            map.put("email", contactInfo.email);
            map.put("phone_number", contactInfo.phone);
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
