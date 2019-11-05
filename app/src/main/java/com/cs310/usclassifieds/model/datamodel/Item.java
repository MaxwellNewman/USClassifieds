package com.cs310.usclassifieds.model.datamodel;

import android.net.Uri;

import java.util.List;

public class Item {
    public String title;
    public String description;
    public double price;
    public Uri imageUri;
    public String imageUrl;
    public List<String> tags;
    public String userId;
    public String itemId;
    public String username;
    public Location location;

    public Item() {
        location = new Location();
    }
}
