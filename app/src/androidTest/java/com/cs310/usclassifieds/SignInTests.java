package com.cs310.usclassifieds;


import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

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


@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignInTests {

    private static final String USERNAME = "eTest";
    private static final String PASSWORD = "password";
    private static final String ACCOUNT_NAME = "Espresso Test Account";
    private static final String EMAIL = "espressotest@usc.edu";

    private static final String BAD_USERNAME = "Not a real username";
    private static final String BAD_PASSWORD = "bad password";

    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void SignInTest() throws InterruptedException {

        //type in login info and hit submit
        onView(withId(R.id.username)).perform(replaceText(USERNAME));
        onView(withId(R.id.password)).perform(replaceText(PASSWORD));
        onView(withId(R.id.login)).perform(click());

        //current implementation just sleeps for 15s while we do firebase calls
        //we could use idle resources but idk how (or if its worth figuring out how)
        //15 seconds is the an arbitrary time, I figure the google auth should be done by now and the next page will have loaded
        Thread.sleep(15000);

        //change page to profile
        onView(withId(R.id.navigation_profile)).perform(click());

        //confirm that the profile page has the correct user information
        onView(withId(R.id.name_text)).check(matches((withText(ACCOUNT_NAME))));
        onView(withId(R.id.email_profile)).check(matches((withText("Email: " + EMAIL))));
        onView(withId(R.id.username_profile)).check(matches((withText("Username: " + USERNAME))));

    }

    @Test
    public void CreateExistingAccountTest() throws InterruptedException {
        //click on create account button
        onView(withId(R.id.textView8)).perform(click());

        //type in account info and hit submit
        onView(withId(R.id.createUsername)).perform(replaceText(USERNAME));
        onView(withId(R.id.createPassword)).perform(replaceText(PASSWORD));
        onView(withId(R.id.createEmail)).perform(replaceText(EMAIL));
        onView(withId(R.id.createName)).perform(replaceText(ACCOUNT_NAME));
        onView(withId(R.id.createButton)).perform(click());

        //current implementation just sleeps for 15s while we do firebase calls
        //we could use idle resources but idk how (or if its worth figuring out how)
        //15 seconds is the an arbitrary time, I figure the google auth should be done by now and the next page will have loaded
        Thread.sleep(10000);

        //check that the create account button is displayed (aka we did not make an account since it already existed)
        onView(withId(R.id.createButton)).check(matches(isDisplayed()));

        //now we can go back to the login page to confirm that the user does indeed exist
        onView(withId(R.id.signInClickableText)).perform(click());

        //type in login info and hit submit
        onView(withId(R.id.username)).perform(replaceText(USERNAME));
        onView(withId(R.id.password)).perform(replaceText(PASSWORD));
        onView(withId(R.id.login)).perform(click());

        //current implementation just sleeps for 15s while we do firebase calls
        //we could use idle resources but idk how (or if its worth figuring out how)
        //15 seconds is the an arbitrary time, I figure the google auth should be done by now and the next page will have loaded
        Thread.sleep(15000);

        //change page to profile
        onView(withId(R.id.navigation_profile)).perform(click());

        //confirm that the profile page has the correct user information
        onView(withId(R.id.name_text)).check(matches((withText(ACCOUNT_NAME))));
        onView(withId(R.id.email_profile)).check(matches((withText("Email: " + EMAIL))));
        onView(withId(R.id.username_profile)).check(matches((withText("Username: " + USERNAME))));

    }

    @Test
    public void InvalidSignInTest() throws InterruptedException {

        //type in login info and hit submit
        onView(withId(R.id.username)).perform(replaceText(BAD_USERNAME));
        onView(withId(R.id.password)).perform(replaceText(BAD_PASSWORD));
        onView(withId(R.id.login)).perform(click());

        //current implementation just sleeps for 15s while we do firebase calls
        //we could use idle resources but idk how (or if its worth figuring out how)
        //15 seconds is the an arbitrary time, I figure the google auth should be done by now and the next page will have loaded
        Thread.sleep(15000);

        //check that the login button is displayed (aka we haven't left the login page)
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }
}
