package com.cs310.usclassifieds;

import android.net.Uri;
import android.provider.ContactsContract;

import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.Location;
import com.cs310.usclassifieds.model.datamodel.User;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class TestData {
    public static final String USER_ID_1 = "user1";
    public static final String USER_ID_2 = "user2";
    public static final String USER_ID_3 = "user3";
    public static final String USERNAME_1 = "username1";
    public static final String USERNAME_2 = "username2";
    public static final String USERNAME_3 = "username3";
    public static final String FULLNAME_1 = "full name1";
    public static final String FULLNAME_2 = "full name2";
    public static final String FULLNAME_3 = "full name3";
    public static final String PASSWORD_1 = "password1";
    public static final String PASSWORD_2 = "password2";
    public static final String PASSWORD_3 = "password3";
    public static final String EMAIL_1 = "email_1@usc.edu";
    public static final String EMAIL_2 = "email_2@usc.edu";
    public static final String EMAIL_3 = "email_3@usc.edu";
    public static final String PHONE_NUMBER_1 = "1111111111";
    public static final String PHONE_NUMBER_2 = "2222222222";
    public static final String PHONE_NUMBER_3 = "3333333333";
    public static final String PROFILE_DESC_1 = "test profile description 1";
    public static final String PROFILE_DESC_2 = "test profile description 2";
    public static final String PROFILE_DESC_3 = "test profile description 3";

    public static final Uri TEST_URI = null;

    public static final String TAG_1 = "tag1";
    public static final String TAG_2 = "tag2";
    public static final String TAG_3 = "tag3";
    public static final String TAG_4 = "tag4";
    public static final String TEST_URL = "image URL 1";

    public static final String TITLE_1 = "title 1 red bike";
    public static final String TITLE_2 = "title 2 blue bike";
    public static final String TITLE_3 = "title 1 red apple";
    public static final String TITLE_4 = "title 1 blue pickle";
    public static final String ITEM_DESC_1 = "item description 1";
    public static final String ITEM_DESC_2 = "item description 2";
    public static final String ITEM_DESC_3 = "item description 3";
    public static final String ITEM_DESC_4 = "item description 4";

    public static final List<String> TAGS_1 = ImmutableList.of(
            TAG_1
    );
    public static final List<String> TAGS_2 = ImmutableList.of(
            TAG_1,
            TAG_3,
            TAG_4
    );
    public static final List<String> TAGS_3 = ImmutableList.of(
            TAG_1,
            TAG_2,
            TAG_3
    );
    public static final List<String> TAGS_4 = ImmutableList.of(
            TAG_1,
            TAG_4
    );

    public static final double PRICE_1 = 100.45;
    public static final double PRICE_2 = 13.49;
    public static final double PRICE_3 = 9.68;
    public static final double PRICE_4 = 2000.13;
    public static final String ITEM_ID_1 = "Item1";
    public static final String ITEM_ID_2 = "Item2";
    public static final String ITEM_ID_3 = "Item3";
    public static final String ITEM_ID_4 = "Item4";

    public static final double LATITUDE_SHRINE = 34.025130;
    public static final double LONGITUDE_SHRINE = -118.279915;
    public static final String ADDRESS_SHRINE = "3030 Shrine Pl, Los Angeles, CA 90007";
    public static final Location LOCATION_SHRINE = new Location(ADDRESS_SHRINE, LATITUDE_SHRINE, LONGITUDE_SHRINE);

    public static final double LATITUDE_ILIUM = 34.025757;
    public static final double LONGITUDE_ILIUM = -118.284286;
    public static final String ADDRESS_ILIUM = "3301 S Hoover St, Los Angeles, CA 90007";
    public static final Location LOCATION_ILIUM = new Location(ADDRESS_ILIUM, LATITUDE_ILIUM, LONGITUDE_ILIUM);

    public static final double LATITUDE_ICON = 34.019025;
    public static final double LONGITUDE_ICON= -118.281540;
    public static final String ADDRESS_ICON = "3584 S Figueroa St, Los Angeles, CA 90007";
    public static final Location LOCATION_ICON = new Location(ADDRESS_ICON, LATITUDE_ICON, LONGITUDE_ICON);

    public static final double LATITUDE_WHITE_HOUSE = 38.897626;
    public static final double LONGITUDE_WHITE_HOUSE = -77.037013;
    public static final String ADDRESS_WHITE_HOUSE = "1600 Pennsylvania Ave NW, Washington, DC 20500";
    public static final Location LOCATION_WHITE_HOUSE = new Location(ADDRESS_WHITE_HOUSE, LATITUDE_WHITE_HOUSE, LONGITUDE_WHITE_HOUSE);

    public static final double LATITUDE_USC_HOTEL= 34.019074;
    public static final double LONGITUDE_USC_HOTEL = -118.281367;
    public static final String ADDRESS_USC_HOTEL = "3540 S Figueroa St, Los Angeles, CA 90007";
    public static final Location LOCATION_USC_HOTEL = new Location(ADDRESS_USC_HOTEL, LATITUDE_USC_HOTEL, LONGITUDE_USC_HOTEL);

    public static final Item ITEM_1 = new Item(
            TITLE_1,
            ITEM_DESC_1,
            PRICE_1,
            TEST_URI,
            TEST_URL,
            TAGS_1,
            USER_ID_1,
            ITEM_ID_1,
            USERNAME_1,
            LOCATION_SHRINE
    );

    public static final Item ITEM_2 = new Item(
            TITLE_2,
            ITEM_DESC_2,
            PRICE_2,
            TEST_URI,
            TEST_URL,
            TAGS_2,
            USER_ID_2,
            ITEM_ID_2,
            USERNAME_2,
            LOCATION_ILIUM
    );

    public static final Item ITEM_3 = new Item(
            TITLE_3,
            ITEM_DESC_3,
            PRICE_3,
            TEST_URI,
            TEST_URL,
            TAGS_3,
            USER_ID_3,
            ITEM_ID_3,
            USERNAME_3,
            LOCATION_ICON
    );

    public static final Item ITEM_4 = new Item(
            TITLE_4,
            ITEM_DESC_4,
            PRICE_4,
            TEST_URI,
            TEST_URL,
            TAGS_4,
            USER_ID_1,
            ITEM_ID_4,
            USERNAME_1,
            LOCATION_WHITE_HOUSE
    );

    public static final User USER_1 = new User(
            USER_ID_1,
            FULLNAME_1,
            USERNAME_1,
            EMAIL_1,
            PHONE_NUMBER_1,
            PROFILE_DESC_1,
            TEST_URI
    );

    public static final User USER_2 = new User(
            USER_ID_2,
            FULLNAME_2,
            USERNAME_2,
            EMAIL_2,
            PHONE_NUMBER_2,
            PROFILE_DESC_2,
            TEST_URI
    );

    public static final User USER_3 = new User(
            USER_ID_3,
            FULLNAME_3,
            USERNAME_3,
            EMAIL_3,
            PHONE_NUMBER_3,
            PROFILE_DESC_3,
            TEST_URI
    );

    public static final List<User> ALL_USERS = ImmutableList.of(
            USER_1,
            USER_2,
            USER_3
    );

    public static final List<Item> ALL_ITEMS = ImmutableList.of(
            ITEM_1,
            ITEM_2,
            ITEM_3,
            ITEM_4
    );

    public static final List<Item> USER1_ITEMS = ImmutableList.of(
            ITEM_1,
            ITEM_4
    );

    public static final List<Item> ITEMS_1_2 = ImmutableList.of(
            ITEM_1,
            ITEM_2
    );
}
