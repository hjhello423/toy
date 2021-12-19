package com.my.studydesignpattern.chapter8.practice1;

public class TV {

    private boolean powerOn = false;
    private boolean muteOn = false;

    public void power() {
        powerOn = !powerOn;

        if (powerOn) {
            System.out.println("power on");
        } else {
            System.out.println("power off");
        }

    }

    public void mute() {
        if (!powerOn) {
            return;
        }

        muteOn = !muteOn;

        if (muteOn) {
            System.out.println("mute on");
        } else {
            System.out.println("mute off");
        }
    }

}
