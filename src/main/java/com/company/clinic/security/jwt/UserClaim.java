package com.company.clinic.security.jwt;

import java.util.List;

public class UserClaim {
    private long userId;
    private String username;
    private String email;
    private List<String> roles;

    public UserClaim() {
    }

    public UserClaim(long userId, String username, String email, List<String> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
