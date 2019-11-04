package com.cs310.usclassifieds.model.manager;

import com.cs310.usclassifieds.model.datamodel.*;
import java.util.*;

public class SearchManager {
    private DataManager dataManager;

    public SearchManager(DataManager dm) {
        this.dataManager = dm;
    }

    public List<Item> searchItemsByTitle(String searchString) {
        SearchQuery query = new SearchQuery();
        //TODO: Create query given the searchString
        //return dataManager.searchItems(query);

        // PLACEHOLDER: Return dummy values for UI
        List<Item> placeHolder =  new ArrayList<Item>();
        Item item1 = new Item();
        item1.title = searchString + "1";
        item1.image = "image1.png";
        item1.price = (float) 5.50;
        item1.description = "Example item 1";
        List<String> item1Tags = new ArrayList<String>();
        item1Tags.add("item");
        item1Tags.add("1");
        item1Tags.add("example");
        item1.tags = item1Tags;
        item1.location = new Location("651 W 35th St, Los Angeles, CA 90089", 34.021638, -118.282806); // Leavey
        item1.userId = "1";
        item1.itemId = "1";
        placeHolder.add(item1);

        Item item2 = new Item();
        item2.title = searchString + "2";
        item2.image = "image1.png";
        item2.price = (float) 5.50;
        item2.description = "Example item 2";
        List<String> item2Tags = new ArrayList<String>();
        item2Tags.add("item");
        item2Tags.add("2");
        item2Tags.add("example");
        item2.tags = item1Tags;
        item2.location = new Location("3550 Trousdale PKWY , Los Angeles, CA 90089", 34.020247, -118.283874); // Doheny
        item2.userId = "2";
        item2.itemId = "2";
        placeHolder.add(item2);

        return placeHolder;
    }

    public List<User> searchUsers(String searchString) {
        SearchQuery query = new SearchQuery();
        //TODO: Create query given the searchString
        //return dataManager.searchUsers(query);

        // PLACEHOLDER: Return dummy values for UI
        List<User> placeHolder =  new ArrayList<User>();
        User user1 = new User();
        user1.username = searchString + "1";
        user1.profilePicture = "image1.png";
        user1.userId = "1";
        placeHolder.add(user1);

        User user2 = new User();
        user2.username = searchString + "2";
        user2.profilePicture = "image1.png";
        user2.userId = "2";
        placeHolder.add(user2);

        return placeHolder;
    }
}
