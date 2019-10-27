package com.cs310.usclassifieds.model.manager;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.Query;
import com.cs310.usclassifieds.model.datamodel.User;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    // Create friend request from user1 to user2, returns false if unsuccessful
    public boolean createFriendRequest(String user1, String user2) {
        return true;
    }
    
    // Remove friend request from user1 to user2, returns false if unsuccessful
    public boolean resolveFriendRequest(String user1, String user2) {
        return true;
    }

    // Adds user1 to user2's friend list and vice versa, returns false if unsuccessful
    public boolean addFriend(String user1, String user2) {
        return true;
    }

    // Adds user to the database
    public boolean addUser(User user) {
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

    public List<User> searchUsers(Query query) {
        List<User> users = new ArrayList<User>();
        // add logic to search users
        return users;
    }

    public List<Item> searchItems(Query query) {
        List<Item> items = new ArrayList<Item>();
        // add logic to search items
        return items;
    }

    public boolean addListing(Item i) {
        return true;
    }

    public boolean resolveSale(Item i) {
        return true;
    }

    public boolean updateItem(int itemId, Item i) {
        return true;
    }

}
