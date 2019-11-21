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
    public String userId;
    public String itemId;
    public String username;
    public Location location;

    // default constructors necessary to be written into firestore
    public Item() {
        location = new Location();
        tags = new ArrayList<>();
    }

    public Item(String title,
                String description,
                double price,
                Uri imageUri,
                String imageUrl,
                List<String> tags,
                String userId,
                String itemId,
                String username,
                Location location) {

        this.title = title;
        this.description = description;
        this.price = price;
        this.imageUri = imageUri;
        this.tags = tags;
        this.userId = userId;
        this.itemId = itemId;
        this.username = username;
        this.location = location;
    }

    public boolean equals(Item other) {
        boolean equivalent = true;
        equivalent &= (userId.equals(other.userId));
        equivalent &= (username.equals(other.username));
        equivalent &= (price == other.price);
        equivalent &= (description.equalsIgnoreCase(other.description));
        equivalent &= (tags.equals(other.tags));
        equivalent &= (location.address.equalsIgnoreCase(other.location.address));
        equivalent &= (location.latitude == other.location.latitude);
        equivalent &= (location.longitude == other.location.longitude);
        equivalent &= (itemId.equalsIgnoreCase(other.itemId));
        equivalent &= (title.equalsIgnoreCase(other.title));

        return  equivalent;
    }
}
