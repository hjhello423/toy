package com.my.studydesignpattern.chapter9.practice2;

public class ControlRoomDisplay implements Observer {

    private ElevatorController elevatorController;

    public ControlRoomDisplay(ElevatorController elevatorController) {
        this.elevatorController = elevatorController;
    }

    @Override
    public void update() {
        System.out.println("room display : " + elevatorController.getFloor());
    }

}
