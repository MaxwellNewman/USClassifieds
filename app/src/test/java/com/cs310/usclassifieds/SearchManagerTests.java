package com.cs310.usclassifieds;

import android.content.Context;
import android.util.Pair;

import androidx.test.runner.AndroidJUnit4;

import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.ItemManager;
import com.cs310.usclassifieds.model.manager.SearchManager;
import com.cs310.usclassifieds.model.manager.UserManager;
import com.google.common.collect.ImmutableList;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import static com.cs310.usclassifieds.TestData.ALL_ITEMS;
import static com.cs310.usclassifieds.TestData.ALL_USERS;
import static com.cs310.usclassifieds.TestData.ITEMS_1_2;
import static com.cs310.usclassifieds.TestData.ITEM_1;
import static com.cs310.usclassifieds.TestData.ITEM_2;
import static com.cs310.usclassifieds.TestData.ITEM_3;
import static com.cs310.usclassifieds.TestData.ITEM_4;
import static com.cs310.usclassifieds.TestData.LATITUDE_USC_HOTEL;
import static com.cs310.usclassifieds.TestData.LONGITUDE_USC_HOTEL;
import static com.cs310.usclassifieds.TestData.USER1_ITEMS;
import static com.cs310.usclassifieds.TestData.USERNAME_1;
import static com.cs310.usclassifieds.TestData.USERNAME_2;
import static com.cs310.usclassifieds.TestData.USER_1;
import static com.cs310.usclassifieds.TestData.USER_2;
import static com.cs310.usclassifieds.TestData.USER_3;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Class testing the SearchManager class
 * Tests exercise functions searching and sorting items and users
 */
@RunWith(AndroidJUnit4.class)
public class SearchManagerTests {
    private static final String FIREBASE_APP_NAME = "test-app";

    private DataManager dataManager = mock(DataManager.class);
    private SearchManager searchManager = new SearchManager(dataManager);
    private ItemManager itemManager = new ItemManager(dataManager);
    private UserManager userManager = new UserManager(dataManager);

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    private FirebaseApp initializeApp(Context context) {
        System.out.println("in func");
        return FirebaseApp.initializeApp(
                context,
                new FirebaseOptions.Builder()
                        .setApplicationId("firebaseuitests-app123")
                        .setApiKey("AIzaSyCSTWld6jstN2eosUB6MYCTgjs8qYK-lm8")
                        .setProjectId("usclassifieds-4ae04")
                        .build(),
                FIREBASE_APP_NAME);
    }

    private FirebaseApp getAppInstance(Context context) {
        try {
            return FirebaseApp.getInstance(FIREBASE_APP_NAME);
        } catch (IllegalStateException e) {
            return initializeApp(context);
        }
    }

    // null or empty string returns all users
    @Test
    public void testSearchUsersEmptyNullString() {
        when(dataManager.getAllUsers()).thenReturn(ALL_USERS);

        final List<User> emptyUsers = searchManager.searchUsers("");
        final List<User> nullUsers = searchManager.searchUsers(null);

        assertEquals(ALL_USERS, emptyUsers);
        assertEquals(ALL_USERS, nullUsers);
        verify(dataManager, times(2)).getAllUsers();

        System.out.println("EmptyNullString passed");
    }

    // ensures that searching by username checks that a username "contains" one of the search terms
    @Test
    public void testSearchUsers() {
        final String searchString = "1 2";
        when(dataManager.getAllUsers()).thenReturn(ALL_USERS);

        final List<User> returnUsers = searchManager.searchUsers(searchString);

        assertEquals(2, returnUsers.size());
        assertTrue(returnUsers.contains(USER_1));
        assertTrue(returnUsers.contains(USER_2));
        assertFalse(returnUsers.contains(USER_3));
        verify(dataManager, times(1)).getAllUsers();

        System.out.println("Search Users passed");
    }

