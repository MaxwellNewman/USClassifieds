package com.cs310.usclassifieds.model.manager;
import com.cs310.usclassifieds.model.datamodel.User;

public class DataManager {

    // Create friend request from user1 to user2
    public void createFriendRequest(String user1, String user2) {

    }
    
    // Remove friend request from user1 to user2
    public void resolveFriendRequest(String user1, String user2) {

    }

    // Adds user1 to user2's friend list and vice versa
    public void addFriend(String user1, String user2) {

    }

    boolean setUser(User user) {
        return true;
    }
    
    // Returns a user, given the userId
    public User getUser(int userId) {
        User user = new User();
        user.userId = userId;

        return user;
    }
    
    // Returns a user, given the username
    public User getUser(String username) {
        return new User();
    }

    // Returns true if a user is in the database, false otherwise
    public boolean userExists(String username) {
        return getUser(username) != null;
    }

}
