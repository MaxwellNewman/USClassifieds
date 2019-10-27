package com.cs310.usclassifieds.model.manager;
import com.cs310.usclassifieds.model.datamodel;

public class DataManager {

    // Create friend request from user1 to user2
    public void createFriendRequest(String user2, String user2) {

    }
    
    // Remove friend request from user1 to user2
    public void resolveFriendRequest(String user1, String user2) {

    }

    // Adds user1 to user2's friend list and vice versa
    public void addFriend(String user1, String user2) {

    }
    
    // Returns a user, given the userID 
    public User getUser(Integer userID) {
        User user = new User();
        user.userID = userID;

        return user;
    }
    
}
