package com.my.studydesignpattern.chapter8.practice2;

public class Client_practice2_6 {

    public static void main(String[] args) {
        TV tv = new TV();
        Power power = new Power(tv);
        Mute mute = new Mute(tv);

        TwoButtonController controller = new TwoButtonController(mute, power);

        controller.button1();
        controller.button2();
        controller.button1();
        controller.button1();
        controller.button2();
        controller.button1();
    }

}
