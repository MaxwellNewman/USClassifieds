package com.cs310.usclassifieds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.view.View;
import android.os.Bundle;
import android.util.Log;

import com.cs310.usclassifieds.ui.DataPassListener;
import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.User;

import java.util.List;
import java.util.ArrayList;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements DataPassListener {

    private List<Item> items = new ArrayList<Item>();
    private List<User> users = new ArrayList<User>();
    private String currentUsername;

    public String getCurrentUsername(){
        return currentUsername;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.currentUsername = getIntent().getStringExtra("USERNAME");
        Log.v("MainActivity", currentUsername);
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
