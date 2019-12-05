package com.cs310.usclassifieds;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.cs310.usclassifieds.ui.search.SearchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.google.common.base.Verify.verify;
import static junit.framework.TestCase.assertTrue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchTest {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();;

    private static final String USERNAME = "eTest";
    private static final String PASSWORD = "password";
    private static final String ACCOUNT_NAME = "Espresso Test Account";
    private static final String EMAIL = "espressotest@usc.edu";

    private static final String MARKER_DESCRIPTION1 = "Max's used water bottle";
    private static final String MARKER_DESCRIPTION2 = "Red Schwinn Bike";

    private static final String ITEM_DESCRIPTION = "it is completely empty, no water included";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUp() throws Exception {
        mAuth.signInWithEmailAndPassword(USERNAME + "@usclassifieds.com", PASSWORD);
    }

    @Test
    public void testDisplaysCorrectText() {
        // Check correct text for all basic views on page
        onView(withId(R.id.searchbar2)).check(matches(withHint(" Search for an item...")));
        onView(withId(R.id.advanced_search_button)).check(matches(withText("Advanced Search")));
        onView(withId(R.id.map_button)).check(matches(withText("View Item Map")));
        onView(withId(R.id.textView3)).check(matches(withText("Browse All Items")));
    }

    @Test
    public void testSearchingForItemNavigatesToSearchResultsPage() {
        // Type in arbitrary search string and click search
        onView(withId(R.id.searchbar2)).perform(replaceText("asdf"));
        onView(withId(R.id.search_button)).perform(click());

        // Means clicking search button successfully navigated to search results page
        onView(withId(R.id.listings_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationToAdvancedSearchPage() {
        // Click on advanced search button
        onView(withId(R.id.advanced_search_button)).perform(click());

        // Means clicking on advanced search button successfully navigates to advanced search page
        // Also further checks that all items on advanced search page are displayed and display the correct
        // text
        onView(withId(R.id.advanced_search_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.advanced_search_bar)).check(matches(withHint("Search")));
        onView(withId(R.id.submit_advanced_search_button)).check(matches(isDisplayed()));
        onView(withId(R.id.submit_advanced_search_button)).check(matches(withText("Submit Search")));
    }

    @Test
    public void testNavigationToItemMap() {
        // Click on item map
        onView(withId(R.id.map_button)).perform(click());

        // Check successful navigation to item map, and additional check that the correct text
        // fields are being displayed and items are being rendered
        onView(withId(R.id.map_search_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.map_search_bar)).check(matches(withHint("Search by title or category")));
        onView(withId(R.id.map_of_listings)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationToItemDetailPage() {
        // Click on first item in list
        onView(withId(R.id.browse_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check navigation to item detail page
        onView(withId(R.id.viewed_item_name)).check(matches(isDisplayed()));
        onView(withId(R.id.viewed_item_image)).check(matches(isDisplayed()));
        onView(withId(R.id.viewed_item_description)).check(matches(isDisplayed()));
        onView(withId(R.id.viewed_item_listingUser)).check(matches(isDisplayed()));
        onView(withId(R.id.viewed_item_price)).check(matches(isDisplayed()));
    }

    // tests that map markers can be selected
    @Test
    public void testMarkerClick() throws UiObjectNotFoundException {
        onView(withId(R.id.map_button)).perform(click());
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker1 = device.findObject(new UiSelector().descriptionContains(MARKER_DESCRIPTION1));
        UiObject marker2 = device.findObject(new UiSelector().descriptionContains(MARKER_DESCRIPTION2));

        // Ensure that can click the markers
        marker1.click();
        marker2.click();

        marker1.click();
        marker2.click();

        // Should not redirect
        onView(withId(R.id.map_of_listings)).check(matches(isDisplayed()));
    }

    @Test
    public void testMarkerRedirect() throws UiObjectNotFoundException, InterruptedException {
        onView(withId(R.id.map_button)).perform(click());

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains(MARKER_DESCRIPTION1));
        marker.click();
        Thread.sleep(100);
        marker.click();
        Thread.sleep(2000);

        // Check if new window opens
        device = UiDevice.getInstance(getInstrumentation());
        UiObject description = device.findObject(new UiSelector().descriptionContains(ITEM_DESCRIPTION));
        assertTrue(description.exists());

    }
}
