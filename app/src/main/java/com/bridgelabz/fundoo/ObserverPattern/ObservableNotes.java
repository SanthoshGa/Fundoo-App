package com.bridgelabz.fundoo.ObserverPattern;

import com.bridgelabz.fundoo.Dashboard.Model.Note;
import com.bridgelabz.fundoo.Dashboard.Model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class ObservableNotes implements Observable<List<Note>> {
    private List<Note> listOfNotes;
    private List<Observer> observerList = new ArrayList<>();

    public ObservableNotes(List<Note> noteList){
        this.listOfNotes = noteList;
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void notify(Observer observer) {
        observer.update(this);
    }

    @Override
    public void notifyAllObservers() {
        for(Observer observer : observerList) {
            observer.update(this);
        }
    }

    public List<Note> getListOfNotes() {
        return listOfNotes;
    }

    public void setListOfNotes(List<Note> listOfNotes) {
        this.listOfNotes = listOfNotes;
        notifyAllObservers();
    }
}
