package com.my.studydesignpattern.chapter8.practice1;

public class TwoButtonController {

    private TV tv;

    public TwoButtonController(TV tv) {
        this.tv = tv;
    }

    public void button1Pressed() {
        tv.power();
    }
    public void button2Pressed() {
        tv.mute();
    }

}
