package com.cs310.usclassifieds;

import android.content.Context;

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
import static com.cs310.usclassifieds.TestData.USER_1;
import static com.cs310.usclassifieds.TestData.USER_ID_1;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class ExampleInstrumentedTest {

    private static DataManager dataManager;
    private static SearchManager searchManager;
    private static UserManager userManager;
    private static ItemManager itemManager;
    private static FirebaseFirestore database;

    private static final List<Item> ITEMS = new ArrayList<>(ALL_ITEMS);
    private static final List<User> USERS = new ArrayList<>(ALL_USERS);

    @BeforeClass
    public static void setup() {
        dataManager = new DataManager();
        searchManager = new SearchManager(dataManager);
        userManager = new UserManager(dataManager);
        itemManager = new ItemManager(dataManager);
        database = FirebaseFirestore.getInstance();
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
    public void testSearchItemsByTitleAndTags() {
        for(final Item item : ALL_ITEMS) {
            itemManager.createListing(item);
        }

        final String searchString = "bike tag1 red";
        final List<Item> items = searchManager.searchItemsByUserAndTitle(USER_1.username, searchString);

        assertEquals(2, items.size());
        for(final Item item : items) {
            assertTrue(item.equals(ITEM_1) || item.equals(ITEM_4));
        }

        System.out.println("Search Items By Title and Tags passed");
    }
}
