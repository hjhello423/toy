package com.my.studydesignpattern.chapter9.practice2;

public class VoiceNotice implements Observer{

    private ElevatorController elevatorController;

    public VoiceNotice(ElevatorController elevatorController) {
        this.elevatorController = elevatorController;
    }

    @Override
    public void update() {
        System.out.println("speak : " + elevatorController.getFloor());
    }

}
