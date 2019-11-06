package com.cs310.usclassifieds.model.manager;
import com.cs310.usclassifieds.model.datamodel.User;

import java.util.List;

public class UserManager {
    private DataManager dataManager;

    public UserManager(DataManager dm) {
        this.dataManager = dm;
    }

    // Sends friend request from u1 to u2
    public void sendFriendRequest(User u1, User u2) {
        dataManager.createFriendRequest(u1, u2);
    }

    // Accepts friend request from u1 to u2
    public void acceptFriendRequest(User u1, User u2) {
        dataManager.addFriend(u1, u2);
    }

    // Decline friend request from u1 to u2
    public void declineFriendRequest(User u1, User u2) {
        dataManager.declineFriendRequest(u1, u2);
    }

    // Returns a profile given a user ID
    public User loadProfile(String userId) {
        return dataManager.getUser(userId);
    }

    // Gets the friends of a specific user
    public List<User> getFriendsOf(String userId) {
        return dataManager.getFriendsOf(userId);
    }

    public List<User> getFriendRequestsOf(User user) {
        return dataManager.getUsers(user.friendRequests);
    }

    // Adds user to the database
    public void addUser(User user) {
        dataManager.addUser(user);
    }
}