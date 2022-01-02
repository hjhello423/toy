package com.my.studydesignpattern.chapter9.practice2;

public class ElevatorDisplay implements Observer {

    private ElevatorController elevatorController;

    public ElevatorDisplay(ElevatorController elevatorController) {
        this.elevatorController = elevatorController;
    }

    @Override
    public void update() {
        System.out.println("display : " + elevatorController.getFloor());
    }
}
