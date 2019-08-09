package com.bridgelabz.fundoo.add_note_page.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoteResponseModel extends BaseNoteModel {
//    String title;
//    String description;
//    boolean isPinned;
//    boolean isArchived;
//    boolean isDeleted;


    public NoteResponseModel(String title, String description, boolean isPinned, boolean isArchived,
                             boolean isDeleted, String createdDate, String modifiedDate, String color,
                             String id, String userId, List<String> reminder) {
        super(title, description, isPinned, isArchived, isDeleted, createdDate, modifiedDate,
                color, id, userId);
        this.reminder = reminder;
    }

    @SerializedName("reminder")
    private List<String> reminder;

    public List<String> getReminder() {
        return reminder;
    }

    public void setReminder(List<String> reminder) {
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
