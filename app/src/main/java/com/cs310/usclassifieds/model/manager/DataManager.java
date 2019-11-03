package com.cs310.usclassifieds.model.manager;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cs310.usclassifieds.SignInActivity;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.SearchQuery;
import com.cs310.usclassifieds.model.datamodel.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    public static final String USERS_PATH = "users";
    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String ITEMS_PATH = "items";
    private FirebaseFirestore database;

    public DataManager() {
        database = FirebaseFirestore.getInstance();
    }

    private boolean modifyUser(User user) {
        Task<Void> ref = database.collection(USERS_PATH)
                .document(user.userId)
                .set(user);

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
    public boolean addUser(User user) {
        return modifyUser(user);
    }

    boolean updateUser(User user) {
        return modifyUser(user);
    }

    public User getUser(String userId) {
        CollectionReference userRef = database.collection(USERS_PATH);
        Task<QuerySnapshot> query = userRef.whereEqualTo(USER_ID, userId).get();

        while(!query.isComplete()) {
            // waiting for query to finish
        }

        final QuerySnapshot snapshot = query.getResult();
        final List<DocumentSnapshot> documents;
        try {
            documents = snapshot.getDocuments();

            return documents.isEmpty() ?
                    null :
                    documents.get(0).toObject(User.class);
        } catch(Exception e) {
            Log.e("exception get user: " + userId, e.getMessage());
            return null;
        }
    }

    // Gets a list of users by making queries simultaneously instead of waiting for each
    // query to completely finish before starting the next one
    public List<User> getUsers(List<String> userIds) {
        final List<Task<QuerySnapshot> > queries = new ArrayList<>();
        final List<User> users = new ArrayList<>();

        for(int i=0; i<userIds.size(); ++i) {
            queries.add(database.collection(USERS_PATH)
                            .whereEqualTo(USER_ID, userIds.get(i))
                            .get()
            );
        }

        for(int i=0; i<queries.size(); ++i) {
            while(!queries.get(i).isComplete()) {
                //waiting for ith query to finish
            }

            final QuerySnapshot snapshot = queries.get(i).getResult();
            final List<DocumentSnapshot> documents;
            try {
                documents = snapshot.getDocuments();

                users.add(documents.isEmpty() ?
                        null :
                        documents.get(0).toObject(User.class)
                );
            } catch(Exception e) {
                Log.e("error get users: " + userIds.get(i), e.getMessage());
                users.add(null);
            }
        }

        return users;
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

    private String modifyListing(Item item) {
        DocumentReference document;
        if(item.itemId == null) {
            document = database.collection(ITEMS_PATH).document();
            item.itemId = document.getId();
        } else {
            document = database.collection(ITEMS_PATH).document(item.itemId);
        }

        document.set(item);
        return document.getId();
    }

    public String addListing(Item item) {
        return modifyListing(item);
    }

    boolean resolveSale(Item i) {
        return true;
    }

    boolean updateItem(int itemId, Item i) {
        return true;
    }

    // Returns a list of Users that are friends of the userId passed in
    // If there is an error retrieving an individual user, that user will be returned as null
    public List<User> getFriendsOf(String userId) {
        try {
            final User user = getUser(userId);
            return getUsers(user.friends);
        } catch(Exception e) {
            return null;
        }
    }

}
