package com.cs310.usclassifieds.managers;

import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.ItemManager;

import static com.cs310.usclassifieds.TestData.ALL_ITEMS;
import static com.cs310.usclassifieds.TestData.ITEM_2;
import static com.cs310.usclassifieds.TestData.ITEM_3;
import static com.cs310.usclassifieds.TestData.ITEM_4;
import static com.cs310.usclassifieds.TestData.USER_1;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import org.junit.Test;

import static com.cs310.usclassifieds.TestData.ITEM_1;
import static org.mockito.Mockito.mock;

public class ItemManagerTests {
    private DataManager dataManager = mock(DataManager.class);
    private ItemManager itemManager = new ItemManager(dataManager);

    // Test that calling createListing properly calls the dataManager to put into database
    @Test
    public void testCreateListing() {
        for(final Item item : ALL_ITEMS) {
            itemManager.createListing(item);
        }

        verify(dataManager, times(1)).addListing(ITEM_1);
        verify(dataManager, times(1)).addListing(ITEM_2);
        verify(dataManager, times(1)).addListing(ITEM_3);
        verify(dataManager, times(1)).addListing(ITEM_4);

        System.out.println("Create Listing (all items) passed");
    }

    // test that calling createListing with null pointer does not crash
    @Test
    public void testCreateListingNullPointer() {
        itemManager.createListing(null);

        verify(dataManager, times(1)).addListing(null);

        System.out.println("Create listing (null item) passed");
    }

    // test that calling markSold properly calls dataManager to modify database
    @Test
    public void testMarkSold() {
        for(final Item item : ALL_ITEMS) {
            itemManager.markSold(item, USER_1);
        }

        verify(dataManager, times(1)).resolveSale(ITEM_1, USER_1);
        verify(dataManager, times(1)).resolveSale(ITEM_2, USER_1);
        verify(dataManager, times(1)).resolveSale(ITEM_3, USER_1);
        verify(dataManager, times(1)).resolveSale(ITEM_4, USER_1);

        System.out.println("Mark as sold (all items) passed");
    }

    // test that calling markSold with null pointer argument does not crash
    @Test
    public void testMarkSoldNullPointer() {
        itemManager.markSold(null, null);

        verify(dataManager, times(1)).resolveSale(null, null);

        System.out.println("Mark as sold (null pointers) passed");
    }

    // test that calling updateItem properly calls dataManager to put updates into database
    @Test
    public void testUpdateItem() {
        for(final Item item : ALL_ITEMS) {
            itemManager.updateItem(item.itemId, item);
        }

        verify(dataManager, times(1)).updateItem(ITEM_1.itemId, ITEM_1);
        verify(dataManager, times(1)).updateItem(ITEM_2.itemId, ITEM_2);
        verify(dataManager, times(1)).updateItem(ITEM_3.itemId, ITEM_3);
        verify(dataManager, times(1)).updateItem(ITEM_4.itemId, ITEM_4);


        System.out.println("Update item (all items) passed");
    }

    // test that calling updateItem with null pointer does not crash program
    @Test
    public void testUpdateItemNullPointers() {
        itemManager.updateItem(null, null);

        verify(dataManager, times(1)).updateItem(null, null);

        System.out.println("Update Item (null pointers) passed");
    }
}
