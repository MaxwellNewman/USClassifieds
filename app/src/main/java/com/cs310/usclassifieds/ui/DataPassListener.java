package com.cs310.usclassifieds.ui;

// Helper class for passing data between fragments


import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.User;

import java.util.List;
import java.util.ArrayList;

public interface DataPassListener {

    void passItems(List<Item> items);
    void passUsers(List<User> users);
    void setViewedItem(Item item);
    void setViewedUser(User user);
    List<Item> getItems();
    List<User> getUsers();
    Item getViewedItem();
    User getViewedUser();
}
