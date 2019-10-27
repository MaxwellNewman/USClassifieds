package com.cs310.usclassifieds.model.manager;

public class ItemManager {
    private DataManager dataManager;

    public ItemManager(DataManager dm) {
        this.dataManager = dm;
    } 

    // Puts Item i in the database of available deals
    public void createListing(Item i) {

    }

    // Marks Item i as sold
    public void markSold(Item i) {

    }

    // Replace current itemID with new Item i 
    public void replaceItem(Integer itemID, Item i) {

    }
}
