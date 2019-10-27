package com.cs310.usclassifieds.model.datamodel;

public class User {
    private String passwordHash;
    private String username;

    public User(String username, String passwordHash) {
        this.passwordHash = passwordHash;
        this.username = username;
    }

    public User() {
        this.passwordHash = null;
        this.username = null;
    }

    public String getPassword() {
        return passwordHash;
    }

    public String getUsername() {
        return username;
    }
}
