package com.cs310.usclassifieds.model.manager;

import com.cs310.usclassifieds.model.datamodel.*;
import java.util.*;

public class SearchManager {
    private DataManager dataManager;

    public SearchManager(DataManager dm) {
        this.dataManager = dm;
    }

    public List<Item> searchItems(String searchString) {
        Query query = new Query();
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
        item1.location = new Location("651 W 35th St, Los Angeles, CA 90089", 34.0231334, -118.2930887); // Leavey
        item1.userId = 1;
        item1.itemId = 1;
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
        item2.location = new Location("3550 Trousdale PKWY , Los Angeles, CA 90089", 34.0201426, -118.2859196); // Doheny
        item2.userId = 2;
        item2.itemId = 2;
        placeHolder.add(item2);

        return placeHolder;
    }

    public List<User> searchUsers(String searchString) {
        Query query = new Query();
        //TODO: Create query given the searchString
        return dataManager.searchUsers(query);

        //PLACEHOLDER: Return dummy values for UI
    }
}
