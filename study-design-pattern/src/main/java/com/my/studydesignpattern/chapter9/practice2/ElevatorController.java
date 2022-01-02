package com.my.studydesignpattern.chapter9.practice2;

import lombok.Getter;

public class ElevatorController {

    @Getter
    private int floor = 1;

    public void gotoFloor(int destination) {
        floor = destination;
    }

}
