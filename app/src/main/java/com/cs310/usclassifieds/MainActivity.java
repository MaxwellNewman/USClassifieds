package com.cs310.usclassifieds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.os.Bundle;
import android.util.Log;

import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.UserManager;
import com.cs310.usclassifieds.ui.DataPassListener;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.User;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements DataPassListener {
    public static final String DEFAULT_URL = "https://en.wikipedia.org/wiki/Sublime_Porte#/media/File:Imperial_Gate_Topkapi_Istanbul_2007_002.jpg";

    private final DataManager dataManager = new DataManager();
    private final UserManager userManager = new UserManager(dataManager);
    private List<Item> items = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Item viewedItem = new Item();
    private User viewedUser = new User();
    private User currentUser = null;

    public User getCurrentUser() {
        if (currentUser == null) {
            currentUser = userManager.loadProfile(getCurrentUserId());
        }

        return currentUser;
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public String getCurrentUserId(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void setViewedItem(Item item) {
        this.viewedItem = item;
    }

    @Override
    public void setViewedUser(User user) {
        this.viewedUser = user;
    }

    @Override
    public Item getViewedItem() {
        return this.viewedItem;
    }

    @Override
    public User getViewedUser() {
        return this.viewedUser;
    }

    @Override
    public void passItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public void passUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public List<User> getUsers() {
        return this.users;
    }

    @Override
    public List<Item> getItems() {
        return this.items;
    }

    private void populateCurrentUser() {
        /*final FirebaseFirestore database = FirebaseFirestore.getInstance();
        final User user = database.collection(DataManager.USERS_PATH)*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateCurrentUser();
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search, R.id.navigation_create_listing, R.id.navigation_profile)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    public void onBackPressed() {
        Log.v("MainActivity", "Back Button Pressed");
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void backPressed(View view) {
        this.onBackPressed();
    }
}
