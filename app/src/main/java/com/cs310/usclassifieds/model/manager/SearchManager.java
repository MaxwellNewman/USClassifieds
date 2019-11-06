package com.cs310.usclassifieds.model.manager;

import android.util.Pair;

import com.cs310.usclassifieds.model.datamodel.*;
import com.google.firebase.firestore.CollectionReference;

import java.util.*;

public class SearchManager {
    private DataManager dataManager;
    private static final double EARTH_RADIUS = 3958.8;
    private static final int METERS_PER_MILE = 1600;

    public SearchManager(DataManager dm) {
        this.dataManager = dm;
    }

    class DistanceComparator implements Comparator<Pair<Double, Item> > {
        @Override
        public int compare(Pair<Double,Item> first, Pair<Double,Item> second) {
            return (int) (METERS_PER_MILE *(first.first - second.first));
        }
    }

    class PriceComparator implements  Comparator<Item> {
        @Override
        public int compare(Item first, Item second) {
            return (int) (first.price - second.price);
        }
    }

    private static Double halversine(double lat1, double lon1, double lat2, double lon2) {
        double deltaLon = lon1 - lon2;
        double deltaLat = lat1 - lat2;
        double a = Math.pow(Math.sin(deltaLat/2.0), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLon / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return c * EARTH_RADIUS;
    }

    public List<Pair<Double, Item> > sortByDistance(double lat, double lon, List<Item> items) {
        final List<Item> result = new ArrayList<>();
        List<Pair<Double, Item> > pairs = new ArrayList<>();
        DistanceComparator distanceComp = new DistanceComparator();

        for(final Item item : items) {
            double distance = halversine(lat, lon, item.location.latitude, item.location.longitude);
            pairs.add(new Pair<>(distance, item));
        }

        Collections.sort(pairs, distanceComp);
        return pairs;
    }

    public List<Item> sortByPrice(boolean cheapestFirst, List<Item> items) {
        final PriceComparator priceComp = new PriceComparator();

        Collections.sort(items, priceComp);
        if(!cheapestFirst) {
            Collections.reverse(items);
        }

        return items;
    }

    public List<Item> searchByTags(String searchString) {
        final List<String> searchTerms = Arrays.asList(searchString.split("\\s+"));
        return dataManager.searchItemsByTags(searchTerms);
    }

    public List<Item> searchItemsByUser(String searchString) {
        return dataManager.searchItemsByUser(searchString);
    }

    public List<Item> searchItemsByTitle(String searchString) {
        final List<Item> items = dataManager.getAllItems();
        final List<String> searchTerms = Arrays.asList(searchString.split("\\s+"));
        final List<Item> results = new ArrayList<>();

        for(int i=0; i<items.size(); ++i) {
            boolean itemAdded = false;
            for(int j=0; j<searchTerms.size() && !itemAdded; ++j) {
                if (items.get(i).title != null) {
                    if (items.get(i).title.toLowerCase().contains(searchTerms.get(j).toLowerCase())) {
                        results.add(items.get(i));
                        itemAdded = true;
                    }
                }
            }
        }

        return results;
    }

    private boolean validateUser(final User user) {
        return user.username != null &&
                user.userId != null &&
                user.fullName != null;
    }

    public List<User> searchUsers(String searchString) {
        if(searchString == null || searchString.equals("")) {
            return dataManager.getAllUsers();
        }

        final List<User> users = dataManager.getAllUsers();
        final List<String> searchTerms = Arrays.asList(searchString.split("\\s+"));
        final List<User> results = new ArrayList<>();

        for(int i=0; i<users.size(); ++i) {
            boolean userAdded = false;
            for(int j=0; j<searchTerms.size() && !userAdded; ++j) {
                if(validateUser(users.get(i)) &&  users.get(i)
                        .username
                        .toLowerCase()
                        .contains(searchTerms.get(j).toLowerCase())) {
                    results.add(users.get(i));
                    userAdded = true;
                }
            }
        }

        return results;
    }
}
