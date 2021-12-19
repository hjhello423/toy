package com.my.studydesignpattern.chapter8.practice2;

public class TwoButtonController {

    private Command button1Command;
    private Command button2Command;

    public TwoButtonController(Command button1Command, Command button2Command) {
        this.button1Command = button1Command;
        this.button2Command = button2Command;
    }

    public void button1() {
        this.button1Command.execute();
    }

    public void button2() {
        this.button2Command.execute();
    }

}
