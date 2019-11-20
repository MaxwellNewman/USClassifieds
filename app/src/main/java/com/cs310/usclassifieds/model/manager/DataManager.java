package com.cs310.usclassifieds.model.manager;
import android.net.Uri;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cs310.usclassifieds.SignInActivity;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.SearchQuery;
import com.cs310.usclassifieds.model.datamodel.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataManager {
    public static final String USERS_PATH = "users";
    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String ITEMS_PATH = "items";
    public static final String TAGS = "tags";
    private FirebaseFirestore database;
    private FirebaseStorage storage;

    public DataManager() {
        database = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    private boolean modifyUser(final User user) {
        System.out.println("database app: " + database.getApp());


        final DocumentReference document = database.collection(USERS_PATH).document(user.userId);

        if(user.imageUri != null) {
            final StorageReference storageRef = storage.getReference();
            final Uri imageUri = user.imageUri;
            final StorageReference imageRef = storageRef.child(imageUri.getLastPathSegment());
            final UploadTask uploadTask = imageRef.putFile(imageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        user.imageUrl = task.getResult().toString();
                        user.imageUri = null;
                        document.set(user);
                    } else {
                        Log.e("error adding user: " + user.userId, "exception");
                    }
                }
            });
        } else {
            document.set(user);
        }

        return true;
    }

    // Create friend request from user1 to user2, returns false if unsuccessful
    boolean createFriendRequest(final User user1, final User user2) {
        database.collection(USERS_PATH)
                .document(user2.userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    try {
                        final User toRequest = task.getResult().toObject(User.class);
                        toRequest.addFriendRequest(user1.userId);
                        modifyUser(toRequest);
                    } catch (Exception e) {
                        Log.e("Error adding friend", "unable to add friend " + user2.userId + ", " + e.getMessage());
                    }
                }
            }
        });

        return true;
    }
    
    // Remove friend request from user1 to user2, returns false if unsuccessful
    boolean declineFriendRequest(final User user1, final User user2) {
        database.collection(USERS_PATH)
                .document(user1.userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    final User toRemove = task.getResult().toObject(User.class);
                    toRemove.removeFriendRequest(user2.userId);
                    modifyUser(toRemove);
                }
            }
        });

        return true;
    }

    // Adds user1 to user2's friend list and vice versa, returns false if unsuccessful
    boolean addFriend(final User user1, final User user2) {
        database.collection(USERS_PATH)
                .document(user1.userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        final User toAdd = task.getResult().toObject(User.class);
                        toAdd.addFriend(user2.userId);
                        toAdd.removeFriendRequest(user2.userId);
                        modifyUser(toAdd);
                    }
                });

        database.collection(USERS_PATH)
                .document(user2.userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        final User toAdd = task.getResult().toObject(User.class);
                        toAdd.addFriend(user1.userId);
                        toAdd.addFriend(user1.userId);
                        modifyUser(toAdd);
                    }
                });

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

    List<User> searchUsers(String searchTerm) {
        List<User> users = new ArrayList<>();
        // add logic to search users
        return users;
    }

    public List<Item> getAllItems() {
        final List<Item> items = new ArrayList<>();
        final Task<QuerySnapshot> query = database.collection(ITEMS_PATH).get();

        while(!query.isComplete()) {
            // waiting for query to finish
        }

        final List<DocumentSnapshot> documents;
        try {
            documents = query.getResult().getDocuments();
        } catch(Exception e) {
            Log.e("error get item", e.getMessage());
            return null;
        }

        for(final DocumentSnapshot doc : documents) {
            try {
                items.add(doc.toObject(Item.class));
            } catch (Exception e) {
                Log.e("error adding item: " + doc.getId(), e.getMessage());
            }
        }

        return items;
    }

    public List<Item> searchItemsByUser(String username) {
        final List<Item> items = new ArrayList<>();
        final Task<QuerySnapshot> query = database.collection(ITEMS_PATH)
                .whereEqualTo(USERNAME, username.toLowerCase())
                .get();

        while(!query.isComplete()) {
            // wait for query
        }

        final List<DocumentSnapshot> documents;
        try {
            documents = query.getResult().getDocuments();
        } catch(Exception e) {
            Log.e("Error getting all users", e.getMessage());
            return null;
        }

        for(final DocumentSnapshot doc : documents) {
            try {
                items.add(doc.toObject(Item.class));
            } catch (Exception e) {
                Log.e("error adding all user: " + doc.getId(), e.getMessage());
            }
        }

        return items;
    }

    public List<User> getAllUsers() {
        final List<User> users = new ArrayList<>();
        final Task<QuerySnapshot> query = database.collection(USERS_PATH).get();

        while(!query.isComplete()) {
            // waiting for query
        }

        final List<DocumentSnapshot> documents;
        try {
            documents = query.getResult().getDocuments();
        } catch(Exception e) {
            Log.e("Error getting all users", e.getMessage());
            return null;
        }

        for(final DocumentSnapshot doc : documents) {
            try {
                users.add(doc.toObject(User.class));
            } catch (Exception e) {
                Log.e("error adding all user: " + doc.getId(), e.getMessage());
            }
        }

        return users;
    }

    public List<Item> searchItemsByTags(List<String> searchTerms) {
        final List<Task<QuerySnapshot> > queries = new ArrayList<>();
        final Set<String> itemsIncluded = new HashSet<>();
        final List<Item> items = new ArrayList<>();

        for(int i=0; i<searchTerms.size(); ++i) {
            searchTerms.set(i, searchTerms.get(i).toLowerCase());
        }

        // start the queries for all the search terms
        for(final String searchTerm : searchTerms) {
            queries.add(database.collection(ITEMS_PATH)
                    .whereArrayContains(TAGS, searchTerm)
                    .get()
            );
        }

        for(final Task<QuerySnapshot> query : queries) {
            while (!query.isComplete()) {
                // waiting for query to complete
            }

            List<DocumentSnapshot> documents = new ArrayList<>();
            try {
                documents = query.getResult().getDocuments();
            } catch (Exception e) {
                Log.e("error getting items", e.getMessage());
                return null;
            }

            for (final DocumentSnapshot doc : documents) {
                try {
                    if (!itemsIncluded.contains(doc.getId())) {
                        items.add(doc.toObject(Item.class));
                        itemsIncluded.add(doc.getId());
                    }
                } catch (Exception e) {
                    Log.e("Error getting item: " + doc.getId(), e.getMessage());
                }
            }
        }

        return items;
    }

    private String modifyListing(final Item item) {
        for(int i=0; i<item.tags.size(); ++i) {
            item.tags.set(i, item.tags.get(i).toLowerCase());
        }

        item.username = item.username.toLowerCase();

        final DocumentReference document;
        if(item.itemId == null) {
            document = database.collection(ITEMS_PATH).document();
            item.itemId = document.getId();
        } else {
            document = database.collection(ITEMS_PATH).document(item.itemId);
        }

        final StorageReference storageRef = storage.getReference();
        final Uri imageUri = item.imageUri;
        final StorageReference imageRef = storageRef.child(imageUri.getLastPathSegment());
        final UploadTask uploadTask = imageRef.putFile(imageUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    item.imageUrl = task.getResult().toString();
                    item.imageUri = null;
                    document.set(item);
                } else {
                    Log.e("error adding item: " + item.itemId, "exception");
                }
            }
        });

        return document.getId();
    }


    public void addListing(final Item item) {
        modifyListing(item);
    }

    boolean resolveSale(Item item, User user) {
        database.collection(ITEMS_PATH).document(item.itemId).delete();
        ++user.sales;
        modifyUser(user);

        return true;
    }

    String updateItem(String itemId, Item item) {
        item.itemId = itemId;
        return modifyListing(item);
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
