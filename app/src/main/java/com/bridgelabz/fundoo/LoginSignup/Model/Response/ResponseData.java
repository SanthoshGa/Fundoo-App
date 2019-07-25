package com.bridgelabz.fundoo.LoginSignup.Model.Response;

import com.google.gson.annotations.SerializedName;

public class ResponseData extends ResponseModel {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("details")
    private Object details;

    public ResponseData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("success :").append(success).append("\n")
                     .append("message :").append(message).append("\n\n");
        return stringBuilder.toString();

    }
}
