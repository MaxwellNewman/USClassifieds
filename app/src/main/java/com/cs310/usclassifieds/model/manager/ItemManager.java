package com.cs310.usclassifieds.model.manager;

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

    // Replace current itemID with new Item i 
    public void updateItem(Integer itemID, Item i) {
        dataManager.updateItem(itemID, i);
    }
}
