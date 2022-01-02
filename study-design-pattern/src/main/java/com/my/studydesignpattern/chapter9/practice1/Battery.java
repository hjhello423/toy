package com.my.studydesignpattern.chapter9.practice1;

import lombok.Getter;

public class Battery extends Subject {

    @Getter
    private int level = 100;

    public void consume(int amount) {
        this.level -= amount;
        notifyObserver();
    }

}
