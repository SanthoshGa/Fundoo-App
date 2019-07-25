package com.bridgelabz.fundoo.ObserverPattern;

import java.util.ArrayList;
import java.util.List;

public class ObservableImpl<T> implements Observable {
    private T data;
    List<Observer> observerList = new ArrayList<>();

    public ObservableImpl(T data) {
        this.data = data;
    }

    @Override
    public void registerObserver(Observer observer) {
        if (!observerList.contains(observer)) {
            this.observerList.add(observer);
        }
    }

    @Override
    public void unregisterObserver(Observer observer) {
        if (observerList.contains(observer)) {
            this.observerList.remove(observer);
        }
    }

    @Override
    public void notify(Observer observer) {
        observer.update(this);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observerList) {
            observer.update(this);
        }

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
