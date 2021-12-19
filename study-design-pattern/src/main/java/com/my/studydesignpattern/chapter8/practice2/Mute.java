package com.my.studydesignpattern.chapter8.practice2;

public class Mute implements Command {

    private TV tv;

    public Mute(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.mute();
    }

}