    // only item 1 has bike in title, items 2 and 3 not part of USER1_ITEMS
    @Test
    public void testSearchItemsByUserAndTitleItem1() {
        final String searchString = "bike tag3";
        when(dataManager.searchItemsByUser(USERNAME_1)).thenReturn(USER1_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByUserAndTitle(USERNAME_1, searchString);

        assertEquals(1, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertFalse(returnItems.contains(ITEM_2));
        assertFalse(returnItems.contains(ITEM_3));
        assertFalse(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).searchItemsByUser(USERNAME_1);

        System.out.println("Search Items By User(Item 1) passed");
    }

    // both items 1 and 4 have tag1, so returned
    @Test
    public void testSearchItemsByUserAndTitleItem1AndItem4Tag1() {
        final String searchString = "tag1";
        when(dataManager.searchItemsByUser(USERNAME_1)).thenReturn(USER1_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByUserAndTitle(USERNAME_1, searchString);

        assertEquals(2, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertFalse(returnItems.contains(ITEM_2));
        assertFalse(returnItems.contains(ITEM_3));
        assertTrue(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).searchItemsByUser(USERNAME_1);

        System.out.println("Search Items By User(Item 1, Item 4, Tag 1) passed");
    }

    // item 4 because tag4, items 2 and 3 not contained in USER1_ITEMS, item 1 does not have search terms
    @Test
    public void testSearchItemsByUserAndTitleItem4() {
        final String searchString = "tag4 apple blue";
        when(dataManager.searchItemsByUser(USERNAME_1)).thenReturn(USER1_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByUserAndTitle(USERNAME_1, searchString);

        assertEquals(1, returnItems.size());
        assertFalse(returnItems.contains(ITEM_1));
        assertFalse(returnItems.contains(ITEM_2));
        assertFalse(returnItems.contains(ITEM_3));
        assertTrue(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).searchItemsByUser(USERNAME_1);

        System.out.println("Search Items By User (Item4) passed");
    }

    // empty string returns all items for given user
    @Test
    public void testSearchItemsByUserAndTitleEmptyString() {
        final String searchString = "";
        when(dataManager.searchItemsByUser(USERNAME_1)).thenReturn(USER1_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByUserAndTitle(USERNAME_1, searchString);

        assertEquals(2, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertFalse(returnItems.contains(ITEM_2));
        assertFalse(returnItems.contains(ITEM_3));
        assertTrue(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).searchItemsByUser(USERNAME_1);

        System.out.println("Search Items By User (Item 1, Item 4, empty string) passed");
    }

    // null string does not crash program, but returns all items for given user
    @Test
    public void testSearchItemsByUserAndTitleNullString() {
        final String searchString = null;
        when(dataManager.searchItemsByUser(USERNAME_1)).thenReturn(USER1_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByUserAndTitle(USERNAME_1, searchString);

        assertEquals(2, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertFalse(returnItems.contains(ITEM_2));
        assertFalse(returnItems.contains(ITEM_3));
        assertTrue(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).searchItemsByUser(USERNAME_1);

        System.out.println("Search Items By User(Item 1, Item 4, null string) passed");
    }

    // all items returned because all have tag 1 (search string gets properly split"
    @Test
    public void testSearchItemsByTitleOrTagsAllItems() {
        final String searchString = "tag1 tag2 tag3 pickle";
        when(dataManager.getAllItems()).thenReturn(ALL_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByTitleOrTags(searchString);

        assertEquals(4, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertTrue(returnItems.contains(ITEM_2));
        assertTrue(returnItems.contains(ITEM_3));
        assertTrue(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).getAllItems();

        System.out.println("Search Items By Tags (All Items) passed");
    }

    // all items returned because all have tag1
    @Test
    public void testSearchItemsByTitleOrTagsAllItemsTag1() {
        final String searchString = "tag1 banana";
        when(dataManager.getAllItems()).thenReturn(ALL_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByTitleOrTags(searchString);

        assertEquals(4, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertTrue(returnItems.contains(ITEM_2));
        assertTrue(returnItems.contains(ITEM_3));
        assertTrue(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).getAllItems();

        System.out.println("Search Items By Tags (All Items, Tag1) passed");
    }

    // returns only items 1 and 2 because they have the string "bike" in their titles
    @Test
    public void testSearchItemsByTitleOrTagsItems1And2() {
        final String searchString = "bike banana";
        when(dataManager.getAllItems()).thenReturn(ALL_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByTitleOrTags(searchString);

        assertEquals(2, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertTrue(returnItems.contains(ITEM_2));
        assertFalse(returnItems.contains(ITEM_3));
        assertFalse(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).getAllItems();

        System.out.println("Search Items By Tags (Item1 and Item2) passed");
    }

    // Item 1 selected because "bike" is in its title, items 2 and 3 selected because they have tag 3
    // checking that terms are made lowercase before comparing
    @Test
    public void testSearchItemsByTitleOrTagsItems1And2Caps() {
        final String searchString = "bIKe TAG3";
        when(dataManager.getAllItems()).thenReturn(ALL_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByTitleOrTags(searchString);

        assertEquals(3, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertTrue(returnItems.contains(ITEM_2));
        assertTrue(returnItems.contains(ITEM_3));
        assertFalse(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).getAllItems();

        System.out.println("Search Items By Tags (Item1 and Item2, Caps) passed");
    }

    // Test that the searchItemsByTitleOrTags function returns all items when empty string passed in
    @Test
    public void testSearchItemsByTitleOrTagsEmptyString() {
        final String searchString = "";
        when(dataManager.getAllItems()).thenReturn(ALL_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByTitleOrTags(searchString);

        assertEquals(4, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertTrue(returnItems.contains(ITEM_2));
        assertTrue(returnItems.contains(ITEM_3));
        assertTrue(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).getAllItems();

        System.out.println("Search Items By Tags (Empty string) passed");
    }

    // Test that the searchItemsByTitleOrTags function returns all items when null string passed in
    @Test
    public void testSearchItemsByTitleOrTagsNullString() {
        final String searchString = null;
        when(dataManager.getAllItems()).thenReturn(ALL_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByTitleOrTags(searchString);

        assertEquals(4, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertTrue(returnItems.contains(ITEM_2));
        assertTrue(returnItems.contains(ITEM_3));
        assertTrue(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).getAllItems();

        System.out.println("Search Items By Tags (Null string) passed");
    }

    // checks that this function searches only by title, and not by tags
    // items 1 and 2 because bike in title, neither 3 or 4 because tag1 not checked
    // this function is rarely used
    @Test
    public void testSearchItemsByTitleItem1AndItem2() {
        final String searchString = "bike tag1";
        when(dataManager.getAllItems()).thenReturn(ALL_ITEMS);

        final List<Item> returnItems = searchManager.searchItemsByTitle(searchString);

        assertEquals(2, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertTrue(returnItems.contains(ITEM_2));
        assertFalse(returnItems.contains(ITEM_3));
        assertFalse(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).getAllItems();

        System.out.printf("Search Items By Title (Item1 and Item2) passed");
    }

    // checks that searching by tags results in the right DataManager function being called
    // and properly splits the search string
    @Test
    public void testSearchItemsByTags() {
        final String searchString = "tag1 tag2 banana";
        final List<String> searchTerms = ImmutableList.of(
                "tag1",
                "tag2",
                "banana"
        );
        when(dataManager.searchItemsByTags(searchTerms)).thenReturn(ITEMS_1_2);

        final List<Item> returnItems = searchManager.searchByTags(searchString);
        assertEquals(2, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertTrue(returnItems.contains(ITEM_2));
        assertFalse(returnItems.contains(ITEM_3));
        assertFalse(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).searchItemsByTags(searchTerms);

        System.out.println("Search Items By Tags Only (Items 1 and 2) passed");
    }

    // ensures that the username that is passed in is not split up
    // so is check verbatim in firebase for efficiency
    @Test
    public void testSearchItemsByUser() {
        final String searchString = USERNAME_1 + " " + USERNAME_2;
        when(dataManager.searchItemsByUser(searchString)).thenReturn(ITEMS_1_2);

        final List<Item> returnItems = searchManager.searchItemsByUser(searchString);
        assertEquals(2, returnItems.size());
        assertTrue(returnItems.contains(ITEM_1));
        assertTrue(returnItems.contains(ITEM_2));
        assertFalse(returnItems.contains(ITEM_3));
        assertFalse(returnItems.contains(ITEM_4));
        verify(dataManager, times(1)).searchItemsByUser(searchString);

        System.out.println("Search Items By User Only (Items 1 and 2) passed");
    }

    // test to ensure that sorting by distance gives the proper ordering
    // From USC Hotel: order listed below
    @Test
    public void testSortByDistance() {
        final List<Pair<Double, Item>> sortedItems = searchManager.sortByDistance(LATITUDE_USC_HOTEL, LONGITUDE_USC_HOTEL, ALL_ITEMS);

        assertEquals(4, sortedItems.size());
        assertEquals(sortedItems.get(0).second, ITEM_3); // Icon Apartments
        assertEquals(sortedItems.get(1).second, ITEM_1); // Shrine Place
        assertEquals(sortedItems.get(2).second, ITEM_2); // Ilium (Village)
        assertEquals(sortedItems.get(3).second, ITEM_4); // White House

        System.out.println("Sort Items By Distance passed");
    }

    // ensure that sorting by distance doesn't crash on a null pointer
    @Test
    public void testSortByDistanceNullPointer() {
        final List<Pair<Double, Item> > sortedItems = searchManager.sortByDistance(LATITUDE_USC_HOTEL, LONGITUDE_USC_HOTEL, null);
        assertEquals(0, sortedItems.size());

        System.out.println("Sort Items By Distance (Null Pointer) passed");
    }

    // ensure that sorting by distance doesn't crash on an empty list
    @Test
    public void testSortByDistanceEmptyList() {
        final List<Pair<Double, Item> > sortedItems = searchManager.sortByDistance(LATITUDE_USC_HOTEL, LONGITUDE_USC_HOTEL, new ArrayList<Item>());
        assertEquals(0, sortedItems.size());

        System.out.println("Sort Items By Distance (Null Pointer) passed");
    }

    // verify that items are sorted appropriately according to price, cheapest first
    @Test
    public void testSortByPriceCheapestFirst() {
        final List<Item> sortedItems = searchManager.sortByPrice(true, ALL_ITEMS);
        assertEquals(4, sortedItems.size());

        assertEquals(ITEM_3, sortedItems.get(0)); // $9
        assertEquals(ITEM_2, sortedItems.get(1)); // $13
        assertEquals(ITEM_1, sortedItems.get(2)); // $100
        assertEquals(ITEM_4, sortedItems.get(3)); // $2000

        System.out.println("Sort Items By Price (cheapest first) passed");
    }

    // verify that expensive first sorts accordingly
    @Test
    public void testSortByPriceExpensiveFirst() {
        final List<Item> sortedItems = searchManager.sortByPrice(false, ALL_ITEMS);
        assertEquals(4, sortedItems.size());

        assertEquals(ITEM_4, sortedItems.get(0)); // $2000
        assertEquals(ITEM_1, sortedItems.get(1)); // $100
        assertEquals(ITEM_2, sortedItems.get(2)); // $13
        assertEquals(ITEM_3, sortedItems.get(3)); // $9

        System.out.println("Sort Items By Price (most expensive first) passed");
    }
/*
    @Test
    public void createUserTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println("first");

        FirebaseApp r = FirebaseApp.initializeApp(getContext());

        System.out.println(r);

        // initializeApp(getContext());
        FirebaseApp.initializeApp(
                context,
                new FirebaseOptions.Builder()
                        .setApplicationId("1:268970013919:android:a77bd05f271e76e08be56a")
                        .setApiKey("AIzaSyA2BvfVabweO3MS2fGEyjTBmMaEF58OjnY")
                        .setProjectId("usclassifieds-4ae04")
                        .setStorageBucket("usclassifieds-4ae04.appspot.com")
                        .setDatabaseUrl("https://usclassifieds-4ae04.firebaseio.com")
                        .build()
        );

        FirebaseApp p = FirebaseApp.getInstance();

        System.out.println(p.toString());

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInAnonymously();

        auth.signInWithEmailAndPassword("mt1@usclassifieds.com", "password");
        System.out.println("after sign in ");

        auth.createUserWithEmailAndPassword(testUsername, testPassword);

        System.out.println(auth.getCurrentUser());

        System.out.println("second");
        // FirebaseApp app = getAppInstance(getContext());
        // FirebaseApp app = getAppInstance(getContext());
        System.out.println("third");

        final Map<String, String> map = new HashMap<>();
        map.put("first", "second");

        FirebaseFirestore ff = FirebaseFirestore.getInstance();

        System.out.println(ff.getFirestoreSettings());

        ff.setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build());

        ff.collection(DataManager.USERS_PATH)
                .add(map)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("FAILURE");
                    }
                });

        System.out.println("in between");

        System.out.println(ff.getApp());
        System.out.println(ff.collection(DataManager.USERS_PATH).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                System.out.println("READING DONE");
            }
        }));
*//*
        FirebaseFirestore ff = FirebaseFirestore.getInstance("https://usclassifieds-4ae04.firebaseio.com");
*//*

        InstrumentationRegistry.getInstrumentation();

        System.out.println("between after");

        // FirebaseApp app = getAppInstance(InstrumentationRegistry.getContext());

        // Configure Firestore and disable persistence
        FirebaseFirestore store = FirebaseFirestore.getInstance(app);

        store.setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                        .setPersistenceEnabled(false)
                        .build());

        final UserManager userManager = new UserManager(new DataManager());
        final User user = populateUser();
        System.out.println("fourth");
        userManager.addUser(user);
        System.out.println("fifth");

        final SearchManager searchManager = new SearchManager(new DataManager());
        System.out.println("sixth");
        final List<User> ALL_USERS = searchManager.searchUsers(testUsername);
        System.out.println("seventh");

        assertTrue(ALL_USERS.size() >= 1);
        assertEquals(testUsername, ALL_USERS.get(0).username);
        assertEquals(testUserId, ALL_USERS.get(0).userId);

        System.out.println("eighth");
        // clean test user from database
        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        System.out.println("ninth");
        database.collection(DataManager.USERS_PATH).document(testUserId).delete();
        System.out.println("tenth");
    }*/
}