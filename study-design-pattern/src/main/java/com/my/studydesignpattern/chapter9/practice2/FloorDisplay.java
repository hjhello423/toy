package com.my.studydesignpattern.chapter9.practice2;

public class FloorDisplay implements Observer {

    private ElevatorController elevatorController;

    public FloorDisplay(ElevatorController elevatorController) {
        this.elevatorController = elevatorController;
    }

    @Override
    public void update() {
        System.out.println("floor display : " + elevatorController.getFloor());
    }
}
