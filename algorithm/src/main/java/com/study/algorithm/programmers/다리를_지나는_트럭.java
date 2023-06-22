package com.study.algorithm.programmers;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42583
 */

public class 다리를_지나는_트럭 {

    public static void main(String[] args) {
        int solution = solution(2, 10, new int[]{7, 4, 5, 6});
        System.out.println(solution);
    }

    public static int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<Truck> bridge = new LinkedList<>();
        int currentWeight = 0;
        int index = 0;
        int time = 0;

        while (index < truck_weights.length) {
            time++;
            int currentTruckWeight = truck_weights[index];

            if (!bridge.isEmpty()) {
                Truck peek = bridge.peek();
                if (time - peek.time >= bridge_length) {
                    Truck poll = bridge.poll();
                    currentWeight -= poll.weight;
                }
            }

            if (bridge.size() >= bridge_length) {
                continue;
            }

            if (currentWeight + currentTruckWeight > weight) {
                continue;
            }

            bridge.add(new Truck(currentTruckWeight, time));
            currentWeight += currentTruckWeight;
            index++;
        }
        return time + bridge_length;

    }

    private static class Truck {
        int weight;
        int time;

        public Truck(int weight, int time) {
            this.weight = weight;
            this.time = time;
        }
    }
}
