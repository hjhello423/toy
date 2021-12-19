package com.my.studydesignpattern.chapter8.practice2;

public class Power implements Command {

    private TV tv;

    public Power(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.power();
    }

}
