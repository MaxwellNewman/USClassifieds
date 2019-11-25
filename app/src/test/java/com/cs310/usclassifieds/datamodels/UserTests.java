package com.cs310.usclassifieds.datamodels;

import com.cs310.usclassifieds.model.datamodel.User;

import org.junit.Before;
import org.junit.Test;

import static com.cs310.usclassifieds.TestData.ALL_USERS;
import static com.cs310.usclassifieds.TestData.ALL_USER_IDS;
import static com.cs310.usclassifieds.TestData.USER_1;
import static com.cs310.usclassifieds.TestData.USER_2;
import static com.cs310.usclassifieds.TestData.USER_3;
import static com.cs310.usclassifieds.TestData.USER_ID_2;
import static com.cs310.usclassifieds.TestData.USER_ID_4;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UserTests {
    @Before
    public void setup() {
        // make sure each user has no preexisting friends or friend requests
        for(final User user : ALL_USERS) {
            user.friendRequests.clear();
            user.friends.clear();
        }
    }

    // tests that adding friends does not duplicate
    @Test
    public void testAddFriends() {
        assertTrue(USER_1.friends.isEmpty());

        USER_1.addFriend(USER_2.userId);
        USER_1.addFriend(USER_3.userId);

        assertEquals(2, USER_1.friends.size());
        assertTrue(USER_1.friends.contains(USER_2.userId));
        assertTrue(USER_1.friends.contains(USER_3.userId));

        USER_1.addFriend(USER_2.userId);
        USER_1.addFriend(USER_3.userId);

        // Adding the friends a second time should not duplicate
        assertEquals(2, USER_1.friends.size());
        assertTrue(USER_1.friends.contains(USER_2.userId));
        assertTrue(USER_1.friends.contains(USER_3.userId));

        System.out.println("Add Friend passed");
    }

    // test that adding friend requests succeeds, and that adding a friend removes the
    // corresponding friend request, if it exists
    @Test
    public void testAddFriendRequests() {
        assertTrue(USER_1.friendRequests.isEmpty());

        USER_1.addFriendRequest(USER_2.userId);
        USER_1.addFriendRequest(USER_3.userId);

        assertEquals(2, USER_1.friendRequests.size());
        assertTrue(USER_1.friendRequests.contains(USER_2.userId));
        assertTrue(USER_1.friendRequests.contains(USER_3.userId));

        USER_1.addFriendRequest(USER_2.userId);
        USER_1.addFriendRequest(USER_3.userId);

        assertEquals(2, USER_1.friendRequests.size());
        assertTrue(USER_1.friendRequests.contains(USER_2.userId));
        assertTrue(USER_1.friendRequests.contains(USER_3.userId));

        // adding a friend should remove the friend request, if it exists
        USER_1.addFriend(USER_2.userId);
        // adding a friend should not require a preexisting friend request
        USER_1.addFriend(USER_ID_4);

        // there should now be only one friend request
        assertEquals(1, USER_1.friendRequests.size());
        assertTrue(USER_1.friendRequests.contains(USER_3.userId));

        assertEquals(2, USER_1.friends.size());
        assertTrue(USER_1.friends.contains(USER_2.userId));
        assertTrue(USER_1.friends.contains(USER_ID_4));

        // removing friend request should remove the request if it exists
        USER_1.removeFriendRequest(USER_3.userId);
        // if it does not exist, it should remove nothing
        USER_2.removeFriendRequest(USER_2.userId);

        assertTrue(USER_1.friendRequests.isEmpty());

        System.out.println("Add Friend Requests passed");
    }

    @Test
    public void testAddFriendRequestNullPointer() {
        USER_1.addFriendRequest(null);

        assertTrue(USER_1.friendRequests.isEmpty());
        assertTrue(USER_1.friends.isEmpty());

        System.out.println("Add Friend Request (null pointer) passed");
    }

    @Test
    public void testRemoveFriendRequestNullPointer() {
        assertTrue(USER_1.friendRequests.isEmpty());

        USER_1.addFriendRequest(USER_2.userId);
        USER_1.removeFriendRequest(null);
        assertEquals(1, USER_1.friendRequests.size());
        USER_1.removeFriendRequest(USER_2.userId);
        assertTrue(USER_1.friendRequests.isEmpty());

        System.out.println("Remove Friend Request (null pointer) passed");
    }

    @Test
    public void testIsFriend() {
        assertFalse(USER_1.isFriend(USER_2.userId));
        assertFalse(USER_1.isFriend(USER_3.userId));
        USER_1.addFriendRequest(USER_2.userId);
        assertFalse(USER_1.isFriend(USER_2.userId));
        assertFalse(USER_1.isFriend(USER_3.userId));
        USER_1.addFriend(USER_2.userId);
        assertTrue(USER_1.isFriend(USER_2.userId));
        assertFalse(USER_1.isFriend(USER_3.userId));
        USER_1.addFriend(USER_3.userId);
        assertTrue(USER_1.isFriend(USER_2.userId));
        assertTrue(USER_1.isFriend(USER_3.userId));

        System.out.println("Is Friend passed");
    }

    @Test
    public void testHasFriendRequest() {
        assertFalse(USER_1.hasFriendRequest(USER_2.userId));
        assertFalse(USER_1.hasFriendRequest(USER_3.userId));
        assertFalse(USER_1.isFriend(USER_2.userId));
        assertFalse(USER_1.isFriend(USER_3.userId));

        USER_1.addFriendRequest(USER_2.userId);
        assertTrue(USER_1.hasFriendRequest(USER_2.userId));
        assertFalse(USER_1.hasFriendRequest(USER_3.userId));
        assertFalse(USER_1.isFriend(USER_2.userId));
        assertFalse(USER_1.isFriend(USER_3.userId));

        USER_1.addFriendRequest(USER_3.userId);
        assertTrue(USER_1.hasFriendRequest(USER_2.userId));
        assertTrue(USER_1.hasFriendRequest(USER_3.userId));
        assertFalse(USER_1.isFriend(USER_2.userId));
        assertFalse(USER_1.isFriend(USER_3.userId));

        USER_1.removeFriendRequest(USER_3.userId);
        assertTrue(USER_1.hasFriendRequest(USER_2.userId));
        assertFalse(USER_1.hasFriendRequest(USER_3.userId));
        assertFalse(USER_1.isFriend(USER_2.userId));
        assertFalse(USER_1.isFriend(USER_3.userId));

        USER_1.addFriend(USER_2.userId);
        assertFalse(USER_1.hasFriendRequest(USER_2.userId));
        assertFalse(USER_1.hasFriendRequest(USER_3.userId));
        assertTrue(USER_1.isFriend(USER_2.userId));
        assertFalse(USER_1.isFriend(USER_3.userId));

        System.out.println("Has Friend Request passed");
    }
}
