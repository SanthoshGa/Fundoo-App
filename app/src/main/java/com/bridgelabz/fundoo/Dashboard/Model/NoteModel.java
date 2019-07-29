package com.bridgelabz.fundoo.Dashboard.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NoteModel implements Serializable {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("isPinned")
    private boolean isPinned;

    @SerializedName("isArchived")
    private boolean isArchived;

    @SerializedName("isDeleted")
    private boolean isDeleted;

    @SerializedName("reminder")
    private List<String> reminder;

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

    public NoteModel(String title, String description, boolean isPinned, boolean isArchived,
                     boolean isDeleted, List<String> reminder, String createdDate, String modifiedDate,
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

    public List<String> getReminder() {
        return reminder;
    }

    public void setReminder(List<String> reminder) {
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

    public  String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" title: ").append(title).append("\n")
                     .append(" description :").append(description).append("\n")
                     .append(" isPinned :").append(isPinned).append("\n")
                     .append("isArchived :").append(isArchived).append("\n")
                     .append("isDeleted :").append(isDeleted).append("\n")
                     .append("reminder :").append(reminder).append("\n")
                     .append("createdDate :").append(createdDate).append("\n")
                     .append("modifiedDate :").append(modifiedDate).append("\n");
//                     .append()

        return stringBuilder.toString();
    }
}
