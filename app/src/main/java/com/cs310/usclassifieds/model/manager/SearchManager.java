package com.cs310.usclassifieds.model.manager;

import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.Query;
import com.cs310.usclassifieds.model.datamodel.User;

import java.util.List;

public class SearchManager {
    private DataManager dataManager;
    protected List<Item> items;
    protected List<User> users;

    public SearchManager(DataManager dm) {
        this.dataManager = dm;
    }

    public void searchItems(Query query) {
        items = dataManager.searchItems(query);
    }

    public void searchUsers(Query query) {
        users = dataManager.searchUsers(query);
    }
}
