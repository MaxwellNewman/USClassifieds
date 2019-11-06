package com.cs310.usclassifieds.model.manager;

import com.cs310.usclassifieds.model.datamodel.Item;

public class ItemManager {
    private DataManager dataManager;

    public ItemManager(DataManager dm) {
        this.dataManager = dm;
    } 

    // Puts Item i in the database of available deals
    public void createListing(Item item) {
        dataManager.addListing(item);
    }

    // Marks Item i as sold
    public void markSold(Item item) {
        dataManager.resolveSale(item);
    }

    // Replace current itemId with new Item i
    public void updateItem(String itemId, Item item) {
        dataManager.updateItem(itemId, item);
    }
}
