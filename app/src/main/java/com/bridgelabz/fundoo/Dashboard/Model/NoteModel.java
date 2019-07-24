package com.bridgelabz.fundoo.Dashboard.Model;

import com.google.gson.annotations.SerializedName;

public class NoteModel {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("isPinned")
    private String isPinned;

    @SerializedName("isArchived")
    private String isArchived;

    @SerializedName("isDeleted")
    private String isDeleted;

    @SerializedName("reminder")
    private String reminder;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("modifiedDate")
    private String modifiedDate;

    @SerializedName("color")
    private String color;

    @SerializedName("id")
    private String id;

    @SerializedName("userId")
    private String userId;

    public NoteModel(String title, String description, String isPinned, String isArchived,
                     String isDeleted, String reminder, String createdDate, String modifiedDate,
                     String color, String id, String userId) {
        this.title = title;
        this.description = description;
        this.isPinned = isPinned;
        this.isArchived = isArchived;
        this.isDeleted = isDeleted;
        this.reminder = reminder;
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

    public String getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(String isPinned) {
        this.isPinned = isPinned;
    }

    public String getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(String isArchived) {
        this.isArchived = isArchived;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

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
}
