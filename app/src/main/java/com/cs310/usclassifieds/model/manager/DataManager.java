package com.cs310.usclassifieds.model.manager;

import com.cs310.usclassifieds.model.datamodel.User;

class DataManager {
    User getUser(int id) {
        return new User();
    }

    User getUser(String username) {
        return new User();
    }

    boolean userExists(String username) {
        return getUser(username) != null;
    }
}
