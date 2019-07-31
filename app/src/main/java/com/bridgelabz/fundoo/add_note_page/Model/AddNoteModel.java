package com.bridgelabz.fundoo.add_note_page.Model;

import com.google.gson.annotations.SerializedName;

public class AddNoteModel extends BaseNoteModel {
    public AddNoteModel(String title, String description, boolean isPinned, boolean isArchived,
                        boolean isDeleted, String createdDate, String modifiedDate, String color,
                        String id, String userId, String reminder) {
        super(title, description, isPinned, isArchived, isDeleted,
                createdDate, modifiedDate, color, id, userId);

        this.reminder = reminder;
    }

    @SerializedName("reminder")
    private String reminder;

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public  String toString(){
        //                     .append()
        return " title: " + title + "\n" +
                " description :" + description + "\n" +
                " isPinned :" + isPinned + "\n" +
                "isArchived :" + isArchived + "\n" +
                "isDeleted :" + isDeleted + "\n" +
                "reminder :" + reminder + "\n" +
                "createdDate :" + createdDate + "\n" +
                "modifiedDate :" + modifiedDate + "\n";
    }
}
