package com.cs310.usclassifieds.datamodels;

import com.cs310.usclassifieds.model.datamodel.Item;

import org.junit.Before;
import org.junit.Test;

import static com.cs310.usclassifieds.TestData.ALL_ITEMS;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ItemTests {
    @Before
    public void setup() {
    }

    // tests that the same item is equal
    @Test
    public void testEqualsSameItem() {
        for (Item item: ALL_ITEMS) {
            assertTrue(item.equals(item));
        }
        System.out.println("Item.equals() passed (same item)");
    }

    // tests that a newly created item with the same values is equal
    @Test
    public void testEqualsNewItem() {
        for (Item item: ALL_ITEMS) {
            // Create new item
            Item newItem = new Item(item.title, item.description, item.price,
                    item.imageUri, item.imageUrl, item.tags, item.userId,
                    item.itemId, item.username, item.location);
            assertTrue(item.equals(newItem));
        }
        System.out.println("Item.equals() passed (new item)");
    }


}
