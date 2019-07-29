package com.bridgelabz.fundoo.Dashboard.Model;

import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private String title;
    private String description;
    private String color;
    private boolean isArchived;
    private String ifReminder;
    private boolean isPinned;
    private boolean isTrashed;



    public Note(String title, String description, String color, boolean isArchived,
                String ifReminder, boolean isPinned, boolean isTrashed) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.isArchived = isArchived;
        this.ifReminder = ifReminder;
        this.isPinned = isPinned;
        this.isTrashed = isTrashed;
    }

    public boolean isTrashed() {
        return isTrashed;
    }

    public void setTrashed(boolean trashed) {
        isTrashed = trashed;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public String getIfReminder()
    {
        return ifReminder;
    }

    public void setIfReminder(String ifReminder) {
        this.ifReminder = ifReminder;
    }

    public boolean isArchived()
    {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Note Id : ").append(id).append("\n")
                .append("Note Title : ").append(title).append("\n")
                .append("Note Description : ").append(description).append("\n")
                .append("Note Color : ").append(color).append("\n")
                .append("Note Archive :").append(isArchived).append("\n")
                .append("Note Reminder :").append(ifReminder).append("\n")
                .append("Note Pinned :").append(isPinned).append("\n");
        return stringBuilder.toString();
    }
}


