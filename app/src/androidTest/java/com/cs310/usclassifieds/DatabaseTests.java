package com.cs310.usclassifieds;

import android.content.Context;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.ItemManager;
import com.cs310.usclassifieds.model.manager.SearchManager;
import com.cs310.usclassifieds.model.manager.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static androidx.test.InstrumentationRegistry.getContext;
import static com.cs310.usclassifieds.TestData.ALL_ITEMS;
import static com.cs310.usclassifieds.TestData.ALL_USERS;
import static com.cs310.usclassifieds.TestData.ITEM_1;
import static com.cs310.usclassifieds.TestData.ITEM_2;
import static com.cs310.usclassifieds.TestData.ITEM_3;
import static com.cs310.usclassifieds.TestData.ITEM_4;
import static com.cs310.usclassifieds.TestData.LATITUDE_USC_HOTEL;
import static com.cs310.usclassifieds.TestData.LONGITUDE_USC_HOTEL;
import static com.cs310.usclassifieds.TestData.USERNAME_2;
import static com.cs310.usclassifieds.TestData.USER_1;
import static com.cs310.usclassifieds.TestData.USER_2;
import static com.cs310.usclassifieds.TestData.USER_3;
import static com.cs310.usclassifieds.TestData.USER_ID_1;
import static org.junit.Assert.*;

