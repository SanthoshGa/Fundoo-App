package com.bridgelabz.fundoo.Dashboard.ViewModel;

import android.content.Context;

import com.bridgelabz.fundoo.Dashboard.Model.Note;
import com.bridgelabz.fundoo.Dashboard.Model.NoteDatabaseManager;
import com.bridgelabz.fundoo.ObserverPattern.Observable;
import com.bridgelabz.fundoo.ObserverPattern.ObservableNotes;

import java.util.List;

public class NoteViewModel
{
    private Observable<List<Note>> observableNotes;
    private NoteDatabaseManager noteDbManager;

    public NoteViewModel(Context context){
        noteDbManager = new NoteDatabaseManager(context);
//        List<Note> listOfNotes = noteDbManager.getAllNoteData();
        this.observableNotes = noteDbManager.getAllObservableNotes();
    }

    public  boolean addNote(Note note){
        return noteDbManager.addNote(note);
    }
    public boolean deleteNote(Note note){
       return  noteDbManager.deleteNote(note);
    }
// test
    public List<Note> getAllNoteData() {
        return noteDbManager.getAllNoteData();
    }
    public List<Note> getArchivedNotes(){
        return noteDbManager.getArchivedNotes();
    }
    public List<Note> getPinnedNotes(){
        return noteDbManager.getPinnedNotes();
    }
    public List<Note> getReminderNotes(){
        return noteDbManager.getReminderNotes();
    }
    public  List<Note> getTrashedNotes(){
        return noteDbManager.getTrashedNotes();
    }

    public boolean deleteAllNotes() {
        return noteDbManager.deleteAllNotes();
    }
    public boolean updateNote(Note noteToEdit){
        return noteDbManager.updateNote(noteToEdit);
    }

    public Observable<List<Note>> fetchAllNotes() {
        return observableNotes;
    }
}
