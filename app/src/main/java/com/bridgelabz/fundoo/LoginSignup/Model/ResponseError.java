package com.bridgelabz.fundoo.LoginSignup.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseError {

    @SerializedName("statusCode")
    private String statusCode;

    @SerializedName("name")
    private String name;

    @SerializedName("message")
    private String message;

    @SerializedName("details")
    private Object details;

    @SerializedName("stack")
    private String stack;

    @SerializedName("code")
    private String code;

    public ResponseError(String statusCode, String name, String message, String context) {
        this.statusCode = statusCode;
        this.name = name;
        this.message = message;
        this.details = context;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

//    public String toString() {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("name :").append(name).append()
//    }
}
