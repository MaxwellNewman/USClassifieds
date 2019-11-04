package com.cs310.usclassifieds.model.manager;

import android.util.Pair;

import com.cs310.usclassifieds.model.datamodel.*;
import java.util.*;

public class SearchManager {
    private DataManager dataManager;
    double EARTH_RADIUS = 3958.8;
    int METERS_PER_MILE = 1600;

    public SearchManager(DataManager dm) {
        this.dataManager = dm;
    }

    class DistanceComparator implements Comparator<Pair<Double, Item> > {
        @Override
        public int compare(Pair<Double,Item> first, Pair<Double,Item> second) {
            return (int) (METERS_PER_MILE *(second.first - first.first));
        }
    }

    public List<Item> searchByDistance(double lat, double lon) {
        final List<Item> items = dataManager.getAllItems();
        final List<Item> result = new ArrayList<>();
        List<Pair<Double, Item> > pairs = new ArrayList<>();
        DistanceComparator comp = new DistanceComparator();

        for(final Item item : items) {
            double deltaLon = lon - item.location.longitude;
            double deltaLat = lat - item.location.latitude;
            double a = Math.pow(Math.sin(deltaLat/2.0), 2) + Math.cos(lat) * Math.cos(item.location.latitude) * Math.pow(Math.sin(deltaLon / 2.0), 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double d = c * EARTH_RADIUS;

            pairs.add(new Pair<Double, Item>(d, item));
        }

        Collections.sort(pairs, comp);

        for(int i=0; i<pairs.size(); ++i) {
            result.add(pairs.get(i).second);
        }

        return result;
    }

    public List<Item> searchByPrice(double price, boolean cheapestFirst) {
        // TODO
        return new ArrayList<>();
    }

    public List<Item> searchByTags(String searchString) {
        final List<String> searchTerms = Arrays.asList(searchString.split("\\s+"));
        return dataManager.searchItemsByTags(searchTerms);
    }

    public List<Item> searchItemsByTitle(String searchString) {
        final List<Item> items = dataManager.getAllItems();
        final List<String> searchTerms = Arrays.asList(searchString.split("\\s+"));
        final List<Item> results = new ArrayList<>();

        for(int i=0; i<items.size(); ++i) {
            boolean itemAdded = false;
            for(int j=0; j<searchTerms.size() && !itemAdded; ++j) {
                if(items.get(i).title.contains(searchTerms.get(i))) {
                    results.add(items.get(i));
                    itemAdded = true;
                }
            }
        }

        return results;
/*        // return dataManager.searchItems(searchTerms);
        // SearchQuery query = new SearchQuery();
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
        item2.location = new Location("3550 Trousdale PKWY , Los Angeles, CA 90089", 34.0201426, -118.2859196); // Doheny
        item2.userId = "2";
        item2.itemId = "2";
        placeHolder.add(item2);

        return placeHolder;*/
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
