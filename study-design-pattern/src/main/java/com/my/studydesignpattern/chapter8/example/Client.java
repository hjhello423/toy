package com.my.studydesignpattern.chapter8.example;

public class Client {

    public static void main(String[] args) {
        Lamp lamp = new Lamp();
        Button button = new Button(lamp);
        button.pressed();
    }


    private static class Lamp {

        public void turnOn() {
            System.out.println("lamp on");
        }

    }

    private static class Button {

        private Lamp lamp;

        public Button(Lamp lamp) {
            this.lamp = lamp;
        }

        public void pressed() {
            lamp.turnOn();
        }
    }

}

