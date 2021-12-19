package com.my.studydesignpattern.chapter8.practice3;

public class DestinationSelectionCommand implements Command {

    private int floor;
    private ElevatorController elevatorController;

    public DestinationSelectionCommand(int floor,
        ElevatorController elevatorController) {
        this.floor = floor;
        this.elevatorController = elevatorController;
    }

    @Override
    public void execute() {
        elevatorController.gotoFloor(floor);
    }

}
