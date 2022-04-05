package com.my.studydesignpattern.chapter12.example;

import java.util.List;
import java.util.stream.IntStream;

public class Step1 {

    public class ElevatorManager {

        private List<ElevatorController> controllers;
        private ThroughputScheduler scheduler;

        public ElevatorManager(int controllerCount) {
            IntStream.range(0, controllerCount)
                .forEach(it -> {
                    controllers.add(new ElevatorController(it));
                });
            scheduler = new ThroughputScheduler();
        }
    }

    private class ThroughputScheduler {

    }

    private class ElevatorController {

        private int id;
        private int curFloor;

        public ElevatorController(int id) {
            this.id = id;
            curFloor = 1;
        }

        public void gotFloor(int destination) {

        }
    }

}
