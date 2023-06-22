package com.study.algorithm.programmers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42586
 */
public class 기능개발 {

    public static void main(String[] args) {
        int[] solution = solution(new int[]{90,90,90,90}, new int[]{30,1,1,1});

        Stream.of(solution)
                .forEach(it -> System.out.println(it));
    }

    public static int[] solution(int[] progresses, int[] speeds) {
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> answer = new ArrayList<>();

        for (int i = 0; i < progresses.length; i++) {
            int progress = progresses[i];
            int speed = speeds[i];

            int day = 0;
            if (progress != 100) {
                day = (int) Math.ceil((100 - progress) / (double) speed);
            }

            queue.add(day);
        }

        int count = 0;
        int previousElement = queue.peek();

        while (!queue.isEmpty()) {
            int current = queue.poll();

            if (current <= previousElement) {
                count++;
                continue;
            } else {
                answer.add(count);
            }

            previousElement = current;
            count =1;

        }

        answer.add(count);

        return answer.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

}
