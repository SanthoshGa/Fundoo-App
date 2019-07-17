package com.bridgelabz.fundoo.Dashboard.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.NOTE_TABLE_COL_ARCHIVE;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.NOTE_TABLE_COL_COLOR;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.NOTE_TABLE_COL_DESCRIPTION;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.NOTE_TABLE_COL_ID;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.NOTE_TABLE_COL_PINNED;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.NOTE_TABLE_COL_REMINDER;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.NOTE_TABLE_COL_TITLE;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.NOTE_TABLE_COL_TRASHED;
import static com.bridgelabz.fundoo.DatabaseHelpers.SQLiteDatabaseHelper.NOTE_TABLE_NAME;

public class NoteDatabaseManager {

    private static final String TAG = "DatabaseHelper.class";

    private SQLiteDatabaseHelper databaseHelper;
   List<Note> noteList;
//    NotesAdapter notesAdapter;


    public NoteDatabaseManager(Context context) {
        databaseHelper = SQLiteDatabaseHelper.getDatabaseHelper(context);
    }

    // new comment
    public boolean addNote(Note note) {
        SQLiteDatabase db = databaseHelper.openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TABLE_COL_TITLE, note.getTitle());
        contentValues.put(NOTE_TABLE_COL_DESCRIPTION, note.getDescription());
        contentValues.put(NOTE_TABLE_COL_COLOR, note.getColor());
        contentValues.put(NOTE_TABLE_COL_REMINDER, note.getIfReminder());
        contentValues.put(NOTE_TABLE_COL_ARCHIVE, note.isArchived());
        contentValues.put(NOTE_TABLE_COL_PINNED, note.isPinned());
        contentValues.put(NOTE_TABLE_COL_TRASHED, note.isTrashed());

        long res = db.insert(NOTE_TABLE_NAME, null, contentValues);
        db.close();

        Log.e(TAG, "res is " + res);
        return res > 0;

    }
    public boolean deleteNote(Note note)
    {
        SQLiteDatabase db = databaseHelper.openDb();
        long res = db.delete(NOTE_TABLE_NAME ,
                SQLiteDatabaseHelper.NOTE_TABLE_COL_ID + " = ? " ,
                new String[]{String.valueOf(note.getId())} );
        Log.e(TAG, "item deleted");

        return  res > 0;
    }

    public List<Note> getAllNoteData()
    {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + NOTE_TABLE_NAME + " ; " , null);
        Note note;
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TITLE));

            String description = cursor.getString(cursor.getColumnIndexOrThrow
                    (NOTE_TABLE_COL_DESCRIPTION));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_COLOR));
            boolean isArchived = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow
                    (NOTE_TABLE_COL_ARCHIVE)));
            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow
                    (NOTE_TABLE_COL_PINNED)));
            boolean isTrashed = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow
                    (NOTE_TABLE_COL_TRASHED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_REMINDER));
            note = new Note(title, description, color, isArchived, ifReminder, isPinned, isTrashed);
            note.setId(id);
            noteList.add(note);

            for (Note newNote : noteList) {
                System.out.println(newNote.toString());
            }
        }
        cursor.close();
        return noteList;
    }
    public List<Note> getArchivedNotes(){
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE_NAME +
                " WHERE " + SQLiteDatabaseHelper.NOTE_TABLE_COL_ARCHIVE + "= 1", null);
        Note note;
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_DESCRIPTION));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_COLOR));
            boolean isArchived = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ARCHIVE)));
            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_PINNED)));
            boolean isTrashed = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TRASHED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_REMINDER));
            note = new Note(title,description,color,isArchived,ifReminder,isPinned,isTrashed);
            note.setId(id);
            noteList.add(note);

            for (Note newNote : noteList){
                System.out.println(newNote.toString());
            }

        }
        cursor.close();
        return  noteList;
    }
    public List<Note> getPinnedNotes(){
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE_NAME +
                " WHERE " + NOTE_TABLE_COL_PINNED + "= 1" , null);
        Note note;
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_DESCRIPTION));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_COLOR));
            boolean isArchived = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ARCHIVE)));
            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_PINNED)));
            boolean isTrashed = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TRASHED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_REMINDER));
            note = new Note(title,description,color,isArchived,ifReminder,isPinned,isTrashed);
            note.setId(id);
            noteList.add(note);

            for (Note newNote : noteList){
                System.out.println(newNote.toString());
            }

        }
        cursor.close();
        return  noteList;
    }

    public  List<Note> getReminderNotes(){
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE_NAME +
                " WHERE " + NOTE_TABLE_COL_REMINDER + " != \"\"" , null );

        Note note = null;
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_DESCRIPTION));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_COLOR));
            boolean isArchived = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ARCHIVE)));
            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_PINNED)));
            boolean isTrashed = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TRASHED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_REMINDER));
            note = new Note(title,description,color,isArchived,ifReminder,isPinned,isTrashed);
            note.setId(id);
            noteList.add(note);

            for (Note newNote : noteList){
                System.out.println(newNote.toString());
            }

        }
        cursor.close();
        return  noteList;
    }

    public List<Note> getTrashedNotes(){
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE_NAME +
                " WHERE " + NOTE_TABLE_COL_TRASHED + " = 1", null);

        Note note = null;
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_DESCRIPTION));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_COLOR));
            boolean isArchived = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ARCHIVE)));
            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_PINNED)));
            boolean isTrashed = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TRASHED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_REMINDER));
            note = new Note(title,description,color,isArchived,ifReminder,isPinned,isTrashed);
            note.setId(id);
            noteList.add(note);

            for (Note newNote : noteList){
                System.out.println(newNote.toString());
            }

        }
        cursor.close();
        return  noteList;
    }

    public boolean updateNote(Note noteToEdit){
        SQLiteDatabase db = databaseHelper.openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TABLE_COL_TITLE, noteToEdit.getTitle());
        contentValues.put(NOTE_TABLE_COL_DESCRIPTION, noteToEdit.getDescription());
        contentValues.put(NOTE_TABLE_COL_COLOR, noteToEdit.getColor());

        long res = db.update(NOTE_TABLE_NAME, contentValues,
                SQLiteDatabaseHelper.NOTE_TABLE_COL_ID + " = ? ",
                new String[]{noteToEdit.getId() + ""});

        db.close();

        Log.e(TAG, "res is " + res);
        return res > 0;
    }

    public boolean deleteAllNotes() {
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery(" DELETE  FROM " + NOTE_TABLE_NAME + " ; " , null);

        boolean isDeleted = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return isDeleted;
    }

}
