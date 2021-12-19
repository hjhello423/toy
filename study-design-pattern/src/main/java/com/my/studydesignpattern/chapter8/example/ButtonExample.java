package com.my.studydesignpattern.chapter8.example;

public class ButtonExample {

    private Lamp lamp;
    private Alarm alarm;
    private Mode mode;

    public ButtonExample(Lamp lamp, Alarm alarm) {
        this.lamp = lamp;
        this.alarm = alarm;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
    
    public void pressed() {
        switch (mode) {
            case LAMP:
                lamp.turnOn();
                break;
            case ALARM:
                alarm.start();
                break;
        }
    }
    
    private static class Lamp {

        public void turnOn() {
        }

    }

    private static class Alarm {

        public void start() {
        }

    }

    private enum Mode {
        LAMP, ALARM
    }
    

}
