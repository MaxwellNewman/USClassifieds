package com.cs310.usclassifieds.model.manager;

import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.SearchQuery;
import com.cs310.usclassifieds.model.datamodel.User;

import java.util.List;

public class SearchManager {
    private DataManager dataManager;

    public SearchManager(DataManager dm) {
        this.dataManager = dm;
    }

    public List<Item> searchItems(String text) {
        SearchQuery query = new SearchQuery();
        return dataManager.searchItems(query);
    }

    public List<User> searchUsers(String text) {
        SearchQuery query = new SearchQuery();
        return dataManager.searchUsers(query);
    }
}
