package com.cs310.usclassifieds.model.manager;

import com.cs310.usclassifieds.model.datamodel.Item;

public class ItemManager {
    private DataManager dataManager;

    public ItemManager(DataManager dm) {
        this.dataManager = dm;
    } 

    // Puts Item i in the database of available deals
    public void createListing(Item i) {
        dataManager.addListing(i);
    }

    // Marks Item i as sold
    public void markSold(Item i) {
        dataManager.resolveSale(i);
    }

    // Replace current itemId with new Item i
    public void updateItem(int itemId, Item i) {
        dataManager.updateItem(itemId, i);
    }
}
