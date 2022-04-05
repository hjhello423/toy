package com.my.studydesignpattern.chapter12.example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public class Step2 {

    public class ElevatorManager {

        private List<ElevatorController> controllers;
        private ThroughputScheduler scheduler;

        public ElevatorManager(int controllerCount) {
            IntStream.range(0, controllerCount)
                .forEach(it -> {
                    controllers.add(new ElevatorController(it));
                });
        }

        public void requestElevator(int destination, Direction direction) {
            ElevatorScheduler scheduler;
            int hour = LocalDateTime.now().getHour();

            if (hour < 12) {
                scheduler = new ResponseTimeScheduler();
            } else {
                scheduler = new ThroughputScheduler();
            }

        }
    }

    private class ThroughputScheduler implements ElevatorScheduler {

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

    private class Direction {

    }

    private interface ElevatorScheduler {

    }

    private class ResponseTimeScheduler implements ElevatorScheduler {

    }
}
