package com.bridgelabz.fundoo.LoginSignup.Model;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("service")
    private String service;

    @SerializedName("role")
    private String role;

    @SerializedName("email")
    private String emailId;

    @SerializedName("username")
    private String userName;

    @SerializedName("id")
    private String id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("password")
    private String password;

    @SerializedName("ttl")
    private String ttl;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel(String firstName, String lastName, String phoneNumber, String imageUrl,
                     String service, String role, String emailId, String userName, String id,
                     String userId, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.service = service;
        this.role = role;
        this.emailId = emailId;
        this.userName = userName;
        this.id = id;
        this.userId = userId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId)  {
        this.userId = userId;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("firstName : ").append(firstName).append("\n")
                .append("lastName : ").append(lastName).append("\n")
                .append("phoneNumber ").append(phoneNumber).append("\n")
                .append("imageUrl ").append(imageUrl).append("\n")
                .append("role : ").append(role).append("\n")
                .append("service : ").append(service).append("\n")
                .append("emailId : ").append(emailId).append("\n")
                .append("userName : ").append(userName).append("\n")
                .append("userId : ").append(userId).append("\n")
                .append("id : ").append(id).append("\n")
                .append("password :").append(password).append("\n\n");
        return stringBuilder.toString();
    }


    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }
}
