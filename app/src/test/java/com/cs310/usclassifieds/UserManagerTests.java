package com.cs310.usclassifieds;

import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.ItemManager;
import com.cs310.usclassifieds.model.manager.UserManager;
import com.google.common.collect.ImmutableList;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.cs310.usclassifieds.TestData.ALL_USERS;
import static com.cs310.usclassifieds.TestData.ALL_USER_IDS;
import static com.cs310.usclassifieds.TestData.USER1_ITEMS;
import static com.cs310.usclassifieds.TestData.USERNAME_1;
import static com.cs310.usclassifieds.TestData.USERNAME_2;
import static com.cs310.usclassifieds.TestData.USERNAME_3;
import static com.cs310.usclassifieds.TestData.USER_1;
import static com.cs310.usclassifieds.TestData.USER_2;
import static com.cs310.usclassifieds.TestData.USER_3;
import static com.cs310.usclassifieds.TestData.USER_ID_1;
import static com.cs310.usclassifieds.TestData.USER_ID_2;
import static com.cs310.usclassifieds.TestData.USER_ID_3;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserManagerTests {
    private DataManager dataManager = mock(DataManager.class);
    private UserManager userManager = new UserManager(dataManager);

    // test that usermanager properly calls datamanager to send friend requests
    @Test
    public void testSendFriendRequest() {
        for(int i=0; i<ALL_USERS.size(); ++i) {
            for(int j=i+1; j<ALL_USERS.size(); ++j) {
                userManager.sendFriendRequest(ALL_USERS.get(i), ALL_USERS.get(j));
            }
        }

        // check that friend requests are sent
        verify(dataManager, times(1)).createFriendRequest(USER_1, USER_2);
        verify(dataManager, times(1)).createFriendRequest(USER_1, USER_3);
        verify(dataManager, times(1)).createFriendRequest(USER_2, USER_3);

        // check that the friend isn't actually added
        verify(dataManager, times(0)).addFriend((User)any(), (User)any());

        System.out.println("Send Friend Request (all users) passed");
    }

    // Test that passing a null pointer in does not crash
    @Test
    public void testSendFriendRequestNullPointers() {
        userManager.sendFriendRequest(null, null);

        verify(dataManager, times(1)).createFriendRequest(null,null);

        System.out.println("Send Friend Request (null pointers) passed");
    }

    @Test
    public void testAcceptFriendRequest() {
        for(int i=0; i<ALL_USERS.size(); ++i) {
            for(int j=i+1; j<ALL_USERS.size(); ++j) {
                userManager.acceptFriendRequest(ALL_USERS.get(i), ALL_USERS.get(j));
            }
        }

        // check that friends are added
        verify(dataManager, times(1)).addFriend(USER_1, USER_2);
        verify(dataManager, times(1)).addFriend(USER_1, USER_3);
        verify(dataManager, times(1)).addFriend(USER_2, USER_3);

        System.out.println("Accept Friend request (all users) passed");
    }

    @Test
    public void testAcceptFriendRequestNullPointer() {
        userManager.acceptFriendRequest(null, null);

        verify(dataManager, times(1)).addFriend(null, null);

        System.out.println("Accept friend request (null pointers) passed");
    }

    @Test
    public void testDeclineFriendRequest() {
        for(int i=0; i<ALL_USERS.size(); ++i) {
            for(int j=i+1; j<ALL_USERS.size(); ++j) {
                userManager.declineFriendRequest(ALL_USERS.get(i), ALL_USERS.get(j));
            }
        }

        // check that friends are added
        verify(dataManager, times(1)).declineFriendRequest(USER_1, USER_2);
        verify(dataManager, times(1)).declineFriendRequest(USER_1, USER_3);
        verify(dataManager, times(1)).declineFriendRequest(USER_2, USER_3);

        System.out.println("Decline Friend request (all users) passed");
    }

    @Test
    public void testDeclineFriendRequestNullPointer() {
        userManager.declineFriendRequest(null, null);

        verify(dataManager, times(1)).declineFriendRequest(null, null);

        System.out.println("Decline friend request (null pointers) passed");
    }

    @Test
    public void testLoadProfile() {
        when(dataManager.getUser(USER_ID_1)).thenReturn(USER_1);
        when(dataManager.getUser(USER_ID_2)).thenReturn(USER_2);
        when(dataManager.getUser(USER_ID_3)).thenReturn(USER_3);

        final ImmutableList.Builder<User> builder = ImmutableList.builder();

        for (final User user : ALL_USERS) {
            builder.add(userManager.loadProfile(user.userId));
        }

        final List<User> returnUsers = builder.build();

        for(final User user : ALL_USERS) {
            assertTrue(returnUsers.contains(user));
        }

        verify(dataManager, times(1)).getUser(USER_ID_1);
        verify(dataManager, times(1)).getUser(USER_ID_2);
        verify(dataManager, times(1)).getUser(USER_ID_3);

        System.out.println("Load Profile (all users) passed");
    }

    @Test
    public void testLoadProfileNullPointer() {
        userManager.loadProfile(null);
        userManager.loadProfile("");

        verify(dataManager, times(1)).getUser(null);
        verify(dataManager, times(1)).getUser("");

        System.out.println("Load profile (null pointer) passed");
    }

    // test that getFriendsOf calls correct datamanager function to get friends from database
    @Test
    public void testGetFriendsOf() {
        when(dataManager.getFriendsOf((String)any())).thenReturn(ALL_USERS);

        final List<List<User>> userLists = new ArrayList<>();

        for(final User user : ALL_USERS) {
            userLists.add(userManager.getFriendsOf(user.userId));
        }

        for(final List<User> users : userLists) {
            for(final User user : ALL_USERS) {
                assertTrue(users.contains(user));
            }
        }

        verify(dataManager, times(1)).getFriendsOf(USER_ID_1);
        verify(dataManager, times(1)).getFriendsOf(USER_ID_2);
        verify(dataManager, times(1)).getFriendsOf(USER_ID_3);

        System.out.println("Get Friends of (all users) passed");
    }

    @Test
    public void testGetFriendsOfNullPointer() {
        when(dataManager.getFriendsOf(null)).thenReturn(null);

        userManager.getFriendsOf(null);

        verify(dataManager, times(1)).getFriendsOf(null);
        verify(dataManager, times(0)).getUser((String) any());

        System.out.println("Get Friends of (null pointer) passed");
    }

    // test that user.addFriendRequest functions as intended and getFriendRequestsOf call
    // right datamanager function to access database
    @Test
    public void testGetFriendRequestsOf() {
        when(dataManager.getUsers(ALL_USER_IDS)).thenReturn(ALL_USERS);

        final ImmutableList.Builder<List<User> > builder = ImmutableList.builder();

        for(final User user : ALL_USERS) {
            for(final User userToAdd : ALL_USERS) {
                user.addFriendRequest(userToAdd.userId);
            }

            builder.add(userManager.getFriendRequestsOf(user));
        }

        final List<List<User> > userLists = builder.build();

        for(final List<User> userList : userLists) {
            for(final User user : ALL_USERS) {
                assertTrue(userList.contains(user));
            }
        }

        verify(dataManager, times(3)).getUsers(ALL_USER_IDS);

        System.out.println("Get Friend Request (all users) passed");
    }

    @Test
    public void testGetFriendRequestsOfNullPointer() {
        final List<User> requests = userManager.getFriendRequestsOf(null);
        assertEquals(null, requests);

        verify(dataManager, times(0)).getUsers((List<String>)any());

        System.out.println("Get Friend Requests of (null pointer) passed");
    }

    @Test
    public void testAddUser() {
        for(final User user : ALL_USERS) {
            userManager.addUser(user);
        }

        verify(dataManager, times(1)).addUser(USER_1);
        verify(dataManager, times(1)).addUser(USER_2);
        verify(dataManager, times(1)).addUser(USER_3);

        System.out.println("Add User (all users) passed");
    }

    @Test
    public void testAddUserNullPointer() {
        when(dataManager.addUser(null)).thenReturn(false);
        userManager.addUser(null);

        verify(dataManager, times(1)).addUser(null);

        System.out.println("Add User (null pointer) passed");
    }
}
