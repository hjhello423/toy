package com.my.studydesignpattern.chapter8.practice3;

public class ElevatorRequestCommand implements Command {

    private int floor;
    private ElevatorManager elevatorManager;

    public ElevatorRequestCommand(int floor, ElevatorManager elevatorManager) {
        this.elevatorManager = elevatorManager;
    }

    @Override
    public void execute() {
        elevatorManager.requestElevator(floor);
    }

}
