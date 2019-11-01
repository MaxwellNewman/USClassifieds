package com.cs310.usclassifieds.model.manager;

import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.Query;
import com.cs310.usclassifieds.model.datamodel.User;

import java.util.List;

public class SearchManager {
    private DataManager dataManager;

    public SearchManager(DataManager dm) {
        this.dataManager = dm;
    }

    public List<Item> searchItems(String searchString) {
        Query query = new Query();
        //TODO: Create query given the searchString
        
        return dataManager.searchItems(query);
    }

    public List<User> searchUsers(String searchString) {
        Query query = new Query();
        //TODO: Create query given the searchString

        return dataManager.searchUsers(query);
    }
}
