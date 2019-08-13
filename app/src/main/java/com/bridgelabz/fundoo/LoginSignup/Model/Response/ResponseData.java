package com.bridgelabz.fundoo.LoginSignup.Model.Response;

import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData extends ResponseModel {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("details")
    private Object details;

    @SerializedName("data")
    private List<NoteResponseModel> noteModelList;

    @SerializedName("imageUrl")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ResponseData(boolean success, String message, String imageUrl) {
        this.success = success;
        this.message = message;
        this.imageUrl = imageUrl;
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
                     .append("message :").append(message).append("\n")
                     .append("imageUrl :").append(imageUrl).append("\n\n");
        return stringBuilder.toString();

    }

    public List<NoteResponseModel> getNoteModelList() {
        return noteModelList;
    }
}
