package com.bridgelabz.fundoo.LoginSignup.Model;

public class User
{
    private  String firstName;
    private  String lastName;
    private String username;
    private String emailId;
    private String password;

    public User(String firstName, String lastName, String username, String emailId, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.emailId = emailId;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
