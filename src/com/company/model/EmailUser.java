package com.company.model;

/**
 * Created by nikolai on 31.01.2015.
 */
public class EmailUser {
    private String email;
    private String password;

    public EmailUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}