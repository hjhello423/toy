package com.my.studydesignpattern.chapter8.practice3;


public class ElevatorButton {

    private Command command;

    public ElevatorButton(Command command) {
        this.command = command;
    }

    public void pressed() {
        this.command.execute();
    }

}
