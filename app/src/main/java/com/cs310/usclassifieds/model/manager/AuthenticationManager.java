package com.cs310.usclassifieds.model.manager;

import com.cs310.usclassifieds.model.datamodel.User;

public class AuthenticationManager {
    private static final int MINIMUM_PASS_LENGTH = 10;
    private DataManager dataManager;

    public enum AuthError {
        PASSWORD_LENGTH,
        USER_DOES_NOT_EXIST,
        USER_ALREADY_EXISTS,
        WRONG_USERNAME_OR_PASSWORD,
        NO_ERROR,
    }

    public AuthenticationManager(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public AuthError validateUser(String username, String password) {
        final User user = dataManager.getUser(username);

        if(user == null) {
            return AuthError.USER_DOES_NOT_EXIST;
        }

        return AuthError.NO_ERROR;

    }

    public AuthError checkUsernameAvailability(String username) {
        User user = dataManager.getUser(username);
        return user != null ?
                AuthError.USER_ALREADY_EXISTS :
                AuthError.NO_ERROR;
    }

    public AuthError createAccount(String username, String password) {
        final String passwordHash = applyHash(password);

        if(!validatePassword(password)) {
            return AuthError.PASSWORD_LENGTH;
        }

        if(checkUsernameAvailability(username) != AuthError.NO_ERROR) {
            return AuthError.USER_ALREADY_EXISTS;
        }

        User user = new User(username, passwordHash, "id");
        dataManager.addUser(user);

        return AuthError.NO_ERROR;
    }

    private String applyHash(String password) {
        final StringBuilder builder = new StringBuilder();

        for(int i=0; i<password.length(); ++i) {
            int currInt = (password.charAt(i)*password.charAt(i) + i*2 + i)% 256;
            builder.append((char)currInt);
        }

        return builder.toString();
    }

    private boolean validatePassword(String password) {
        int numValidChars = 0;

        for(int i=0; i<password.length(); ++i) {
            if(Character.isDigit(password.charAt(i)) || Character.isLetter(password.charAt(i))) {
                ++numValidChars;
            }
        }

        return numValidChars >= MINIMUM_PASS_LENGTH;
    }
}
