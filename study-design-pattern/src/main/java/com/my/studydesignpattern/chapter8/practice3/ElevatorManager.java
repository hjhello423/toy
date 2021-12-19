package com.my.studydesignpattern.chapter8.practice3;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ElevatorManager {

    public static final Random RANDOM = new Random();

    private List<ElevatorController> elevators;

    public static ElevatorManager of(int elevatorCont) {
        ElevatorManager result = new ElevatorManager();

        result.elevators = IntStream.range(0, elevatorCont)
            .mapToObj(i -> new ElevatorController())
            .collect(Collectors.toUnmodifiableList());

        return result;
    }

    public void requestElevator(int floor) {
        selectElevator().gotoFloor(floor);
    }

    private ElevatorController selectElevator() {
        return elevators.get(RANDOM.nextInt(elevators.size()));
    }

}
