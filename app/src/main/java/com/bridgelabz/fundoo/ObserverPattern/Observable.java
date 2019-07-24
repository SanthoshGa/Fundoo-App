package com.bridgelabz.fundoo.ObserverPattern;

public interface Observable<T> {
    void registerObserver(Observer observer);
    void unregisterObserver(Observer observer);
    void notify(Observer observer);
    void notifyAllObservers();
}
