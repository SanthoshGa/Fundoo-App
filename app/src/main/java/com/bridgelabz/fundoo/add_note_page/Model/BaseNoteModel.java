package com.bridgelabz.fundoo.Dashboard.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseNoteModel implements Serializable {

    @SerializedName("title")
    protected String title;

    @SerializedName("description")
    protected String description;

    @SerializedName("isPinned")
    protected boolean isPinned;

    @SerializedName("isArchived")
    protected boolean isArchived;

    @SerializedName("isDeleted")
    protected boolean isDeleted;

//    @SerializedName("reminder")
//    private String reminderString;

    @SerializedName("createdDate")
    protected String createdDate;

    @SerializedName("modifiedDate")
    protected String modifiedDate;

    @SerializedName("color")
    private String color;

    @SerializedName("id")
    private String id;

    @SerializedName("userId")
    private String userId;

    public BaseNoteModel(String title, String description, boolean isPinned, boolean isArchived,
                         boolean isDeleted, String createdDate, String modifiedDate,
                         String color, String id, String userId) {
        this.title = title;
        this.description = description;
        this.isPinned = isPinned;
        this.isArchived = isArchived;
        this.isDeleted = isDeleted;
//        this.reminder = reminder;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.color = color;
        this.id = id;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

    public boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

//    public List<String> getReminder() {
//        return reminder;
//    }

//    public void setReminder(List<String> reminder) {
//        this.reminder = reminder;
//    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public  String toString(){
        //                     .append()
        return " title: " + title + "\n" +
                " description :" + description + "\n" +
                " isPinned :" + isPinned + "\n" +
                "isArchived :" + isArchived + "\n" +
                "isDeleted :" + isDeleted + "\n" +
//                     .append("reminder :").append(reminder).append("\n")
                "createdDate :" + createdDate + "\n" +
                "modifiedDate :" + modifiedDate + "\n";
    }
}
