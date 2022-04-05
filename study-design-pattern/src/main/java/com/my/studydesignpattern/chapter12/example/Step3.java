package com.my.studydesignpattern.chapter12.example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public class Step3 {

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

    public static class SchedulerFactory {

        public static ElevatorScheduler getScheduler(SchedulerStrategyId strategyId) {
            ElevatorScheduler scheduler = null;

            switch (strategyId) {
                case RESPONSE_TIME:
                    scheduler = ResponseTimeScheduler.of();
                    break;

                case THROUGHPUT:
                    scheduler = ThroughputScheduler.of();
                    break;
            }
            return scheduler;
        }
    }


    public static class ThroughputScheduler implements ElevatorScheduler {

        public static ThroughputScheduler of() {
            return new ThroughputScheduler();
        }
    }

    public class ElevatorController {

        private int id;
        private int curFloor;

        public ElevatorController(int id) {
            this.id = id;
            curFloor = 1;
        }

        public void gotFloor(int destination) {

        }
    }

    public class Direction {

    }

    public interface ElevatorScheduler {

    }

    public static class ResponseTimeScheduler implements ElevatorScheduler {

        public static ResponseTimeScheduler of() {
            return new ResponseTimeScheduler();
        }

    }

    public enum SchedulerStrategyId {
        RESPONSE_TIME,
        THROUGHPUT,
        DYNAMIC
    }

}
