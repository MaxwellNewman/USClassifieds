package com.cs310.usclassifieds.model.manager;
import android.renderscript.Sampler;

import androidx.annotation.NonNull;

import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.SearchQuery;
import com.cs310.usclassifieds.model.datamodel.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    // private final FirebaseFirestore firstore; Use instead?
    private final DatabaseReference reference = database.getReference();

    private static final String us`erDatabaseName = "Users";
    private static final String itemDatabaseName = "Items";

    private static final String userIdTrait = "userId";
    User user;

    private boolean modifyUser(User user) {
        final DatabaseReference userRef = reference.child(userDatabaseName);
        userRef.child(user.contactInfo.email).setValue(user.toMap());

        return true;
    }

    // Create friend request from user1 to user2, returns false if unsuccessful
    boolean createFriendRequest(String user1, String user2) {
        return true;
    }
    
    // Remove friend request from user1 to user2, returns false if unsuccessful
    boolean resolveFriendRequest(String user1, String user2) {
        return true;
    }

    // Adds user1 to user2's friend list and vice versa, returns false if unsuccessful
    boolean addFriend(String user1, String user2) {
        return true;
    }

    // Adds user to the database
    boolean addUser(User user) {
        return modifyUser(user);
    }

    boolean updateUser(User user) {
        return modifyUser(user);
    }

    class Val implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            user = dataSnapshot.getValue(User.class);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            System.out.println(databaseError.getMessage());
        }
    }
    
    // Returns a user, given the userId
    User getUserByEmail(final String email) {
        final Query resultRef = reference.child(userDatabaseName)
                .child(email)
                .limitToFirst(1);

        resultRef.addListenerForSingleValueEvent(new Val());

        return user;
    }
    
    // Returns a user, given the username
    User getUserByUsername(String username) {
        return new User();
    }

    // Returns true if a user is in the database, false otherwise
    public boolean userExists(String email) {
        return getUserByEmail(email) != null;
    }

    List<User> searchUsers(SearchQuery query) {
        List<User> users = new ArrayList<User>();
        // add logic to search users
        return users;
    }

    List<Item> searchItems(SearchQuery query) {
        List<Item> items = new ArrayList<Item>();
        // add logic to search items
        return items;
    }

    boolean addListing(Item i) {
        return true;
    }

    boolean resolveSale(Item i) {
        return true;
    }

    boolean updateItem(int itemId, Item i) {
        return true;
    }

}
