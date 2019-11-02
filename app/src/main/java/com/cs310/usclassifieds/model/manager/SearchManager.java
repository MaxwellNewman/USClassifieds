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

    public void searchItems(SearchQuery query) {
        items = dataManager.searchItems(query);
    }

    public void searchUsers(SearchQuery query) {
        users = dataManager.searchUsers(query);
    }
}
