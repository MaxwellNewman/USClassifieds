package com.cs310.usclassifieds.model.manager;
import com.cs310.usclassifieds.model.datamodel.User;

import java.util.List;

public class UserManager {
    private DataManager dataManager;

    public UserManager(DataManager dm) {
        this.dataManager = dm;
    }

    // Sends friend request from u1 to u2
    public void sendFriendReuest(User u1, User u2) {
        dataManager.createFriendRequest(u1.username, u2.username);
    }

    // Accepts friend request from u1 to u2
    public void acceptFriendRequest(User u1, User u2) {
        dataManager.resolveFriendRequest(u1.username, u2.username);
        dataManager.addFriend(u1.username, u2.username);
    }

    // Decline friend request from u1 to u2
    public void declineFriendRequest(User u1, User u2) {
        dataManager.resolveFriendRequest(u1.username, u2.username);
    }

    // Returns a profile given a user ID
    public User loadProfile(String userId) {
        return dataManager.getUser(userId);
    }

    // Gets the friends of a specific user
    public List<User> getFriendsOf(String username) {
        return dataManager.getFriendsOf(username);
    }
}