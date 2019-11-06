package com.cs310.usclassifieds.model.datamodel;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable {
    // Input fields for Create Account
    public String userId;
    public String fullName;
    public String username;
    public Contact contactInfo;
    public String profileDescription;
    public Uri imageUri;
    public String imageUrl;
    public List<String> friendRequests;
    public int sales;

    public List<String> friends; // userIDs

    public User(String userId, String fullName, String username, String email, String phoneNumber, String profileDescription, Uri uri) {
        this.username = username;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.contactInfo = new Contact(email);
        this.profileDescription = profileDescription;
        this.imageUri = uri;

        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        sales = 0;
    }

    public User(String username, String email, String userId) {
        this.username = username;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.contactInfo = new Contact(email);
        this.profileDescription = profileDescription;

        this.friends = new ArrayList<>();
    }

    public User() {
        this.username = null;
    }

    public void addFriend(String friendId) {
        if(friends == null) {
            friends = new ArrayList<>();
        }

        if(!friends.contains(friendId)) {
            friends.add(friendId);
        }
    }

    public void addFriendRequest(String friendId) {
        if(friendRequests == null) {
            friendRequests = new ArrayList<>();
        }

        if(friends == null) {
            friends = new ArrayList<>();
        }

        if(!friendRequests.contains(friendId) && !friends.contains(friendId)) {
            friendRequests.add(friendId);
        }
    }

    public void removeFriendRequest(String friendId) {
        if(friendRequests == null) {
            friendRequests = new ArrayList<>();
        }

        if(friendRequests.contains(friendId)) {
            friendRequests.remove(friendId);
        }
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

        return map;
    }

    public boolean isFriend(String userId) {
        if(friends == null) {
            friends = new ArrayList<>();
        }

        return friends.contains(userId);
    }

    public boolean hasFriendRequest(String userId) {
        if(friendRequests == null) {
            friendRequests = new ArrayList<>();
        }

        return friendRequests.contains(userId);
    }

}
