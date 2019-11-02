package com.cs310.usclassifieds.model.manager;
import android.renderscript.Sampler;

import androidx.annotation.NonNull;

import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.SearchQuery;
import com.cs310.usclassifieds.model.datamodel.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static final String USERS_PATH = "users";
    public static final String USER_ID = "userId";
    private final FirebaseFirestore database;

    private static final String userDatabaseName = "Users";
    private static final String itemDatabaseName = "Items";

    private static final String userIdTrait = "userId";
    User user;

    public DataManager() {
        database = FirebaseFirestore.getInstance();
    }

    private boolean modifyUser(User user) {
        database.collection(USERS_PATH)
                .add(user);

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

    User getUser(String userId) {
        CollectionReference userRef = database.collection(USERS_PATH);
        Task<QuerySnapshot> query = userRef.whereEqualTo(USER_ID, userId).get();
        QuerySnapshot snapshot = query.getResult();

        final List<DocumentSnapshot> documents;
        try {
            documents = snapshot.getDocuments();
        } catch(NullPointerException e) {
            return null;
        }

        return documents.isEmpty() ?
                null :
                documents.get(0).toObject(User.class);
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
