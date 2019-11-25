package com.cs310.usclassifieds;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.google.android.gms.maps.model.Marker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MapTest {

    private static final String USERNAME = "mapTest";
    private static final String PASSWORD = "password";
    private static final String ACCOUNT_NAME = "Test Account";
    private static final String EMAIL = "maptest@usc.edu";

    private static final String MARKER_DESCRIPTION1 = "Max's used water bottle";
    private static final String MARKER_DESCRIPTION2 = "Red Schwinn Bike";

    private static final String ITEM_DESCRIPTION = "it is completely empty, no water included";

    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    // Navigate to the map before each test
    @Before
    public void setupBefore() throws InterruptedException {
        onView(withId(R.id.username)).perform(replaceText(USERNAME));
        onView(withId(R.id.password)).perform(replaceText(PASSWORD));
        onView(withId(R.id.login)).perform(click());

        // Delay to 10 seconds for log in
        Thread.sleep(10000);

        // Click on the button to open the map
        onView(withId(R.id.map_button)).perform(click());
    }

    // tests that map markers can be selected
    @Test
    public void testMarkerClick() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker1 = device.findObject(new UiSelector().descriptionContains(MARKER_DESCRIPTION1));
        UiObject marker2 = device.findObject(new UiSelector().descriptionContains(MARKER_DESCRIPTION2));

        UiObject map = device.findObject(new UiSelector().descriptionContains("Google map"));

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
