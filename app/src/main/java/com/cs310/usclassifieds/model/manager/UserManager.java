package com.cs310.usclassifieds.model.manager;
import com.cs310.usclassifieds.model.model;

public class UserManager {
    private DataManager dataManager;

    public UserManager(DataManager dm) {
        this.dataManager = dm;
    }

    // Sends friend request from u1 to u2
    public void sendFriendReuest(User u1, User u2) {

    }

    // Accepts friend request from u1 to u2
    public void acceptFriendRequest(User u1, User u2) {

    }

    // Returns a profile given a user ID
    public User loadProfile(Integer userID) {
        return new User();
    }
}