/**
 * Black Box tests that should run through the DataManager and upload data to Firebase
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class DatabaseTests {
    private static DataManager dataManager;
    private static SearchManager searchManager;
    private static UserManager userManager;
    private static ItemManager itemManager;
    private static FirebaseFirestore database;
    private static FirebaseAuth auth;

    @BeforeClass
    public static void setup() {
        dataManager = new DataManager();
        searchManager = new SearchManager(dataManager);
        userManager = new UserManager(dataManager);
        itemManager = new ItemManager(dataManager);
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @AfterClass
    public static void resetAll() {
        DataManager.useProductionPaths();
    }

    @Before
    public void setupBefore() {
        DataManager.useTestingPaths();
    }

    @After
    public void reset() {
        for(final Item item : ALL_ITEMS) {
            database.collection(DataManager.ITEMS_PATH).document(item.itemId).delete();
        }

        for(final User user : ALL_USERS) {
            database.collection(DataManager.USERS_PATH).document(user.userId).delete();
        }

        if(auth.getCurrentUser() != null) {
            auth.getCurrentUser().delete();
        }

        DataManager.useProductionPaths();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.cs310.usclassifieds", appContext.getPackageName());
    }

    // Verify that we can upload a user, then search that user by username
    @Test
    public void test() throws Exception {
        userManager.addUser(USER_1);
        final List<User> users = searchManager.searchUsers(USER_1.username);
        assertEquals(1, users.size());
    }

    @Test
    public void testSearchItemsByUserAndTitleAndTags() {
        for(final Item item : ALL_ITEMS) {
            itemManager.createListing(item);
        }

        final String searchString1 = "tag1 RED";
        final String searchString2 = "apple pickLe somethingelse";
        final String emptyString = "";

        final List<Item> user1Items = searchManager.searchItemsByUserAndTitle(USER_1.username, searchString1);
        assertEquals(2, user1Items.size());
        for(final Item item : user1Items) {
            assertTrue(item.equals(ITEM_1) || item.equals(ITEM_4));
        }

        final List<Item> user2Items = searchManager.searchItemsByUserAndTitle(USER_2.username, searchString1);
        assertEquals(1, user2Items.size());
        assertTrue(user2Items.get(0).equals(ITEM_2));

        final List<Item> user3Items = searchManager.searchItemsByUserAndTitle(USER_3.username, searchString2);
        assertEquals(1, user3Items.size());
        assertTrue(user3Items.get(0).equals(ITEM_3));

        final List<Item> itemsEmptySearch = searchManager.searchItemsByUserAndTitle(USER_1.username, emptyString);
        assertEquals(2, itemsEmptySearch.size());
        for(final Item item : itemsEmptySearch) {
            assertTrue(item.equals(ITEM_1) || item.equals(ITEM_4));
        }

        final List<Item> emptyItems = searchManager.searchItemsByUserAndTitle(null, searchString1);
        assertTrue(emptyItems.isEmpty());

        System.out.println("Search Items By Title and Tags passed");
    }

    // Verifies that searching for an item by a substring of its name will return the correct items
    // Uses searchItemsByUserAndTitle, but passes in searchStrings without the tags of any items
    @Test
    public void testSearchItemsByUserAndTitle() {
        for(final Item item : ALL_ITEMS) {
            itemManager.createListing(item);
        }

        final String searchString1 = "BIke RED";
        final String searchString2 = "BIke blue pickle";

        final List<Item> user1Items1 = searchManager.searchItemsByUserAndTitle(USER_1.username, searchString1);
        assertEquals(1, user1Items1.size());
        for(final Item item : user1Items1) {
            assertTrue(item.equals(ITEM_1));
        }

        final List<Item> user1Items2 = searchManager.searchItemsByUserAndTitle(USER_1.username, searchString2);
        assertEquals(2, user1Items2.size());
        for(final Item item : user1Items2) {
            assertTrue(item.equals(ITEM_1) || item.equals(ITEM_4));
        }

        System.out.println("Search Items By Title and Tags passed");
    }

    @Test
    public void testSearchItemsByTitleOrTag() {
        for(final Item item : ALL_ITEMS) {
            itemManager.createListing(item);
        }

        final String searchStringTag1 = "tag1 andsomethingelse";
        final String searchStringBikes = "bike %%$";
        final String twoTitleAndTag = "pickle apple tag4 andas always somethingelse";

        final List<Item> allItems = searchManager.searchItemsByTitleOrTags(searchStringTag1);
        assertEquals(ALL_ITEMS.size(), allItems.size());

        final List<Item> bikeItems = searchManager.searchItemsByTitleOrTags(searchStringBikes);
        assertEquals(2, bikeItems.size());
        for(final Item item : bikeItems) {
            assertTrue(item.equals(ITEM_1) || item.equals(ITEM_2));
        }

        final List<Item> threeItems = searchManager.searchItemsByTitleOrTags(twoTitleAndTag);
        assertEquals(3, threeItems.size());
        for(final Item item : threeItems) {
            assertTrue(item.equals(ITEM_2) || item.equals(ITEM_3) || item.equals(ITEM_4));
        }

        System.out.println("Search By Title And Tags passed");
    }

    @Test
    public void testSearchItemsByTitleOrTagNull() {
        for(final Item item : ALL_ITEMS) {
            itemManager.createListing(item);
        }

        final List<Item> nullResult = searchManager.searchItemsByTitleOrTags(null);
        assertEquals(ALL_ITEMS.size(), nullResult.size());

        final List<Item> emptyResult = searchManager.searchItemsByTitleOrTags("");
        assertEquals(ALL_ITEMS.size(), emptyResult.size());

        System.out.println("Search Items By Title or Tag passed");
    }

    @Test
    public void testSortItemsByDistance() {
        for(final Item item : ALL_ITEMS) {
            itemManager.createListing(item);
        }

        final String titles = "bike apple pickle";
        final List<Item> items = searchManager.searchItemsByTitle(titles);

        final List<Pair<Double, Item>> sortedItems = searchManager.sortByDistance(
                LATITUDE_USC_HOTEL,
                LONGITUDE_USC_HOTEL,
                items
        );

        assertEquals(ALL_ITEMS.size(), sortedItems.size());
        assertTrue(sortedItems.get(0).second.equals(ITEM_3)); // Icon Apartments
        assertTrue(sortedItems.get(1).second.equals(ITEM_1)); // Shrine Place
        assertTrue(sortedItems.get(2).second.equals(ITEM_2)); // Ilium (Village)
        assertTrue(sortedItems.get(3).second.equals(ITEM_4)); // White House

        System.out.println("Sort by distance passed");
    }

    @Test
    public void testSortByPriceExpensiveFirst() {
        for(final Item item : ALL_ITEMS) {
            itemManager.createListing(item);
        }

        final String tag1Search = "tag1";
        final List<Item> items = searchManager.searchItemsByTitleOrTags(tag1Search);

        final List<Item> sortedItems = searchManager.sortByPrice(false, items);
        assertTrue(sortedItems.get(0).equals(ITEM_4)); // $2000
        assertTrue(sortedItems.get(1).equals(ITEM_1)); // $100
        assertTrue(sortedItems.get(2).equals(ITEM_2)); // $13
        assertTrue(sortedItems.get(3).equals(ITEM_3)); // $9

        System.out.println("Sort by price (expensive first) passed");
    }

    @Test
    public void testSearchItemsByUser() {
        for(final Item item : ALL_ITEMS) {
            itemManager.createListing(item);
        }

        final List<Item> itemsUser1 = searchManager.searchItemsByUser(USER_1.username);
        assertEquals(2, itemsUser1.size());
        for(final Item item : itemsUser1) {
            assertTrue(item.equals(ITEM_1) || item.equals(ITEM_4));
        }

        final List<Item> itemsUser2 = searchManager.searchItemsByUser(USER_2.username);
        assertEquals(1, itemsUser2.size());
        assertTrue(itemsUser2.get(0).equals(ITEM_2));

        final List<Item> itemsUser3 = searchManager.searchItemsByUser(USER_3.username);
        assertEquals(1, itemsUser3.size());
        assertTrue(itemsUser3.get(0).equals(ITEM_3));

        System.out.println("Search Items by User passed");
    }

    @Test
    public void testSearchItemsByUserNullOrEmpty() {
        for(final Item item : ALL_ITEMS) {
            itemManager.createListing(item);
        }

        final List<Item> nullItems = searchManager.searchItemsByUser(null);
        assertTrue(nullItems.isEmpty());

        final List<Item> emptyItems = searchManager.searchItemsByUser("");
        assertTrue(emptyItems.isEmpty());

        System.out.println("Search Items By User (null or empty string) passed");
    }

    @Test
    public void testSearchUsers() {
        for(final User user : ALL_USERS) {
            userManager.addUser(user);
        }

        final String searchTerms1 = "user";
        final String oneAndTwo = "1 2 and";

        final List<User> allUsers = searchManager.searchUsers(searchTerms1);
        assertEquals(ALL_USERS.size(), allUsers.size());

        final List<User> oneAndTwoUsers = searchManager.searchUsers(oneAndTwo);
        assertEquals(2, oneAndTwoUsers.size());
        for(final User user : oneAndTwoUsers) {
            assertTrue(user.equals(USER_1) || user.equals(USER_2));
        }

        System.out.println("Search Users passed");
    }

    // desired behavior is for the empty or null string to return all users
    @Test
    public void testSearchUsersNullAndEmpty() {
        for(final User user : ALL_USERS) {
            userManager.addUser(user);
        }

        final List<User> nullUsers = searchManager.searchUsers(null);
        assertEquals(ALL_USERS.size(), nullUsers.size());

        final List<User> emptyUsers = searchManager.searchUsers("");
        assertEquals(ALL_USERS.size(), emptyUsers.size());
    }

    @Test
    public void testAddFriendRequest() {
        for(final User user : ALL_USERS) {
            userManager.addUser(user);
        }
    }

    @Test
    public void testFirebaseAuth() {
        String password = "password";
        final Task<AuthResult> result =
                auth.createUserWithEmailAndPassword(USER_1.username + "@usclassifieds.com", password);

        while(!result.isComplete());
        assertTrue(result.isSuccessful());

        if(result.getResult() != null && result.getResult().getUser() != null) {
            result.getResult().getUser().delete();
        }
        userManager.addUser(USER_1);

        System.out.println("Firebase Auth test passed");
    }
}
