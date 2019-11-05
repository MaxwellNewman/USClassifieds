package com.cs310.usclassifieds.model.datamodel;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Item {
    public String title;
    public Uri imageUri;
    public String imageUrl;
    public double price;
    public String description;
    public List<String> tags;
    public Location location;
    public String userId;
    public String itemId;
    public String username;

    public Item() {
        location = new Location();
        tags = new ArrayList<>();
    }
}
