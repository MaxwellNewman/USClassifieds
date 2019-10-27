package com.cs310.usclassifieds.model.manager;

import com.cs310.usclassifieds.model.datamodel.User;

public class AuthenticationManager {
    private DataManager dataManager;

    public AuthenticationManager(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public boolean verifyUser(String username, String password) {
        final User user = dataManager.getUser(username);

        if(user == null) {
            return false;
        }

        return user.getPassword().equals(applyHash(password));
    }

    public boolean userExists(String username) {
        User user = dataManager.getUser(username);
        return user != null;
    }

    private String applyHash(String password) {
        return password;
    }
}
