package com.cs310.usclassifieds.model.datamodel;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Item {
    public String title;
    public String description;
    public double price;
    public Uri imageUri;
    public String imageUrl;
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
