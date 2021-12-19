package com.my.studydesignpattern.chapter8.example;

public class Client2 {

    public static void main(String[] args) {
        Command command = new LampCommand(new Lamp());
        Button button = new Button(command);
        button.pressed();
    }

    private static class Button {

        private Command command;

        public Button(Command command) {
            this.command = command;
        }

        public void pressed() {
            this.command.execute();
        }
    }

    private static class Lamp {

        public void turnOn() {
            System.out.println("lamp on");
        }

        public void turnOff() {
            System.out.println("lamp off");
        }

    }

    private interface Command {

        void execute();

    }

    private static class LampCommand implements Command {

        private Lamp lamp;

        public LampCommand(Lamp lamp) {
            this.lamp = lamp;
        }

        @Override
        public void execute() {
            lamp.turnOn();
        }

    }

}

