package com.my.studydesignpattern.chapter6.practice.practice_4;

public class Printer {

    private boolean available;

    public boolean isAvailable() {
        return this.available;
    }

    public void unavailable() {
        this.available = false;
    }

}
