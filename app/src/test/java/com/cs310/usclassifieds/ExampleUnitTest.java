package com.cs310.usclassifieds;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.SearchManager;
import com.cs310.usclassifieds.model.manager.UserManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.google.firebase.FirebaseApp.initializeApp;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleUnitTest extends InstrumentationTestCase {
    private final String testUserId = "testId";
    private final String testFullName = "test fullname";
    private final String testUsername = "testUsername";
    private final String testEmail = "testEmail@usc.edu";
    private final String testPhoneNumber = "5556667777";
    private final String testProfileDescription = "this is a test profile description";
    private final Uri testUri = Uri.EMPTY;

    private static final String FIREBASE_APP_NAME = "test-app";

    private User populateUser() {
        return new User(testUserId, testFullName, testUsername, testEmail, testPhoneNumber, testProfileDescription, testUri);
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    private FirebaseApp initializeApp(Context context) {
        return FirebaseApp.initializeApp(context, new FirebaseOptions.Builder()
                .setApplicationId("firebaseuitests-app123")
                .setApiKey("AIzaSyCIA_uf-5Y4G83vlZmjMmCM_wkX62iWXf0")
                .setProjectId("firebaseuitests")
                .build(), FIREBASE_APP_NAME);
    }

    private FirebaseApp getAppInstance(Context context) {
        try {
            return FirebaseApp.getInstance(FIREBASE_APP_NAME);
        } catch (IllegalStateException e) {
            return initializeApp(context);
        }
    }

    @Test
    public void createUserTest() {
        Context context = InstrumentationRegistry.getTargetContext();
        FirebaseApp app = getAppInstance(getC);
/*
        FirebaseFirestore ff = FirebaseFirestore.getInstance("https://usclassifieds-4ae04.firebaseio.com");
*/

        // InstrumentationRegistry.getInstrumentation();

        // FirebaseApp app = getAppInstance(InstrumentationRegistry.getContext());

        // Configure Firestore and disable persistence
        /*FirebaseFirestore store = FirebaseFirestore.getInstance(app);

        store.setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                        .setPersistenceEnabled(false)
                        .build());*/

        final UserManager userManager = new UserManager(new DataManager());
        final User user = populateUser();
        userManager.addUser(user);

        final SearchManager searchManager = new SearchManager(new DataManager());
        final List<User> users = searchManager.searchUsers(testUsername);

        assertTrue(users.size() >= 1);
        assertEquals(testUsername, users.get(0).username);
        assertEquals(testUserId, users.get(0).userId);

        // clean test user from database
        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(DataManager.USERS_PATH).document(testUserId).delete();
    }
}