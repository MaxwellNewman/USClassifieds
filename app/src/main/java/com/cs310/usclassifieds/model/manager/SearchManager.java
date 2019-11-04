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
            return (int) (METERS_PER_MILE *(second.first - first.first));
        }
    }

    class PriceComparator implements  Comparator<Item> {
        @Override
        public int compare(Item first, Item second) {
            return (int) (first.price - second.price);
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

    public List<Item> searchByPrice(boolean cheapestFirst) {
        final List<Item> items = dataManager.getAllItems();
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
    }

    public List<User> searchUsers(String searchString) {
        final List<User> users = dataManager.getAllUsers();
        final List<String> searchTerms = Arrays.asList(searchString.split("\\s+"));
        final List<User> results = new ArrayList<>();

        for(int i=0; i<users.size(); ++i) {
            boolean userAdded = false;
            for(int j=0; j<searchTerms.size() && !userAdded; ++j) {
                if(users.get(i)
                        .username
                        .toLowerCase()
                        .contains(searchTerms.get(i).toLowerCase())) {
                    results.add(users.get(i));
                    userAdded = true;
                }
            }
        }

        return results;
    }
}
