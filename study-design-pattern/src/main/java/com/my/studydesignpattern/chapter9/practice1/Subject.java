package com.my.studydesignpattern.chapter9.practice1;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        this.observers.add(observer);
    }

    public void detach(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObserver() {
        this.observers.stream()
            .forEach(Observer::update);
    }

}
