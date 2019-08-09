package com.bridgelabz.fundoo.add_note_page.ViewModel;

import android.content.Context;

import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.Note;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.add_note_page.data_manager.NoteDatabaseManager;
import com.bridgelabz.fundoo.ObserverPattern.Observable;

import java.util.List;

public class NoteViewModel
{
    private Observable<List<Note>> observableNotes;
    private NoteDatabaseManager noteDbManager;

    public NoteViewModel(Context context){
        noteDbManager = new NoteDatabaseManager(context);
//        List<Note> listOfNotes = noteDbManager.getAllNoteData();
//        this.observableNotes = noteDbManager.getAllObservableNotes();
    }

    public  boolean addNote(NoteResponseModel note){
        return noteDbManager.addNote(note);
    }
    public boolean addListOfNote(List<NoteResponseModel> noteList){
        return noteDbManager.addListOfNote(noteList);
    }
    public boolean deleteNote(Note note){
       return  noteDbManager.deleteNote(note);
    }
// test
    public List<NoteResponseModel> getAllNoteData() {
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
    public boolean updateNote(AddNoteModel noteToEdit){
        return noteDbManager.updateNote(noteToEdit);
    }

    public Observable<List<Note>> fetchAllNotes() {
        return observableNotes;
    }
}
